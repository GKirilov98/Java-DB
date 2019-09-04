package softuni.carshop.services;

import softuni.carshop.domain.dtos.customerDto.CustomerSeedDto;
import softuni.carshop.domain.dtos.customerDto.CustomerTotalSalesDto;
import softuni.carshop.domain.dtos.customerDto.CustomersOrderedDto;

import java.util.List;

public interface CustomerService {
    void seedCustomer(CustomerSeedDto[] customerSeedDtos);
    List<CustomersOrderedDto> getAllCustmersOrdered();

    List<CustomerTotalSalesDto> getAllCustmersTotalSales();
}
