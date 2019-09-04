package app.ccb.services;

import app.ccb.domain.dtos.client.ClientSeedDto;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    public ClientServiceImpl(FileUtil fileUtil, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper, ClientRepository clientRepository, EmployeeRepository employeeRepository) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Boolean clientsAreImported() {
        return this.clientRepository.count() != 0;
    }

    @Override
    public String readClientsJsonFile() {
        final String READ_PATH_FILE = ".\\src\\main\\resources\\files\\json\\clients.json";
        return this.fileUtil.fileReader(READ_PATH_FILE);
    }

    @Override
    public String importClients(String clients) {
        StringBuilder errors = new StringBuilder();

        ClientSeedDto[] clientSeedDtos = this.gson.fromJson(clients, ClientSeedDto[].class);
        for (ClientSeedDto seedDto : clientSeedDtos) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil
                        .violations(seedDto)
                        .forEach(v -> errors.append(v.getMessage()).append(System.lineSeparator()));
                continue;
            } else if (this.clientRepository.findClientByFullName(seedDto.getFirstName() + " " + seedDto.getLastName()) != null){
                continue;
            }

            Client client = this.modelMapper.map(seedDto, Client.class);
            client.setFullName(seedDto.getFirstName() + " " + seedDto.getLastName());

            Employee employee = this.employeeRepository
            .findEmployeeByFirstNameAndLastName(seedDto.getAppointedEmployee().split(" ")[0], seedDto.getAppointedEmployee().split(" ")[1]);
            client.getEmployees().add(employee);


            this.clientRepository.saveAndFlush(client);
            employee.getClients().add(client);
            this.employeeRepository.saveAndFlush(employee);
        }

        return errors.toString().trim();
    }

    @Override
    public String exportFamilyGuy() {
        Client client = this.clientRepository.findAll()
                .stream()
                .filter( c -> c.getBankAccount() != null)
                .sorted((a, b) -> b.getBankAccount().getCards().size() -
                        a.getBankAccount().getCards().size())
                .collect(Collectors.toList()).get(0);

        String cardsInfo = client.getBankAccount().getCards()
                .stream()
                .map(c -> String.format("   - Card number: %s\n" +
                                "   - Card status: %s\n\n",
                        c.getCardNumber(), c.getCardStatus()))
                .collect(Collectors.joining("")).trim();

        return String.format("Full name: %s\n" +
                        "Age: %d\n" +
                        "Bank account: %s\n" +
                        "Cards count: %d\n %s",
                client.getFullName(),
                client.getAge(),
                client.getBankAccount().getAccountNumber(),
                client.getBankAccount().getCards().size(),
                cardsInfo
        );
    }
}
