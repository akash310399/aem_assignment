package com.training.aem.core.services.impl;

import com.day.cq.mailer.MailingService;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.day.cq.notification.api.Notification;
import com.day.cq.notification.api.NotificationService;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.PageService;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component(service = PageService.class)
public class PageServiceImpl implements PageService {



    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    Replicator replicator;

    @Reference
    MessageGatewayService messageGatewayService;

    private static final String RECIPIENT_MAIL = "akash31aks@gmail.com";
    private static final String SERVICE_USER = "useruser";


    @Reference
    NotificationService notificationService;

    private static final String PARENT_PAGE_PATH = "/content/training-project/us";

    private static final String PAGE_TEMPLATE = "/conf/training-project/settings/wcm/templates/page-content";

    @Override
    public void createPage(ProductEntity productEntity) {


        try (ResourceResolver resourceResolver = getResourceResolver()){

            Session session = resourceResolver.adaptTo(Session.class);

            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            Page createdPage = pageManager.create(PARENT_PAGE_PATH,
                    productEntity.getTitle(),PAGE_TEMPLATE,
                    productEntity.getTitle(),true);
            replicatePage(session,createdPage);
            sendMailToAdmin();
        } catch (WCMException | LoginException e) {
            throw new RuntimeException(e);
        }


    }

    private ResourceResolver getResourceResolver() throws LoginException {
        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, SERVICE_USER);
        return resourceResolverFactory.getServiceResourceResolver(map);
    }

    private void replicatePage(Session session, Page page) {
        try {
            replicator.replicate(session, ReplicationActionType.ACTIVATE, page.getPath());
        } catch (ReplicationException e) {
            throw new RuntimeException("Failed to replicate the page", e);
        }
    }



    private void sendMailToAdmin(){
        try {
            MessageGateway<HtmlEmail> messageGateway = messageGatewayService.getGateway(HtmlEmail.class);

            Date date = new Date();

            HtmlEmail email = new HtmlEmail();
            email.addTo(RECIPIENT_MAIL);
            email.setSubject("Page Created By Scheduler at "+ date.getTime() );
            email.setTextMsg("A new page has been created by"
                    + SERVICE_USER + "under"
                    + PARENT_PAGE_PATH);

            messageGateway.send(email);

        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    private void notificationTOAdmin(){

    }

}
