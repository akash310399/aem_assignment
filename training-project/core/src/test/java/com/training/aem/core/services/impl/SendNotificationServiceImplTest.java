package com.training.aem.core.services.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class SendNotificationServiceImplTest {

    @InjectMocks
    SendNotificationServiceImpl notificationService;

    AemContext ctx = new AemContext();

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(SendNotificationServiceImpl.class);
    }

    @Test
    void setTaskNotification() {



    }
}