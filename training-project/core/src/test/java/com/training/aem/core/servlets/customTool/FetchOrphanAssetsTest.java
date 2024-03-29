package com.training.aem.core.servlets.customTool;

import com.training.aem.core.entities.OrphanAsset;
import com.training.aem.core.services.impl.OrphanAssetServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.commons.lang3.StringUtils;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.jcr.Session;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class FetchOrphanAssetsTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    FetchOrphanAssets fetchOrphanAssets;

    @Mock
    SlingHttpServletResponse response;

    @Mock
    SlingHttpServletRequest request;

    @Mock
    OrphanAssetServiceImpl orphanAssetService;

    @Mock
    ResourceResolverFactory resourceResolverFactory;

    @Mock
    ResourceResolver resourceResolver;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(FetchOrphanAssets.class);
    }

    @Test
    void doGet() throws ServletException, IOException {
        String projectName = "we-retail";
        String locale = "us";
        String finalPath = "us";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        List<OrphanAsset> orphanAssets = new ArrayList<>();

        when(request.getParameter("projectName")).thenReturn(projectName);
        when(request.getParameter("locale")).thenReturn(locale);


        when(orphanAssetService.getAllOrphanAssets(any(),anyString())).thenReturn(orphanAssets);
        when(response.getWriter()).thenReturn(printWriter);
        fetchOrphanAssets.doGet(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException, LoginException {
        String projectName = "we-retail";
        String locale = "us";
        String finalPath = "us";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(request.getParameter("projectName")).thenReturn(projectName);
        when(request.getParameter("locale")).thenReturn(locale);

        Session session = mock(Session.class);

        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE,"useruser");
        when(resourceResolverFactory.getServiceResourceResolver(map)).thenReturn(resourceResolver);
        ctx.registerService(ResourceResolverFactory.class,resourceResolverFactory);
        when(response.getWriter()).thenReturn(printWriter);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);



        fetchOrphanAssets.doPost(request,response);
    }
}