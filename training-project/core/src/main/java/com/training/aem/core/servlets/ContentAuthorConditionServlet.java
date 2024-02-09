package com.training.aem.core.servlets;

import com.drew.lang.annotations.NotNull;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Content Author Condition Servlet",
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/content-author-condition",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=GET"
        })
public class ContentAuthorConditionServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        boolean isInContentAuthorGroup = false;
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            if (resourceResolver != null) {
                isInContentAuthorGroup = false;
                User user = resourceResolver.adaptTo(User.class);
                Iterator<Group> groupIterator = user.memberOf();
                while (groupIterator.hasNext()) {
                    Group group = groupIterator.next();
                    if (group.getID().equals("content-authors")) {
                        isInContentAuthorGroup = true;
                        break;
                    }
                }
            }
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().write(Boolean.toString(isInContentAuthorGroup));

    }
}
