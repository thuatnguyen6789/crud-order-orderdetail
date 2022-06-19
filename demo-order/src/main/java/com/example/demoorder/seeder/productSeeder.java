package com.example.demoorder.seeder;

import com.example.demoorder.entity.Product;
import com.example.demoorder.repository.ProductRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Builder
@Component
public class productSeeder {
    @Autowired
    ProductRepository productRepository;
    public void testSave() {
        System.out.println("Save oke");
        productRepository.save(Product.builder()
        );
    }
}
