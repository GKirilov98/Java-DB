package softuni.carshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.carshop.domain.dtos.JSON.customerDto.CustomerSeedDto;
import softuni.carshop.domain.dtos.JSON.customerDto.CustomerTotalSalesDto;
import softuni.carshop.domain.dtos.JSON.customerDto.CustomersOrderedDto;
import softuni.carshop.domain.dtos.XML.exportDto.customerDto.CustomerExportOrderDto;
import softuni.carshop.domain.dtos.XML.exportDto.customerDto.CustomerExportRootDto;
import softuni.carshop.domain.dtos.XML.exportDto.customerDto.CustomerTotalSaleExportRootDto;
import softuni.carshop.domain.dtos.XML.importDto.CustomerImportRootDto;
import softuni.carshop.domain.entities.Customer;
import softuni.carshop.domain.entities.Part;
import softuni.carshop.repositories.CustomerRepository;
import softuni.carshop.services.CustomerService;
import softuni.carshop.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public void seedCustomer(CustomerSeedDto[] customerSeedDtos) {
        if (this.customerRepository.count() != 0) {
            return;
        }

        for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
            validateAndSave(customerSeedDto);
        }
    }

    @Override
    public List<CustomersOrderedDto> getAllCustmersOrdered() {
        List<CustomersOrderedDto> result = new ArrayList<>();
        this.customerRepository
                .findAllByIdIsNotNullOrderByBirthDateAscYoungDriverAsc()
                .forEach(c -> {
                    CustomersOrderedDto cod = this.modelMapper.map(c, CustomersOrderedDto.class);
                    result.add(cod);
                });
        return result;
    }

    @Override
    public List<CustomerTotalSalesDto> getAllCustmersTotalSales() {
        Set<CustomerTotalSalesDto> customerTotalSalesDtos = new HashSet<>();
        this.customerRepository.findAll()
                .forEach(customer -> {
                    CustomerTotalSalesDto cts = this.modelMapper.map(customer, CustomerTotalSalesDto.class);
                    cts.setFullName(customer.getName());
                    cts.setBoughtCar(customer.getSales().size());

                    BigDecimal moneySpent = customer
                            .getSales()
                            .stream()
                            .map(sale -> sale.getCarId()
                                    .getParts()
                                    .stream()
                                    .map(Part::getPrice)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                            )
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    cts.setSpentMoney(moneySpent);

                    customerTotalSalesDtos.add(cts);
                });


        return customerTotalSalesDtos
                .stream()
                .filter(e -> e.getBoughtCar() != 0)
                .sorted((c1, c2) -> {
                    int cmp = c2.getSpentMoney().compareTo(c1.getSpentMoney());
                    if (cmp == 0) {
                        cmp = c2.getBoughtCar() - (c1.getBoughtCar());
                    }
                    return cmp;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void importCustomersXML(CustomerImportRootDto customerImportRootDto) {
        if (this.customerRepository.count() != 0) {
            return;
        }

        for (CustomerSeedDto customerSeedDto : customerImportRootDto.getCustomers()) {
            validateAndSave(customerSeedDto);
        }
    }

    @Override
    public CustomerExportRootDto xmlExecuteOrderedCustomers() {
        CustomerExportRootDto customerExportRootDto = new CustomerExportRootDto();
                List<CustomerExportOrderDto> customersOrderedDtos = new ArrayList<>();
        this.customerRepository
                .findAllByIdIsNotNullOrderByBirthDateAscYoungDriverAsc()
                .forEach(c -> {
                    CustomerExportOrderDto cod = this.modelMapper.map(c, CustomerExportOrderDto.class);
                    customersOrderedDtos.add(cod);
                });
        customerExportRootDto.setCustomer(customersOrderedDtos);
        return customerExportRootDto;
    }

    @Override
    public CustomerTotalSaleExportRootDto xmlExportCustomerTotalSales() {
        List<CustomerTotalSalesDto> customerTotalSalesDtos = this.getAllCustmersTotalSales();
        CustomerTotalSaleExportRootDto customerTotalSaleExportRootDto = new CustomerTotalSaleExportRootDto();
        customerTotalSaleExportRootDto.setCustomer(customerTotalSalesDtos);
        return customerTotalSaleExportRootDto;
    }

    private void validateAndSave(CustomerSeedDto customerSeedDto) {
        if (!this.validatorUtil.isValid(customerSeedDto)) {
            this.validatorUtil.violations(customerSeedDto)
                    .forEach(e -> System.out.println(e.getMessage()));
            return;
        }

        Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);
        String validDate = customerSeedDto.getBirthDate().replace('T', ' ');
        customer.setBirthDate(LocalDate.parse(validDate,
                DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss")));
        this.customerRepository.saveAndFlush(customer);
    }


}
