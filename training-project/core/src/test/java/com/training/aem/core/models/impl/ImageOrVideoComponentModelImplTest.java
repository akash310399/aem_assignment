package com.training.aem.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ImageOrVideoComponentModelImplTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    ImageOrVideoComponentModelImpl imageOrVideoComponentModel;


    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ImageOrVideoComponentModelImpl.class);
    }

    @Test
    void testingImageAndVideo(){
        imageOrVideoComponentModel.getContentType();
        imageOrVideoComponentModel.getImage();
        imageOrVideoComponentModel.getVideo();


    }






}