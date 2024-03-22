package com.training.aem.core.servlets.customTool;

import com.google.gson.Gson;
import com.training.aem.core.CommonConstants;
import com.training.aem.core.entities.OrphanAsset;
import com.training.aem.core.services.OrphanAssetService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = Servlet.class, property = {
        "sling.servlet.paths=" + "/bin/fetch"
})
public class FetchOrphanAssets extends SlingAllMethodsServlet {

    @Reference
    OrphanAssetService orphanAssetService;

    private static final String PARENT_CONTENT_PATH = "/content/dam";
    private static final String SERVICE_USER = "useruser";

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String projectName = request.getParameter("projectName");
        String locale = request.getParameter("locale");

        if (StringUtils.isAllBlank(projectName, locale)) {
            sendResponse(response, SlingHttpServletResponse.SC_BAD_REQUEST, "Project name or locale is missing.");
            return;
        }

        String finalPath = PARENT_CONTENT_PATH + CommonConstants.SLASH + projectName + CommonConstants.SLASH + locale;
        List<OrphanAsset> orphanAssets = orphanAssetService.getAllOrphanAssets(request.getResourceResolver(), finalPath);

        sendResponse(response, SlingHttpServletResponse.SC_OK, orphanAssets);
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String projectName = request.getParameter("projectName");
        String locale = request.getParameter("locale");
        String finalPath = PARENT_CONTENT_PATH + CommonConstants.SLASH + projectName + CommonConstants.SLASH + locale;

        clearOrphanAssets(response, finalPath);
    }

    private void clearOrphanAssets(SlingHttpServletResponse response, String finalPath) throws IOException {
        if (StringUtils.isNotBlank(finalPath)) {
            try (ResourceResolver resourceResolver = getResourceResolver()) {
                orphanAssetService.clearOrphanAsset(resourceResolver, finalPath);
                sendResponse(response, SlingHttpServletResponse.SC_OK, "Asset deleted successfully");
            } catch (LoginException e) {
                sendResponse(response, SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Login Exception occurred.");
            }
        } else {
            sendResponse(response, SlingHttpServletResponse.SC_BAD_REQUEST, "Asset path is missing");
        }
    }

    private void sendResponse(SlingHttpServletResponse response, int status, Object data) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(new Gson().toJson(data));
    }

    private ResourceResolver getResourceResolver() throws LoginException {
        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, SERVICE_USER);
        return resourceResolverFactory.getServiceResourceResolver(map);
    }
}
