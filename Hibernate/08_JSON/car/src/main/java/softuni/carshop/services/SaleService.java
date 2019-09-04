package softuni.carshop.services;

import softuni.carshop.domain.dtos.customerDto.CustomerTotalSalesDto;
import softuni.carshop.domain.dtos.saleDto.CarAppliedDiscountDto;

import java.util.List;

public interface SaleService {
    void seedService();

    List<CarAppliedDiscountDto> getAllSalesWithAppliedDiscount();
}
