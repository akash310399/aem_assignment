package com.training.aem.core.schedulers;

import com.training.aem.core.configs.PageConfig;
import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.PageService;
import com.training.aem.core.services.ProductDetailService;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.function.Predicate;

@Component(service = {Runnable.class},immediate = true)
@Designate(ocd = PageConfig.class)
public class PageScheduler implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(PageScheduler.class);
    private int schedulerId;

    @Reference
    ProductDetailService productDetailService;

    @Reference
    PageService pageService;

    private static final String BASE_URL = "https://fakestoreapi.com/products/";

    private static String PRODUCT_ID = "1";


    @Reference
    Scheduler scheduler;

    @Activate
    protected void active(PageConfig config){
        schedulerId = config.schedulerName().hashCode();
        addScheduler(config);
    }

    @Deactivate
    protected void deactivate(){
        removeScheduler();

    }

    public void addScheduler(PageConfig config){
        ScheduleOptions scheduleOptions = scheduler.EXPR(config.cronExpression());
        scheduleOptions .name(String.valueOf(schedulerId));
        //scheduleOptions.canRunConcurrently(false);
        scheduler.schedule(this,scheduleOptions);


    }

    protected void removeScheduler() {
        scheduler.unschedule(String.valueOf(schedulerId));
    }

    @Override
    public void run() {
        log.info("page creation started here....");
        ProductEntity productEntity = productDetailService.getProductsData(BASE_URL + PRODUCT_ID);
        log.info("Product Entity of got from third party API: ", productEntity);
        productEntity.setTitle("Testingpage");
        try {
            pageService.createPage(productEntity);
        } catch (Exception e) {
            log.error("Error during page creation:", e);
        }

        log.info("Page Created...");
    }
}
