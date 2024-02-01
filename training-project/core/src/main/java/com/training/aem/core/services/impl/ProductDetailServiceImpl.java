package com.training.aem.core.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.aem.core.CommonConstants;
import com.training.aem.core.bean.ClientResponse;
import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.ProductDetailService;
import com.training.aem.core.services.RestService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component(service = {ProductDetailService.class})
public class ProductDetailServiceImpl implements ProductDetailService {


    @Reference
    RestService restService;

    @Override
    public ProductEntity getProductsData(String mainURL) {
        ProductEntity productEntity = new ProductEntity();

        try {
            ClientResponse clientResponse = restService
                    .getProductDetails(mainURL);
            if (clientResponse != null
                    && clientResponse.getStatusCode()
                    == HttpServletResponse.SC_OK){
                JSONObject responseObj = new JSONObject(clientResponse.getData());
                ObjectMapper objectMapper = new ObjectMapper();
                productEntity = objectMapper.readValue(responseObj.toString(), ProductEntity.class);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return productEntity;
    }
}
