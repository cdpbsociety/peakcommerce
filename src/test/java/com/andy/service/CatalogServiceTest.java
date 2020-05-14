package com.andy.service;

import com.andy.controller.response.ProductRatePlan;
import com.andy.controller.response.Products;
import com.andy.exception.NotFoundException;
import com.andy.exception.ZuoraServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CatalogServiceTest {
    @Autowired
    private CatalogService catalogService;

    @Test
    public void findProducts_Success() {
        Products products = catalogService.findProducts();
        Assert.assertNotNull(products);
    }

    @Test(expected = ZuoraServiceException.class)
    public void findProducts_ServiceError() {
        ZuoraService zuoraService = Mockito.mock(ZuoraService.class);
        Mockito.when(zuoraService.get(Mockito.anyString())).thenThrow(new ZuoraServiceException("Houston we have a problem!"));
        CatalogService catalogService = new CatalogService(zuoraService);
        catalogService.findProducts();
    }

    @Test
    public void findProductRatePlan_Success() {
        ProductRatePlan productRatePlan = catalogService.findProductRatePlan("Gamer Elite");
        Assert.assertNotNull(productRatePlan);
    }

    @Test(expected = NotFoundException.class)
    public void findProductRatePlan_NotFound() {
        catalogService.findProductRatePlan("Unknown Rate Plan!");
    }
}
