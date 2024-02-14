package com.training.aem.core.models.impl;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class PagePropertyModelImplTest {

    private final AemContext ctx = new AemContext();

    @InjectMocks
    PagePropertyModelImpl pagePropertyModel;

    @Mock
    Page currentPage;

    @Mock
    Resource resource;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(PagePropertyModelImpl.class);
    }


    @Test
    void init() {
        String propertyName = "productId";
        String propertyValue = "2";
        Page page = resource.adaptTo(Page.class);
        Page currentPage = ctx.currentPage(page);
        when(currentPage.getProperties().get(propertyName)).thenReturn(propertyValue);

        // When
        String actualValue = pagePropertyModel.getPropertyFromPageOrAncestor(propertyName);

    }
}