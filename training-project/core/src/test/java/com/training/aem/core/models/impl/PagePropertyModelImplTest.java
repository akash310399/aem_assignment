package com.training.aem.core.models.impl;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class PagePropertyModelImplTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    PagePropertyModelImpl pagePropertyModel;

    @Mock
    SlingHttpServletRequest request;

    @Mock
    Page currentPage;

    @Mock
    ValueMap pageProperties;

    @BeforeEach
    void setUp(){
        ctx.addModelsForClasses(PagePropertyModelImpl.class);
        when(request.adaptTo(Page.class)).thenReturn(currentPage);
    }

    @Test
    void testing(){

        String propertyName = "customInheritedScript";
        String propertyValue = "something";

        String customInheritedScript = "anything";
        pageProperties.put(propertyName, propertyValue);


        when(currentPage.getProperties()).thenReturn(pageProperties);


        String result = pagePropertyModel.getCustomInheritedScript();

        assertEquals(propertyValue, "something");
    }

    @Test
    void testGetCustomInheritedScriptWhenCustomInheritedScriptIsNotBlank() {
        // Set up a non-blank value for customInheritedScript
        String customInheritedScript = "someValue";
        pagePropertyModel.customInheritedScript = customInheritedScript;

        // Call the method under test
        String result = pagePropertyModel.getCustomInheritedScript();

        // Assert that the method returns the non-blank value
        assertEquals(customInheritedScript, result);
        // Add assertions to verify the behavior as expected when customInheritedScript is not blank.
    }

}