package com.example.demoorder.seeder;

import com.example.demoorder.entity.Order;
import com.example.demoorder.entity.OrderDetail;
import com.example.demoorder.entity.OrderDetailId;
import com.example.demoorder.entity.Product;
import com.example.demoorder.entity.enums.OrderSimpleStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

public class OrderSeeder {
    public static List<Order> orders;
    public static final int NUMBER_OF_ORDER = 1000;
    public static final int NUMBER_OF_DONE = 600;
    public static final int NUMBER_OF_CANCEL = 200;
    public static final int NUMBER_OF_PENDING = 200;
    public static final int MIN_ORDER_DETAIL = 2;
    public static final int MAX_ORDER_DETAIL = 5;
    public static final int MIN_PRODUCT_QUANTITY = 1;
    public static final int MAX_PRODUCT_QUANTITY = 5;

    @Autowired
    OrderRepository orderRepository;

    public void generate() {
        log.debug("-----------Seeding order--------");
        Faker faker = new Faker();
        orders = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_ORDER; i++) {
            // lay random user
            int randomUserIndex = NumberUtil.getRandomNumber(0, UserSeeder.users.size() - 1);
            User user = UserSeeder.users.get(randomUserIndex);
            // tao moi don hang
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setStatus(OrderSimpleStatus.DONE);
            order.setCreatedAt(LocalDateTime.now());
            order.setUserId(user.getId());
            // tao danh sach order detail cho don hang
            Set<OrderDetail> orderDetails = new HashSet<>();
            // map nay dung de check su ton tai cua san pham trong order detail
            HashMap<String, Product> mapProduct = new HashMap<>();
            // generate so luong cua order detail
            int orderDetailNumber = NumberUtil.getRandomNumber(MIN_ORDER_DETAIL, MAX_ORDER_DETAIL);
            for(int j = 0; j < orderDetailNumber; j++) {
                // lay random product
                int randomProductIndex = NumberUtil.getRandomNumber(0, ProductSeeder.products.size() - 1);
                Product product = ProductSeeder.products.get(randomProductIndex);
                // chek ton tai
                if(mapProduct.containsKey(product.getId())) {
                    continue;
                }
                // tao order detail theo san pham random
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(new OrderDetailId(order.getId(), product.getId()));
                orderDetail.setOrder(order); // set quan he
                orderDetail.setProduct(product); // set quan he
                orderDetail.setUnitPrice(product.getPrice()); // gia theo san pham
                // random so luong theo cau hinh
                orderDetail.setQuantity(NumberUtil.getRandomNumber(MIN_PRODUCT_QUANTITY, MAX_PRODUCT_QUANTITY));
                // dua vao danh sach order detail
                orderDetails.add(orderDetail);
                mapProduct.put(product.getId(), product);
            }
            // set quan he voi order
            order.setOrderDetails(orderDetails);
            order.calculateTotalPrice();
            orders.add(order);
        }
        orderRepository.saveAll(orders);
        log.debug("-------End of ")
    }
}
