package com.training.aem.core.jobs;

import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.PageService;
import com.training.aem.core.services.ProductDetailService;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = {JobConsumer.class}
        ,property = {JobConsumer.PROPERTY_TOPICS+"=training/pageJob"})
public class PageSchedulerJob implements JobConsumer {

    private static final Logger log = LoggerFactory.getLogger(PageSchedulerJob.class);


    @Reference
    ProductDetailService productDetailService;

    @Reference
    PageService pageService;

    private static final String BASE_URL = "https://fakestoreapi.com/products/";
    private static String PRODUCT_ID = "1";

    @Override
    public JobResult process(Job job) {
        log.info("Page creation started here....");
        ProductEntity productEntity = productDetailService.getProductsData(BASE_URL+PRODUCT_ID);
        log.info("Product Entity obtained from third-party API: {}", productEntity);
        productEntity.setTitle("Testing-page");
        try {
            pageService.createPage(productEntity);
        } catch (Exception e) {
            log.error("Error during page creation:", e);
            return JobResult.FAILED;
        }

        log.info("Page Created...");
        return JobResult.OK;
    }
}
