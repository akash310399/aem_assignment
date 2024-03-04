package com.training.aem.core.services.impl;

import com.adobe.granite.taskmanagement.Task;
import com.adobe.granite.taskmanagement.TaskManager;
import com.adobe.granite.taskmanagement.TaskManagerException;
import com.adobe.granite.taskmanagement.TaskManagerFactory;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class SendNotificationServiceImplTest {

    @InjectMocks
    SendNotificationServiceImpl notificationService;

    @Mock
    ResourceResolver resourceResolver;

    AemContext ctx = new AemContext();

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(SendNotificationServiceImpl.class);
    }

    @Test
    void setTaskNotification() throws TaskManagerException {

        TaskManager taskManager = mock(TaskManager.class);
        TaskManagerFactory taskManagerFactory = mock(TaskManagerFactory.class);

        when(resourceResolver.adaptTo(TaskManager.class)).thenReturn(taskManager);
        when(taskManager.getTaskManagerFactory()).thenReturn(taskManagerFactory);

        Task task = mock(Task.class);
        when(taskManagerFactory.newTask(anyString())).thenReturn(task);

        notificationService.setTaskNotification(resourceResolver,"aksh","aksh");



    }
}