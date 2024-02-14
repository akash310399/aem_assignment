package com.training.aem.core.servlets;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.day.cq.mcm.emailprovider.EmailService;
import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.PageService;
import com.training.aem.core.services.ReadExcelDataService;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = {Servlet.class},
        property = {
                "sling.servlet.paths=/bin/testing",
                "sling.servlet.methods=GET"
        })
public class TestingServlet extends SlingSafeMethodsServlet {

    @Reference
    PageService pageService;

    @Reference
    ReadExcelDataService readExcelDataService;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    MessageGatewayService messageGatewayService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

//        ProductEntity productEntity = new ProductEntity();
//        productEntity.setTitle("Name");
//
//        pageService.createPage(productEntity);
//
//        response.getWriter().write("servlet called.....");
//
//        try {
//            ResourceResolver resourceResolver = getResourceResolver();
//            readExcelDataService.getDataFromExcel(resourceResolver,"/content/dam/training/mobileSheet.xlsx");
//        } catch (LoginException e) {
//            throw new RuntimeException(e);
//        }

        MessageGateway<HtmlEmail> messageGateway = messageGatewayService.getGateway(HtmlEmail.class);

        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.set




    }

    private ResourceResolver getResourceResolver() throws LoginException {
        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, "useruser");
        return resourceResolverFactory.getServiceResourceResolver(map);
    }
}
