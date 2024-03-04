package com.training.aem.core.models.impl;

import com.training.aem.core.entities.FaqEntity;
import com.training.aem.core.models.FaqComponentModel;
import lombok.Data;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Data
@Model(adaptables = {SlingHttpServletRequest.class},
        adapters = {FaqComponentModel.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FaqComponentModelImpl implements FaqComponentModel {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(FaqComponentModel.class);

    List<FaqEntity> faqList = new ArrayList<>();


    @ChildResource
    Resource faqFields;

    /**
     * Initializes the FAQ component by populating the FAQ list.
     * This method extracts FAQ questions and answers from the provided resource and populates the FAQ list.
     */
    @PostConstruct
    public void init(){
        if(faqFields!=null && faqFields.hasChildren()){
            for(Resource res: faqFields.getChildren()){
                FaqEntity faqEntity = new FaqEntity();
                ValueMap valueMap = res.getValueMap();
                if(valueMap.containsKey("faqQuestion")){
                    faqEntity.setQuestion(valueMap.get("faqQuestion", String.class));
                }

                if(valueMap.containsKey("faqAnswer")){
                    faqEntity.setAnswer(valueMap.get("faqAnswer", String.class));
                }

                LOGGER.debug("FAQ Entity to be added in faqList: {}",faqEntity);
                faqList.add(faqEntity);
            }
        }
    }

}
