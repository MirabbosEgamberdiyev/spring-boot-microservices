package com.programmingtechie.order_service.service;

import com.programmingtechie.order_service.dto.OrderLineItemsDto;
import com.programmingtechie.order_service.dto.OrderRequest;
import com.programmingtechie.order_service.model.Order;
import com.programmingtechie.order_service.model.OrderLineItems;
import com.programmingtechie.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        Stream<OrderLineItems> orderLineItemsStream = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemDto -> mapToDto(orderLineItemDto))
                ;
        order.setOrderLineItemsList(orderLineItemsStream.toList());

        orderRepository.save(order);

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemDto) {
           OrderLineItems orderLineItems = new OrderLineItems();
           orderLineItems.setPrice(orderLineItemDto.getPrice());
           orderLineItems.setQuantity(orderLineItemDto.getQuantity());
           orderLineItems.setSkuCode(orderLineItemDto.getSkuCode());

           return orderLineItems;
    }
}
