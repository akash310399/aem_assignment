package com.training.aem.core.models.impl;

import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.models.ProductCarousalModel;
import com.training.aem.core.services.ProductDetailService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class},
        adapters = {ProductCarousalModel.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductCarousalModelImpl implements ProductCarousalModel {

    @OSGiService
    ProductDetailService productDetailService;

    private static final String BASE_URL = "https://fakestoreapi.com/products/";

    List<ProductEntity> productEntityList = new ArrayList<>();

    public List<ProductEntity> getProductEntityList(){
        return productEntityList;
    }


    @PostConstruct
    public void init(){
        productEntityList = productDetailService.getProductList(BASE_URL);
    }

}
