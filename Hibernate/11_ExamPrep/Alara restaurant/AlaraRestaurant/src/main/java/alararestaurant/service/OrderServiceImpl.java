package alararestaurant.service;

import alararestaurant.domain.dtos.orders.importDto.ItemImportDto;
import alararestaurant.domain.dtos.orders.importDto.OrderImportDto;
import alararestaurant.domain.dtos.orders.importDto.OrderImportRootDto;
import alararestaurant.domain.entities.*;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.ValidatorUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, XmlParser xmlParser, OrderRepository orderRepository, OrderItemRepository orderItemRepository, EmployeeRepository employeeRepository, ItemRepository itemRepository) {

        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
    }


    @Override
    public Boolean ordersAreImported() {
    return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() {
        final String READ_PATH_FILE = ".\\src\\main\\resources\\files\\orders.xml";
        try {
            return Files.lines(Paths.get(READ_PATH_FILE)).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String importOrders() {
        StringBuilder errors = new StringBuilder();
        final String READ_PATH_FILE = ".\\src\\main\\resources\\files\\orders.xml";

        OrderImportRootDto orderImportRootDto = this.xmlParser.objectFromFile(OrderImportRootDto.class, READ_PATH_FILE );

        for (OrderImportDto seedDto : orderImportRootDto.getOrders()) {
            Employee employee = this.employeeRepository.findEmployeeByName(seedDto.getEmployee());
            if (employee == null) {continue;}

            Set<OrderItem> orderItems = new LinkedHashSet<>();
            boolean doNotExistItem = false;
            for (ItemImportDto iid : seedDto.getItems().getItems()) {
                Item item =  this.itemRepository.findItemByName(iid.getName());
                if (item == null){
                    doNotExistItem = true;
                    break;
                } else {
                    OrderItem orderItem  = new OrderItem();
                    orderItem.setItem(item);
                    orderItem.setQuantity(iid.getQuantity());
                    if (!this.validatorUtil.isValid(orderItem)) {
                        this.validatorUtil
                                .violations(orderItem)
                                .forEach(v -> errors.append(v.getMessage()).append(System.lineSeparator()));

                        continue;
                    }

                    orderItems.add(orderItem);
                }
            }

            if (doNotExistItem){
                continue;
            }

            Order order = this.modelMapper.map(seedDto, Order.class);
            order.setEmployee(employee);
            order.setOrderType(OrderType.valueOf(seedDto.getType()));

            if (!this.validatorUtil.isValid(order)) {
                this.validatorUtil
                        .violations(order)
                        .forEach(v -> errors.append(v.getMessage()).append(System.lineSeparator()));

                continue;
            }

            order.setOrderItems(orderItems);
            this.orderRepository.saveAndFlush(order);

            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
                this.orderItemRepository.saveAndFlush(orderItem);
            }
        }

       return errors.toString().trim();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        // TODO : Implement me
        return null;
    }
}
