package com.training.aem.core.services;

import com.adobe.granite.ui.components.ds.DataSource;
import com.day.cq.dam.api.Asset;
import com.training.aem.core.entities.KeyValue;
import com.training.aem.core.entities.OrphanAsset;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

public interface OrphanAssetService {

    List<KeyValue> getDropDown(ResourceResolver resolver, String path);

    void clearOrphanAsset(ResourceResolver resourceResolver, String finalPath);

    List<OrphanAsset> getAllOrphanAssets(ResourceResolver resourceResolver, String finalPath);
}
