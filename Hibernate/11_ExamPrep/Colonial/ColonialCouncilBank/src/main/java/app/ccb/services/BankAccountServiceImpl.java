package app.ccb.services;

import app.ccb.domain.dtos.bankAccount.BankAccountDto;
import app.ccb.domain.dtos.bankAccount.BankAccountsRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.util.ValidatorUtil;
import app.ccb.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    final String READ_PATH_FILE = ".\\src\\main\\resources\\files\\xml\\bank-accounts.xml";


    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;


    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public BankAccountServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, XmlParser xmlParser, BankAccountRepository bankAccountRepository, ClientRepository clientRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
    }


    @Override
    public Boolean bankAccountsAreImported() {
        return this.bankAccountRepository.count() != 0;

    }

    @Override
    public String readBankAccountsXmlFile() {
        try {
            return Files.lines(Paths.get(READ_PATH_FILE)).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String importBankAccounts() {
        StringBuilder messages = new StringBuilder();
        BankAccountsRootDto bankAccountsRootDto = this.xmlParser
                .objectFromFile(BankAccountsRootDto.class, READ_PATH_FILE);
        for (BankAccountDto seedDto : bankAccountsRootDto.getBankAccountDtos()) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil
                        .violations(seedDto)
                        .forEach(v -> messages.append(v.getMessage()).append(System.lineSeparator()));

                continue;
            }

            BankAccount bankAccount = this.modelMapper.map(seedDto, BankAccount.class);

            Client client = this.clientRepository.findClientByFullName(seedDto.getClient());
            if (client == null){
                continue;
            }
            bankAccount.setClient(client);
            this.bankAccountRepository.saveAndFlush(bankAccount);
            client.setBankAccount(bankAccount);
            this.clientRepository.saveAndFlush(client);
        }
        return messages.toString().trim();
    }
}
