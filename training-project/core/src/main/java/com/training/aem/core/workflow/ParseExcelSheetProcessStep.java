package com.training.aem.core.workflow;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.training.aem.core.CommonConstants;
import com.training.aem.core.services.ReadExcelDataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component(service = WorkflowProcess.class,
        property = {"process.label=Custom Workflow Process for Excel Sheet" })
public class ParseExcelSheetProcessStep implements WorkflowProcess {

    private static final Logger log = LoggerFactory
            .getLogger(ParseExcelSheetProcessStep.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    ReadExcelDataService readExcelDataService;

    /**
     * Executes the workflow process step.
     *
     * @param workItem        The work item representing the current workflow instance.
     * @param workflowSession The workflow session associated with the current workflow instance.
     * @param metaDataMap     The metadata associated with the workflow process.
     * @throws WorkflowException If an error occurs during workflow execution.
     */
    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {

        final WorkflowData workFlowData = workItem.getWorkflowData();
        final String type = workFlowData.getPayloadType();
        final String dataPath = workFlowData.getPayload().toString();


        try(ResourceResolver resourceResolver = getResourceResolver()){
            if(StringUtils.equals(type, CommonConstants.TYPE_JCR_PATH) &&
                    StringUtils.startsWith(dataPath,CommonConstants.DAM_ROOT_PATH)){
                String assetPath = dataPath.replace(CommonConstants.ASSET_REPLACE_PATH,
                        StringUtils.EMPTY);
                readExcelDataService.getDataFromExcel(resourceResolver,assetPath);
            }
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Retrieves a resource resolver for the service user.
     *
     * @return A ResourceResolver instance.
     * @throws LoginException If the login process fails.
     */
    private ResourceResolver getResourceResolver() throws LoginException {
        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, "useruser");
        return resourceResolverFactory.getServiceResourceResolver(map);
    }
}
