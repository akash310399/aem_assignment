package com.training.aem.core.services.impl;

import com.training.aem.core.CommonConstants;
import com.training.aem.core.bean.ClientResponse;
import com.training.aem.core.services.RestService;
import com.training.aem.core.utils.CommonUtils;
import org.osgi.service.component.annotations.Component;


@Component(service = {RestService.class}, immediate = true)
public class RestServiceImpl implements RestService {
    @Override
    public ClientResponse getProductDetails(String mainUrl) throws Exception {
        return CommonUtils.getClientResponse
                (CommonConstants.GET,mainUrl,null
                        ,null);
    }
}
