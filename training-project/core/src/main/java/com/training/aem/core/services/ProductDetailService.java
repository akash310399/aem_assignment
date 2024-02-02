package com.training.aem.core.services;

import com.training.aem.core.entities.ProductEntity;

import java.util.List;

public interface ProductDetailService {

    ProductEntity getProductsData(String mainURL);

    List<ProductEntity> getProductList(String mainUrl);
}
