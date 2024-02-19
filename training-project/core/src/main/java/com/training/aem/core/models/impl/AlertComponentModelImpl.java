package com.training.aem.core.models.impl;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.training.aem.core.entities.AlertContentFragmentEntity;
import com.training.aem.core.models.AlertComponentModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = AlertComponentModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AlertComponentModelImpl implements AlertComponentModel {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AlertComponentModel.class);

    public static final String CF_PATH = "/content/dam/training-project/content-fragments";
    private static final String ALERT = "alert";
    private static final String MESSAGE = "message";



    @SlingObject
    private ResourceResolver resourceResolver;

    List<AlertContentFragmentEntity> fragmentEntities = new ArrayList<>();

    /**
     * Retrieves the list of fragment entities.
     * @return List of AlertContentFragmentEntity objects.
     */
    public List<AlertContentFragmentEntity> getFragmentEntities(){
        return fragmentEntities;
    }


    /**
     * Initializes the component by fetching content fragments and populating fragmentEntities list.
     */
    @PostConstruct
    void init(){
        Resource contentFragmentsFolder = resourceResolver.getResource(CF_PATH);
        LOGGER.debug("contentFragmentsFolder fetched from content fragment path",contentFragmentsFolder);
        if(contentFragmentsFolder.hasChildren() && contentFragmentsFolder!=null){
            Iterable<Resource> iterable = contentFragmentsFolder.getChildren();
            for(Resource child: iterable){

                if(child==null){
                    return;
                }

                if ("jcr:content".equals(child.getName())) {
                    continue;
                }
                AlertContentFragmentEntity alertContentFragmentEntity = new AlertContentFragmentEntity();
                ContentFragment contentFragment = child.adaptTo(ContentFragment.class);
                String alert = contentFragment.getElement(ALERT).getContent();
                String message = contentFragment.getElement(MESSAGE).getContent();

                alertContentFragmentEntity.setAlert(alert);
                alertContentFragmentEntity.setMessage(message);

                LOGGER.debug("alertContentFragmentEntity to be added in List: ",alertContentFragmentEntity);

                fragmentEntities.add(alertContentFragmentEntity);

            }
        }

    }

}
