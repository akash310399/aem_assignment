package com.training.aem.core.services;

import com.training.aem.core.entities.ProductEntity;

public interface PageService {

    /**
     * Creates a page based on the provided product entity.
     *
     * @param productEntity The product entity to use for creating the page.
     */
    void createPage(ProductEntity productEntity);

}
