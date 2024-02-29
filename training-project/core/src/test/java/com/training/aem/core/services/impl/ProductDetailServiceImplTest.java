package com.training.aem.core.services.impl;

import com.training.aem.core.bean.ClientResponse;
import com.training.aem.core.services.RestService;
import com.training.aem.core.utils.CommonUtils;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, AemContextExtension.class})
class ProductDetailServiceImplTest {

    @InjectMocks
    ProductDetailServiceImpl productDetailService;

    @Mock
    RestService restService;

    AemContext ctx = new AemContext();

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ProductDetailServiceImpl.class);
    }

    @Test
    void getProductsData() throws Exception {

        ClientResponse clientResponse = new ClientResponse();

        clientResponse.setData("{\n" +
                "  \"id\": 123,\n" +
                "  \"title\": \"Sample Product\",\n" +
                "  \"price\": 49.99,\n" +
                "  \"description\": \"This is a sample product description.\",\n" +
                "  \"category\": \"Electronics\",\n" +
                "  \"image\": \"https://example.com/sample-product.jpg\",\n" +
                "  \"rating\": {\n" +
                "    \"rate\": 4.5,\n" +
                "    \"count\": 100\n" +
                "  }\n" +
                "}\n");
        clientResponse.setStatusCode(200);
        when(restService.getProductDetails(anyString())).thenReturn(clientResponse);
        productDetailService.getProductsData("abc.com");
    }

    @Test
    void getProductList() throws Exception {
        ClientResponse clientResponse = new ClientResponse();

        clientResponse.setData("[\n" +
                "  {\n" +
                "    \"id\": 123,\n" +
                "    \"title\": \"Sample Product\",\n" +
                "    \"price\": 49.99,\n" +
                "    \"description\": \"This is a sample product description.\",\n" +
                "    \"category\": \"Electronics\",\n" +
                "    \"image\": \"https://example.com/sample-product.jpg\",\n" +
                "    \"rating\": {\n" +
                "      \"rate\": 4.5,\n" +
                "      \"count\": 100\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 456,\n" +
                "    \"title\": \"Another Sample Product\",\n" +
                "    \"price\": 79.99,\n" +
                "    \"description\": \"This is another sample product description.\",\n" +
                "    \"category\": \"Clothing\",\n" +
                "    \"image\": \"https://example.com/another-sample-product.jpg\",\n" +
                "    \"rating\": {\n" +
                "      \"rate\": 3.8,\n" +
                "      \"count\": 50\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 789,\n" +
                "    \"title\": \"Yet Another Sample Product\",\n" +
                "    \"price\": 129.99,\n" +
                "    \"description\": \"This is yet another sample product description.\",\n" +
                "    \"category\": \"Home & Kitchen\",\n" +
                "    \"image\": \"https://example.com/yet-another-sample-product.jpg\",\n" +
                "    \"rating\": {\n" +
                "      \"rate\": 4.2,\n" +
                "      \"count\": 75\n" +
                "    }\n" +
                "  }\n" +
                "]");
        clientResponse.setStatusCode(200);
        when(restService.getProductDetailList(anyString())).thenReturn(clientResponse);
        productDetailService.getProductList("abc.com");
    }
}