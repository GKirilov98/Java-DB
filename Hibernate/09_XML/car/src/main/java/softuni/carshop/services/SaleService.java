package softuni.carshop.services;

import softuni.carshop.domain.dtos.JSON.saleDto.CarAppliedDiscountDto;
import softuni.carshop.domain.dtos.XML.exportDto.saleDto.SaleExportRootDto;

import java.util.List;

public interface SaleService {
    void seedService();

    List<CarAppliedDiscountDto> getAllSalesWithAppliedDiscount();

    SaleExportRootDto xmlExportSalesDiscount();
}
