package com.training.aem.core.servlets;

import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.ProductDetailService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class SortProductServletTest {


    @InjectMocks
    SortProductServlet sortProductServlet;

    @Mock
    ProductDetailService productDetailService;

    private static final String MAIN_URL = "https://fakestoreapi.com/products";


    @Test
    void doGet(AemContext context) throws ServletException, IOException {



        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();

        List<ProductEntity> productEntityList = new ArrayList();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle("something");
        productEntityList.add(productEntity);

        when(productDetailService.getProductList(eq(MAIN_URL))).thenReturn(productEntityList);

        sortProductServlet.doGet(request, response);
    }
}