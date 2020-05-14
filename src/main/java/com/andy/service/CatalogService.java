package com.andy.service;

import com.andy.controller.response.Product;
import com.andy.controller.response.ProductRatePlan;
import com.andy.controller.response.Products;
import com.andy.exception.NotFoundException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    private final ZuoraService zuoraService;

    @Autowired
    public CatalogService(ZuoraService zuoraService) {
        this.zuoraService = zuoraService;
    }

    public Products findProducts() {
        String response = this.zuoraService.get("/v1/catalog/products");
        Products products = new Gson().fromJson(response, Products.class);
        return products;
    }

    public ProductRatePlan findProductRatePlan(String ratePlanName) {
        List<Product> products = findProducts().getProducts();

        for (Product product : products) {
            if (product.getProductRatePlans() != null) {
                List<ProductRatePlan> ratePlans = product.getProductRatePlans().stream().filter(ratePlan -> ratePlan.getName().equals(ratePlanName)).collect(Collectors.toList());

                if (ratePlans.size() == 1) {
                    ProductRatePlan productRatePlan = ratePlans.get(0);
                    if (productRatePlan.isActive()) {
                        return productRatePlan;
                    }
                }
            }
        }

        throw new NotFoundException("Rate plan not found for " + ratePlanName);
    }


}
