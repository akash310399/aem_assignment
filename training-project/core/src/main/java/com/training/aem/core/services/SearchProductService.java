package com.training.aem.core.services;

import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

public interface SearchProductService {

    List<String> executeQuery(String query, ResourceResolver resourceResolver);

}
