package com.training.aem.core.services;

import org.apache.sling.api.resource.ResourceResolver;

public interface ReadExcelDataService {

    void getDataFromExcel(final ResourceResolver resolver, String path);


}
