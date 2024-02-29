package com.training.aem.core.services.impl;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.SendNotificationService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.resourceresolver.MockResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PrepareForTest;

import javax.jcr.Session;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class PageServiceImplTest {

    @InjectMocks
    PageServiceImpl pageService;

    @Mock
    ResourceResolver resourceResolver;


    @Mock
    ResourceResolverFactory resourceResolverFactory;
    AemContext ctx = new AemContext();

    @Mock
    Replicator replicator;

    @Mock
    MessageGatewayService messageGatewayService;

    @Mock
    SendNotificationServiceImpl notificationService;


    @Mock
    Page page;


    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(PageServiceImpl.class);
    }

    @Test
    void createPage() throws Exception {

        String parentPagePath = "/content/training-project/us";
        String pageTitle = "test";
        String pageTemplate = "/conf/training-project/settings/wcm/templates/page-content";

        String serviceUser = "useruser";

        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle(pageTitle);
        Session session = mock(Session.class);

        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE,"useruser");
        when(resourceResolverFactory.getServiceResourceResolver(map)).thenReturn(resourceResolver);
        ctx.registerService(ResourceResolverFactory.class,resourceResolverFactory);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        PageManager pageManager = mock(PageManager.class);
        when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);

        when(pageManager.create(
                eq(parentPagePath),
                eq(pageTitle),
                eq(pageTemplate),
                eq(pageTitle),
                eq(true)))
                .thenReturn(page);

        when(page.getPath()).thenReturn("/content/training-project/us/test-page");

        doNothing().when(replicator).replicate(eq(session),eq(ReplicationActionType.ACTIVATE),anyString());

        MessageGateway<HtmlEmail> messageGateway = mock(MessageGateway.class);
        when(messageGatewayService.getGateway(HtmlEmail.class)).thenReturn(messageGateway);


        pageService.createPage(productEntity);
    }
}