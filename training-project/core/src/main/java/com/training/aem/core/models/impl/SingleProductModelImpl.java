package com.training.aem.core.models.impl;

import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.models.SingleProductModel;
import com.training.aem.core.services.ProductDetailService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

@Model(adaptables = {SlingHttpServletRequest.class},
        adapters = {SingleProductModel.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SingleProductModelImpl implements SingleProductModel {

    @ScriptVariable
    SlingHttpServletRequest request;

    @OSGiService
    ProductDetailService productDetailService;

    ProductEntity productEntity = new ProductEntity();

    public ProductEntity getProductEntity(){
        return null;
    }

}
