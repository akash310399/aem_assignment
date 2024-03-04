package com.training.aem.core.models.impl;

import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.impl.ProductDetailServiceImpl;
import com.training.aem.core.utils.CommonUtils;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class SingleProductModelImplTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    SingleProductModelImpl singleProductModel;

    @Mock
    SlingHttpServletRequest request;

    private static final String BASE_URL = "https://fakestoreapi.com/products/";

    @Mock
    ProductDetailServiceImpl productDetailService;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(SingleProductModelImpl.class);
    }

    @Test
    void getProductEntity() {
        List<String> suffixlist = new ArrayList<>();
        String suffix = "/product/1";
        RequestPathInfo requestPathInfo = mock(RequestPathInfo.class);


        when(requestPathInfo.getSuffix()).thenReturn(suffix);
        when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
        suffixlist = CommonUtils.getParamsFromURL(request);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle("test");
        productEntity.setPrice(123);
        productEntity.setId(2);

        when(productDetailService.getProductsData(BASE_URL + 1)).thenReturn(productEntity);

        // Call the method under test
        ProductEntity result = singleProductModel.getProductEntity();

        assertNotNull(result);
        assertEquals(2, result.getId());
    }
}