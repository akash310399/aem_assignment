package com.training.aem.core.servlets.customTool;

import com.adobe.granite.ui.components.ds.DataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.training.aem.core.entities.KeyValue;
import com.training.aem.core.services.OrphanAssetService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Component(service = Servlet.class, property = {
        "sling.servlet.paths="+ "/bin/locale",
        "sling.servlet.methods="+ HttpConstants.METHOD_GET
        })
public class LocaleListing extends SlingSafeMethodsServlet {

    @Reference
    OrphanAssetService orphanAssetService;

    private final String ROOT_PATH = "/content";

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String locale = request.getParameter("project");
        if (locale == null || locale.isEmpty()) {
            sendResponse(response, SlingHttpServletResponse.SC_BAD_REQUEST, "Project parameter is missing");
            return;
        }

        try {
            List<KeyValue> localeList = orphanAssetService.getDropDown(request.getResourceResolver(), ROOT_PATH + "/" + locale);
            if (localeList != null && !localeList.isEmpty()) {
                sendResponse(response, SlingHttpServletResponse.SC_OK, localeList);
            } else {
                sendResponse(response, SlingHttpServletResponse.SC_NO_CONTENT, "No locales found for the project");
            }
        } catch (Exception e) {
            sendResponse(response, SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void sendResponse(SlingHttpServletResponse response, int status, Object data) throws IOException {
        response.setStatus(status);
        response.getWriter().write(new Gson().toJson(data));
    }
}
