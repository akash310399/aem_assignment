package com.training.aem.core.servlets;

import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.PageService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = {Servlet.class},
        property = {
                "sling.servlet.paths=/bin/testing",
                "sling.servlet.methods=GET"
        })
public class TestingServlet extends SlingSafeMethodsServlet {

    @Reference
    PageService pageService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle("Name");

        pageService.createPage(productEntity);

        response.getWriter().write("servlet called.....");
    }
}
