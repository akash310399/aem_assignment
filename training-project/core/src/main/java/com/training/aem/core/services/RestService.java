package com.training.aem.core.services;

import com.training.aem.core.bean.ClientResponse;

public interface RestService {

    ClientResponse getProductDetails(String mainUrl) throws Exception;

    ClientResponse getProductDetailList(String mainUrl) throws Exception;

}
