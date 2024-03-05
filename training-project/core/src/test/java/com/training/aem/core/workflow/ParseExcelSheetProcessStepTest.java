package com.training.aem.core.workflow;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.training.aem.core.CommonConstants;
import com.training.aem.core.services.ReadExcelDataService;
import com.training.aem.core.services.impl.ReadExcelDataServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.LoginException;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class ParseExcelSheetProcessStepTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    ParseExcelSheetProcessStep parseExcelSheetProcessStep;

    @Mock
    ReadExcelDataServiceImpl readExcelDataService;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    WorkflowData mockWorkflowData;

    @Mock
    WorkItem mockWorkItem;

    @Mock
    WorkflowSession workflowSession;

    @Mock
    ResourceResolverFactory resourceResolverFactory;

    @Mock
    MetaDataMap metaDataMap;


    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ParseExcelSheetProcessStep.class);
    }

    @Test
    void execute() throws WorkflowException, LoginException {
        String path = "/content/abc";

        when(mockWorkflowData.getPayloadType()).thenReturn(CommonConstants.TYPE_JCR_PATH);
        when(mockWorkflowData.getPayload()).thenReturn(CommonConstants.DAM_ROOT_PATH+"something");

        when(mockWorkItem.getWorkflowData()).thenReturn(mockWorkflowData);

        Session session = mock(Session.class);

        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE,"useruser");
        when(resourceResolverFactory.getServiceResourceResolver(map)).thenReturn(resourceResolver);
        ctx.registerService(ResourceResolverFactory.class,resourceResolverFactory);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        parseExcelSheetProcessStep.execute(mockWorkItem,workflowSession,metaDataMap);
    }
}