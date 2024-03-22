package com.training.aem.core.servlets.customTool;

import com.training.aem.core.entities.KeyValue;
import com.training.aem.core.services.OrphanAssetService;
import com.training.aem.core.services.impl.OrphanAssetServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ProjectListingTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    ProjectListing projectListing;

    @Mock
    OrphanAssetServiceImpl orphanAssetService;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    MockSlingHttpServletRequest request;

    @Mock
    MockSlingHttpServletResponse response;

    private final String ROOT_PATH = "/content";

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ProjectListing.class);
    }

    @Test
    void doGet() throws ServletException, IOException {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        List<KeyValue> dropDownList = new ArrayList<>();
        dropDownList.add(new KeyValue("key","value"));
        dropDownList.add(new KeyValue("key2","value"));
        when(request.getResourceResolver()).thenReturn(resourceResolver);
        when(orphanAssetService.getDropDown(resourceResolver,ROOT_PATH)).thenReturn(dropDownList);

        when(response.getWriter()).thenReturn(printWriter);

        projectListing.doGet(request,response);

    }

    @Test
    void doGetNegative() throws ServletException, IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        List<KeyValue> dropDownList = new ArrayList<>();
        when(request.getResourceResolver()).thenReturn(resourceResolver);
        when(orphanAssetService.getDropDown(resourceResolver,ROOT_PATH)).thenReturn(dropDownList);

        when(response.getWriter()).thenReturn(printWriter);
        projectListing.doGet(request,response);
    }
}