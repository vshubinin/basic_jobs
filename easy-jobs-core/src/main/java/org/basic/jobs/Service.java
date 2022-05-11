package org.basic.jobs;

import org.basic.jobs.execution.JobExecution;
import org.basic.jobs.execution.JobExecutionRepository;
import org.basic.jobs.execution.JobExecutionStatus;
import org.basic.jobs.job.Job;
import org.basic.jobs.job.JobExitStatus;
import org.basic.jobs.job.JobRepository;
import org.basic.jobs.request.JobExecutionRequest;
import org.basic.jobs.request.JobExecutionRequestRepository;
import org.basic.jobs.request.JobExecutionRequestStatus;
import org.springframework.transaction.annotation.Transactional;

import static org.basic.jobs.execution.JobExecution.newJobExecution;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Central service providing transactional methods to save/update job requests/executions together in a consistent way.
 */
@org.springframework.stereotype.Service
@Transactional
public class Service {

    private JobRepository jobRepository;
    private JobExecutionRepository jobExecutionRepository;
    private JobExecutionRequestRepository jobExecutionRequestRepository;

    public Service(JobExecutionRepository jobExecutionRepository, JobExecutionRequestRepository jobExecutionRequestRepository, JobRepository jobRepository) {
        this.jobExecutionRepository = jobExecutionRepository;
        this.jobExecutionRequestRepository = jobExecutionRequestRepository;
        this.jobRepository = jobRepository;
    }

    public void saveJobExecutionAndUpdateItsCorrespondingRequest(int requestId) {
        JobExecution jobExecution = newJobExecution().withRequestId(requestId).withJobExecutionStatus(JobExecutionStatus.RUNNING).withStartDate(LocalDateTime.now());
        jobExecutionRepository.save(jobExecution);
        JobExecutionRequest jobExecutionRequest = jobExecutionRequestRepository.findJobExecutionRequestById(requestId);
        jobExecutionRequest.setStatus(JobExecutionRequestStatus.SUBMITTED);
        jobExecutionRequestRepository.update(jobExecutionRequest);
    }

    public void updateJobExecutionAndItsCorrespondingRequest(int requestId, JobExitStatus jobExitStatus) {
        JobExecution jobExecution = jobExecutionRepository.findByJobExecutionRequestId(requestId);
        jobExecution.setJobExecutionStatus(JobExecutionStatus.FINISHED);
        jobExecution.setJobExitStatus(jobExitStatus);
        jobExecution.setEndDate(LocalDateTime.now());
        jobExecutionRepository.update(jobExecution);

        JobExecutionRequest jobExecutionRequest = jobExecutionRequestRepository.findJobExecutionRequestById(requestId);
        jobExecutionRequest.setStatus(JobExecutionRequestStatus.PROCESSED);
        jobExecutionRequest.setProcessingDate(LocalDateTime.now());
        jobExecutionRequestRepository.update(jobExecutionRequest);
    }

    public void updateJobExecutionRequestStatus(int requestId, JobExecutionRequestStatus jobExecutionRequestStatus) {
        JobExecutionRequest jobExecutionRequest = jobExecutionRequestRepository.findJobExecutionRequestById(requestId);
        if (jobExecutionRequest != null) {
            jobExecutionRequest.setStatus(jobExecutionRequestStatus);
            jobExecutionRequestRepository.update(jobExecutionRequest);
        }
    }

    @Transactional(readOnly = true)
    public Job findJobById(int jobId) {
        return jobRepository.findById(jobId);
    }

    @Transactional(readOnly = true)
    public List<JobExecutionRequest> findPendingRequests() {
        return jobExecutionRequestRepository.findJobExecutionRequestsByStatus(JobExecutionRequestStatus.PENDING);
    }

}
