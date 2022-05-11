package org.basic.jobs.execution;

import java.time.LocalDateTime;

import org.basic.jobs.job.JobExitStatus;

public class JobExecution {

    private int id;
    private int requestId;
    private JobExecutionStatus jobExecutionStatus;
    private JobExitStatus jobExitStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public JobExecution() {
    }

    public JobExecution(int requestId, JobExecutionStatus jobExecutionStatus, JobExitStatus jobExitStatus, LocalDateTime startDate, LocalDateTime endDate) {
        this.requestId = requestId;
        this.jobExecutionStatus = jobExecutionStatus;
        this.jobExitStatus = jobExitStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static JobExecution newJobExecution() {
        return new JobExecution();
    }

    public int getId() {
        return id;
    }

    public int getRequestId() {
        return requestId;
    }

    public JobExecutionStatus getJobExecutionStatus() {
        return jobExecutionStatus;
    }

    public JobExitStatus getJobExitStatus() {
        return jobExitStatus;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setJobExecutionStatus(JobExecutionStatus jobExecutionStatus) {
        this.jobExecutionStatus = jobExecutionStatus;
    }

    public void setJobExitStatus(JobExitStatus jobExitStatus) {
        this.jobExitStatus = jobExitStatus;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public JobExecution withRequestId(int requestId) {
        this.requestId = requestId;
        return this;
    }

    public JobExecution withJobExecutionStatus(JobExecutionStatus jobExecutionStatus) {
        this.jobExecutionStatus = jobExecutionStatus;
        return this;
    }

    public JobExecution withJobExitStatus(JobExitStatus jobExitStatus) {
        this.jobExitStatus = jobExitStatus;
        return this;
    }

    public JobExecution withStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public JobExecution withEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    @Override
    public String toString() {
        return "JobExecution {" +
                "id=" + id +
                ", requestId=" + requestId +
                ", jobExecutionStatus=" + jobExecutionStatus +
                ", jobExitStatus=" + jobExitStatus +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
