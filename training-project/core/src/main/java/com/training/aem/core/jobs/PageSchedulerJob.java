package com.training.aem.core.jobs;

import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;

@Component(service = {JobConsumer.class}
        ,property = {})
public class PageSchedulerJob implements JobConsumer {
    @Override
    public JobResult process(Job job) {
        return null;
    }
}
