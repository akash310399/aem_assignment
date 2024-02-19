package com.training.aem.core.servlets;

import com.google.gson.Gson;
import com.training.aem.core.entities.AlertContentFragmentEntity;
import com.training.aem.core.services.AlertService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Content Fragment Search",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/alert"
})
public class AlertComponentServlet extends SlingSafeMethodsServlet {


    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private AlertService alertService;

    /**
     * Handles HTTP GET requests.
     *
     * @param request  The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @throws ServletException If an error occurs while processing the servlet request.
     * @throws IOException      If an I/O error occurs while handling the servlet request.
     */
    @Override
    protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {

        response.setContentType("Application/json");
        List<AlertContentFragmentEntity> fragmentEntities =
                alertService.getAlertsFromContentFragment();

        response.getWriter().write(new Gson().
                toJson(fragmentEntities));



    }

}
