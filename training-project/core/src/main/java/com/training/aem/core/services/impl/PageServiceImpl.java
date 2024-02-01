package com.training.aem.core.services.impl;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.PageService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;

@Component(service = {PageService.class})
public class PageServiceImpl implements PageService {



    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    Replicator replicator;



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
        } catch (WCMException | LoginException e) {
            throw new RuntimeException(e);
        }


    }

    private ResourceResolver getResourceResolver() throws LoginException {
        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, "useruser");
        return resourceResolverFactory.getServiceResourceResolver(map);
    }

    private void replicatePage(Session session, Page page) {
        try {
            replicator.replicate(session, ReplicationActionType.ACTIVATE, page.getPath());
        } catch (ReplicationException e) {
            throw new RuntimeException("Failed to replicate the page", e);
        }
    }

}
