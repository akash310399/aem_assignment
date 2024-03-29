package com.training.aem.core.servlets.customTool;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.google.gson.Gson;
import com.training.aem.core.entities.KeyValue;
import com.training.aem.core.services.OrphanAssetService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.iterators.TransformIterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Component(
        service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "= Dynamic Drop Down",
                "sling.servlet.paths=" + "/apps/projectListing"
        })
public class ProjectListing extends SlingSafeMethodsServlet {

    private final String ROOT_PATH = "/content";

    @Reference
    OrphanAssetService orphanAssetService;


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            List<KeyValue> dropDownList = orphanAssetService.getDropDown(resourceResolver, ROOT_PATH);

            if (dropDownList != null && !dropDownList.isEmpty()) {
                sendResponse(response, SlingHttpServletResponse.SC_OK, dropDownList);
            } else {
                sendResponse(response, SlingHttpServletResponse.SC_NO_CONTENT, "No projects found");
            }
        } catch (Exception e) {
            sendResponse(response, SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void sendResponse(SlingHttpServletResponse response, int status, Object data) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(new Gson().toJson(data));
    }

}
