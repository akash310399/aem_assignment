package com.training.aem.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.ProductDetailService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ProductDetailModelImplTest {



    @InjectMocks
    ProductDetailModelImpl productDetailModel;

    @Mock
    SlingHttpServletRequest request;

    @Mock
    Page currentPage;

    @Mock
    ProductDetailService productDetailService;

    @Mock
    ValueMap pageProperties;

    @BeforeEach
    void setUp() {
        when(currentPage.getProperties()).thenReturn(pageProperties);
    }

    @Test
    void init_withValidProductId() {
        // Arrange
        String productId = "123";
        when(pageProperties.get("productId", String.class)).thenReturn(productId);

        ProductEntity expectedProductEntity = new ProductEntity();
        when(productDetailService.getProductsData(anyString())).thenReturn(expectedProductEntity);

        // Act
        productDetailModel.init();

        // Assert
        verify(productDetailService).getProductsData("https://fakestoreapi.com/products/" + productId);
        assertEquals(expectedProductEntity, productDetailModel.getProductEntity());
    }


}