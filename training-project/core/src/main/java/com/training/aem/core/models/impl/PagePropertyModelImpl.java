package com.training.aem.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.training.aem.core.models.PagePropertyModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = {SlingHttpServletRequest.class},
        adapters = {PagePropertyModel.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PagePropertyModelImpl implements PagePropertyModel {


    @ScriptVariable
    Page currentPage;

    private static final String INHERITED_SCRIPT_PROPERTY = "customInheritedScript";

    @ValueMapValue
    @Default(values = StringUtils.EMPTY)
    String customInheritedScript;

    public String getCustomInheritedScript(){
        if(StringUtils.isNotBlank(customInheritedScript)){
            return customInheritedScript;
        }

        return getPropertyFromPageOrAncestor(INHERITED_SCRIPT_PROPERTY);

    }

    @PostConstruct
    public void init(){

    }

    public String getPropertyFromPageOrAncestor(String propertyName) {
        String propertyValue = currentPage.getProperties().get(propertyName, String.class);
        if (propertyValue == null && currentPage.getParent() != null) {
            propertyValue = getPropertyFromAncestors(currentPage.getParent(), propertyName);
        }

        return propertyValue;
    }

    private String getPropertyFromAncestors(Page page, String propertyName) {
        String propertyValue = page.getProperties().get(propertyName, String.class);

        if (propertyValue == null && page.getParent() != null) {
            propertyValue = getPropertyFromAncestors(page.getParent(), propertyName);
        }

        return propertyValue;
    }
}
