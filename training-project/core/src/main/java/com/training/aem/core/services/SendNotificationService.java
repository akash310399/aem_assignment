package com.training.aem.core.services;

import org.apache.sling.api.resource.ResourceResolver;

public interface SendNotificationService {

    void setTaskNotification(ResourceResolver resourceResolver, String createdBy, String pagePath);
}
