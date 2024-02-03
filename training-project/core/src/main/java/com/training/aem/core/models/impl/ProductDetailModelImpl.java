package com.training.aem.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.models.ProductDetailModel;
import com.training.aem.core.services.ProductDetailService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Model(adaptables = {SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        adapters = {ProductDetailModel.class})
public class ProductDetailModelImpl implements ProductDetailModel {


    @Self
    private SlingHttpServletRequest request;

    @OSGiService
    ProductDetailService productDetailService;

    @ScriptVariable
    Page currentPage;

    private static final String BASE_URL = "https://fakestoreapi.com/products/";

    ProductEntity productEntity = new ProductEntity();

    public ProductEntity getProductEntity(){
        return productEntity;
    }

    @PostConstruct
    void init() {
        if (currentPage != null) {
            ValueMap pageProperties = currentPage.getProperties();
            String productId = pageProperties.get("productId", String.class);
            if(StringUtils.isNotEmpty(productId)){
                String mainURL = BASE_URL + productId;
                productEntity = productDetailService.getProductsData(mainURL);
            }

        }
    }


}
