package com.training.aem.core.services.impl;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.training.aem.core.CommonConstants;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class OrphanAssetServiceImplTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    OrphanAssetServiceImpl orphanAssetService;

    @Mock
    ResourceResolver resourceResolver;



    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(OrphanAssetServiceImpl.class);

    }

    @Test
    void getDropDown() {
        String path = "somePath";
        String primaryType = "cq:Page";
        ValueMap valueMap = mock(ValueMap.class);
        when(valueMap.get(JcrConstants.JCR_PRIMARYTYPE)).thenReturn(primaryType);
        when(valueMap.get(JcrConstants.JCR_PRIMARYTYPE).toString()).thenReturn(primaryType);

        Resource resource = mock(Resource.class);
        Resource child1 = mock(Resource.class);
        Resource child2 = mock(Resource.class);
        when(child1.getName()).thenReturn("someName");
        when(child2.getName()).thenReturn("jcr:content");
        when(child1.getValueMap()).thenReturn(valueMap);
        when(child2.getValueMap()).thenReturn(valueMap);

        Iterable<Resource> children = mock(Iterable.class);

        when(resourceResolver.getResource(path)).thenReturn(resource);
        when(resource.getChildren()).thenReturn(children);
        when(children.iterator()).thenReturn(Arrays.asList(child1,child2).iterator());
        when(resource.getChildren()).thenReturn(children);
        orphanAssetService.getDropDown(resourceResolver,path);
    }

    @Test
    void clearOrphanAsset() throws RepositoryException {
        // Mock objects
//        String finalPath = "/content/training/us";
//        Asset asset = mock(Asset.class);
//        List<Asset> orphanAssetsToDelete = Collections.singletonList(asset);
//        Session session = mock(Session.class);
//        ResourceResolver resourceResolver = mock(ResourceResolver.class);
//
//        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
//
//        orphanAssetService.clearOrphanAsset(resourceResolver, finalPath);
//
//        verify(resourceResolver).adaptTo(Session.class);
//        verify(session).refresh(false);
//        verify(session).removeItem(finalPath);
//        verify(session).save();

    }

    @Test
    void getAllOrphanAssets() {
    }
}