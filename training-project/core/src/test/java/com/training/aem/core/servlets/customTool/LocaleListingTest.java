package com.training.aem.core.servlets.customTool;

import com.training.aem.core.entities.KeyValue;
import com.training.aem.core.services.OrphanAssetService;
import com.training.aem.core.services.impl.OrphanAssetServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class LocaleListingTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    LocaleListing localeListing;

    @Mock
    SlingHttpServletResponse response;

    @Mock
    SlingHttpServletRequest request;

    @Mock
    OrphanAssetServiceImpl orphanAssetService;

    private final String ROOT_PATH = "/content";

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(LocaleListing.class);
    }

    @Test
    void doGet() throws ServletException, IOException {
        String locale = "us";
        List<KeyValue> localeList = new ArrayList<>();
        localeList.add(new KeyValue("key","value"));
        localeList.add(new KeyValue("key2","value"));
        when(request.getParameter(eq("project"))).thenReturn(locale);


        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        when(orphanAssetService.getDropDown(request.getResourceResolver(),ROOT_PATH + "/" + locale)).thenReturn(localeList);
        localeListing.doGet(request, response);

    }
}