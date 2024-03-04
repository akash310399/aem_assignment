package com.training.aem.core.services.impl;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.jcr.Session;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class AlertServiceImplTest {

    AemContext ctx = new AemContext();

    @Mock
    ResourceResolver resourceResolver;


    @Mock
    ResourceResolverFactory resourceResolverFactory;

    public static final String CF_PATH = "/content/dam/training-project/content-fragments";
    public static final String ALERT = "alert";
    public static final String MESSAGE = "message";

    @InjectMocks
    AlertServiceImpl alertService;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(AlertServiceImpl.class);
    }

    @Test
    void getAlertsFromContentFragment() throws LoginException {

        String alert = "alert1";
        String message = "message";
        String name = "name";

        Session session = mock(Session.class);

        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE,"useruser");
        when(resourceResolverFactory.getServiceResourceResolver(map)).thenReturn(resourceResolver);
        ctx.registerService(ResourceResolverFactory.class,resourceResolverFactory);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        Resource contentFragmentsFolder = mock(Resource.class);

        Resource child1 = mock(Resource.class);
        Resource child2 = mock(Resource.class);
        when(child1.getName()).thenReturn("someName");
        when(child2.getName()).thenReturn("jcr:content");

        when(resourceResolver.getResource(eq(CF_PATH))).thenReturn(contentFragmentsFolder);
        when(contentFragmentsFolder.hasChildren()).thenReturn(true);

        Iterable<Resource> children = mock(Iterable.class);
        when(children.iterator()).thenReturn(Arrays.asList(child1, child2).iterator());
        when(contentFragmentsFolder.getChildren()).thenReturn(children);

        ContentFragment contentFragment = mock(ContentFragment.class);
        when(child1.adaptTo(ContentFragment.class)).thenReturn(contentFragment);

        ContentElement alertElement = mock(ContentElement.class);
        ContentElement messageElement = mock(ContentElement.class);

        when(contentFragment.getElement(ALERT)).thenReturn(alertElement);
        when(alertElement.getContent()).thenReturn(alert);
        when(contentFragment.getElement(MESSAGE)).thenReturn(messageElement);
        when(messageElement.getContent()).thenReturn(message);
        when(contentFragment.getName()).thenReturn("someName");


        alertService.getAlertsFromContentFragment();
    }
}