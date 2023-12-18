package fact.it.receptservice.service;

import fact.it.receptservice.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final WebClient webClient;

    @Value("${ingredientservice.baseurl}")
    private String ingredientServiceBaseUrl;

    @Value("${ratingservice.baseurl}")
    private String ratingServiceBaseUrl;

//    public boolean placeOrder(OrderRequest orderRequest) {
//        Order order = new Order();
//        order.setOrderNumber(UUID.randomUUID().toString());
//
//        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDtoList()
//                .stream()
//                .map(this::mapToOrderLineItem)
//                .toList();
//
//        order.setOrderLineItemsList(orderLineItems);
//
//        List<String> skuCodes = order.getOrderLineItemsList().stream()
//                .map(OrderLineItem::getSkuCode)
//                .toList();
//
//        InventoryResponse[] inventoryResponseArray = webClient.get()
//                .uri("http://" + inventoryServiceBaseUrl + "/api/inventory",
//                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
//                .retrieve()
//                .bodyToMono(InventoryResponse[].class)
//                .block();
//
//        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
//                .allMatch(InventoryResponse::isInStock);
//
//        if(allProductsInStock){
//            ProductResponse[] productResponseArray = webClient.get()
//                    .uri("http://" + productServiceBaseUrl + "/api/product",
//                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
//                    .retrieve()
//                    .bodyToMono(ProductResponse[].class)
//                    .block();
//
//            order.getOrderLineItemsList().stream()
//                    .map(orderItem -> {
//                        ProductResponse product = Arrays.stream(productResponseArray)
//                                .filter(p -> p.getSkuCode().equals(orderItem.getSkuCode()))
//                                .findFirst()
//                                .orElse(null);
//                        if (product != null) {
//                            orderItem.setPrice(product.getPrice());
//                        }
//                        return orderItem;
//                    })
//                    .collect(Collectors.toList());
//
//            orderRepository.save(order);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public List<OrderResponse> getAllOrders() {
//        List<Order> orders = orderRepository.findAll();
//
//        return orders.stream()
//                .map(order -> new OrderResponse(
//                        order.getOrderNumber(),
//                        mapToOrderLineItemsDto(order.getOrderLineItemsList())
//                ))
//                .collect(Collectors.toList());
//    }
//
//    private OrderLineItem mapToOrderLineItem(OrderLineItemDto orderLineItemDto) {
//        OrderLineItem orderLineItem = new OrderLineItem();
//        orderLineItem.setPrice(orderLineItemDto.getPrice());
//        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
//        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());
//        return orderLineItem;
//    }
//
//    private List<OrderLineItemDto> mapToOrderLineItemsDto(List<OrderLineItem> orderLineItems) {
//        return orderLineItems.stream()
//                .map(orderLineItem -> new OrderLineItemDto(
//                        orderLineItem.getId(),
//                        orderLineItem.getSkuCode(),
//                        orderLineItem.getPrice(),
//                        orderLineItem.getQuantity()
//                ))
//                .collect(Collectors.toList());
//    }


}
