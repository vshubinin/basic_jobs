package org.basic.jobs.server;

import org.basic.jobs.Service;
import org.basic.jobs.job.JobExitStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

class DefaultJob implements Callable<JobExitStatus> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJob.class);

    private int requestId;
    private Service service;

    private Object target;
    private Method method;

    DefaultJob(int requestId, Object target, Method method, Service service) {
        this.requestId = requestId;
        this.target = target;
        this.method = method;
        this.service = service;
    }

    public final JobExitStatus call() {
        try {
            LOGGER.info("Processing job execution request with id " + requestId);
            method.invoke(target);
            service.updateJobExecutionAndItsCorrespondingRequest(requestId, JobExitStatus.SUCCEEDED);
            LOGGER.info("Successfully processed job execution request with id " + requestId);
            return JobExitStatus.SUCCEEDED;
        } catch (Exception e) {
            LOGGER.error("Processing of job execution request with id " + requestId + " has failed", e);
            service.updateJobExecutionAndItsCorrespondingRequest(requestId, JobExitStatus.FAILED);
            return JobExitStatus.FAILED;
        }
    }

}
