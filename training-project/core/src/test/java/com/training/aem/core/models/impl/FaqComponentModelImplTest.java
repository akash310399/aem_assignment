package com.training.aem.core.models.impl;

import com.training.aem.core.entities.FaqEntity;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, AemContextExtension.class})
class FaqComponentModelImplTest {

    @Mock
    private Resource resource;

    @Mock
    private ResourceResolver resourceResolver;

    FaqComponentModelImpl faqComponentModel;

    private List<Resource> faqChildren;

    public static final String FAQ_COMPONENT_MODEL_JSON = "FaqComponentModel.json";

    private final AemContext ctx = new AemContext(ResourceResolverType.JCR_MOCK);

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(FaqComponentModelImpl.class);
        ctx.load().json("/core.training.aem.core.models.impl/"+
                FAQ_COMPONENT_MODEL_JSON,"/content/faq");

        ctx.currentResource("/content/faq");
        ctx.currentResource().adaptTo(FaqComponentModelImpl.class);


    }

    @Test
    void init(){
        faqComponentModel = getModel("/content/faq");
        faqComponentModel.getFaqFields();
        faqComponentModel.init();
    }

    private FaqComponentModelImpl getModel(String currentResource){
        ctx.currentResource(currentResource);
        return ctx.request().adaptTo(FaqComponentModelImpl.class);
    }


}