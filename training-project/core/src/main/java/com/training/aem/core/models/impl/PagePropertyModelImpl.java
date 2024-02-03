package com.training.aem.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.training.aem.core.models.PagePropertyModel;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

import javax.annotation.PostConstruct;

public class PagePropertyModelImpl implements PagePropertyModel {


    @ScriptVariable
    Page currentPage;

    @PostConstruct
    public void init(){

    }
}
