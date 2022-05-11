package org.basic.jobs.request;

import org.basic.jobs.request.AbstractJobExecutionRequestRepositoryTest;
import org.junit.Test;

public class JobExecutionRequestRepositoryTest extends AbstractJobExecutionRequestRepositoryTest {

    @Test
    public void testJobExecutionRequestPersistenceInMySQL() throws Exception {
        super.testJobExecutionRequestPersistence();
    }

    @Test
    public void testFindJobExecutionRequestsByStatusFromMySQL() throws Exception {
        super.testFindJobExecutionRequestsByStatus();
    }

    @Test
    public void testUpdateJobExecutionRequestInMySQL() throws Exception {
        super.testUpdateJobExecutionRequest();
    }

    @Test
    public void testFindAllJobExecutionRequestsFromMySQL() throws Exception {
        super.testFindAllJobExecutionRequests();
    }
}
