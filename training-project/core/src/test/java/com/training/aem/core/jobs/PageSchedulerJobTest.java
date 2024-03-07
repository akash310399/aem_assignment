package com.training.aem.core.jobs;

import com.training.aem.core.entities.ProductEntity;
import com.training.aem.core.services.ProductDetailService;
import com.training.aem.core.services.impl.PageServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.event.jobs.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class PageSchedulerJobTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    PageSchedulerJob pageSchedulerJob;

    @Mock
    Job job;

    @Mock
    ProductDetailService productDetailService;

    @Mock
    PageServiceImpl pageService;

    private static final String BASE_URL = "https://fakestoreapi.com/products/";
    private static String PRODUCT_ID = "1";

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(PageSchedulerJob.class);
    }

    @Test
    void process() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setTitle("assa");

        when(productDetailService.getProductsData(BASE_URL + PRODUCT_ID)).thenReturn(productEntity);


        pageSchedulerJob.process(job);
    }
}