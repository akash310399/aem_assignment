package com.training.aem.core.models.impl;

import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.impl.ProductDetailServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ProductCarousalModelImplTest {

    AemContext ctx = new AemContext();

    @Mock
    ProductDetailServiceImpl productDetailService;

    private static final String BASE_URL = "https://fakestoreapi.com/products/";


    @InjectMocks
    ProductCarousalModelImpl productCarousalModel;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ProductCarousalModelImpl.class);
    }

    @Test
    void getProductEntityList() {
        productCarousalModel.getProductEntityList();
    }

    @Test
    void init() {
        List<ProductEntity> productEntityList = new ArrayList<>();

        when(productDetailService.getProductList(BASE_URL)).thenReturn(productEntityList);
        productCarousalModel.init();
    }
}