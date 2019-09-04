package softuni.carshop.services;

import softuni.carshop.domain.dtos.JSON.customerDto.CustomerSeedDto;
import softuni.carshop.domain.dtos.JSON.customerDto.CustomerTotalSalesDto;
import softuni.carshop.domain.dtos.JSON.customerDto.CustomersOrderedDto;
import softuni.carshop.domain.dtos.XML.exportDto.customerDto.CustomerExportRootDto;
import softuni.carshop.domain.dtos.XML.exportDto.customerDto.CustomerTotalSaleExportRootDto;
import softuni.carshop.domain.dtos.XML.importDto.CustomerImportRootDto;

import java.util.List;

public interface CustomerService {
    void seedCustomer(CustomerSeedDto[] customerSeedDtos);
    List<CustomersOrderedDto> getAllCustmersOrdered();

    List<CustomerTotalSalesDto> getAllCustmersTotalSales();

    void importCustomersXML(CustomerImportRootDto customerImportRootDto);

    CustomerExportRootDto xmlExecuteOrderedCustomers();

    CustomerTotalSaleExportRootDto xmlExportCustomerTotalSales();
}
