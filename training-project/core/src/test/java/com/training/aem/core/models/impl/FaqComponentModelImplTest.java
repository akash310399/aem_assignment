package com.training.aem.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class FaqComponentModelImplTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    FaqComponentModelImpl faqComponentModel;

    @Mock
    Resource faqFields;

    @Mock
    Resource childResource;

    @Mock
    ValueMap valueMap;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(FaqComponentModelImpl.class);
        when(faqFields.hasChildren()).thenReturn(true);
        when(faqFields.getChildren()).thenReturn(Collections.singletonList(childResource));
        when(childResource.getValueMap()).thenReturn(valueMap);
        when(valueMap.containsKey("faqQuestion")).thenReturn(true);
        when(valueMap.get("faqQuestion", String.class)).thenReturn("Test Question");
        when(valueMap.containsKey("faqAnswer")).thenReturn(true);
        when(valueMap.get("faqAnswer", String.class)).thenReturn("Test Answer");
    }

    @Test
    void getFaqList() {
        faqComponentModel.init();
        assertEquals(1, faqComponentModel.getFaqList().size());
        assertEquals("Test Question", faqComponentModel.getFaqList().get(0).getQuestion());
        assertEquals("Test Answer", faqComponentModel.getFaqList().get(0).getAnswer());
    }
}