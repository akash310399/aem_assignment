package com.training.aem.core.services.impl;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.training.aem.core.entities.AlertContentFragmentEntity;
import com.training.aem.core.services.AlertService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = AlertService.class)
public class AlertServiceImpl implements AlertService {

    public static final String CF_PATH = "/content/dam/training-project/content-fragments";
    private static final String ALERT = "alert";
    private static final String MESSAGE = "message";
    private static final String SERVICE_USER = "useruser";

    @Reference
    ResourceResolverFactory resourceResolverFactory;


    public List<AlertContentFragmentEntity> getAlertsFromContentFragment(){
        List<AlertContentFragmentEntity> fragmentEntities = new ArrayList<>();
        try(ResourceResolver resourceResolver = getResourceResolver()){
            Resource contentFragmentsFolder = resourceResolver.getResource(CF_PATH);
            if(contentFragmentsFolder.hasChildren() && contentFragmentsFolder!=null){
                    Iterable<Resource> children = contentFragmentsFolder.getChildren();
                    for(Resource child:children){
                        if("jcr:content".equals(child.getName())){
                            continue;
                        }

                        AlertContentFragmentEntity alertContentFragmentEntity = new AlertContentFragmentEntity();
                        ContentFragment contentFragment = child.adaptTo(ContentFragment.class);
                        String alert = contentFragment.getElement(ALERT).getContent();
                        String message = contentFragment.getElement(MESSAGE).getContent();
                        String name = contentFragment.getName();

                        alertContentFragmentEntity.setName(name);
                        alertContentFragmentEntity.setAlert(alert);
                        alertContentFragmentEntity.setMessage(message);
                        fragmentEntities.add(alertContentFragmentEntity);
                    }
            }
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        return fragmentEntities;
    }

    private ResourceResolver getResourceResolver() throws LoginException {
        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, SERVICE_USER);
        return resourceResolverFactory.getServiceResourceResolver(map);
    }

}
