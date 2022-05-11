package org.basic.jobs.execution;

import org.basic.jobs.execution.AbstractJobExecutionRepositoryTest;
import org.junit.Test;

public class JobExecutionRepositoryTest extends AbstractJobExecutionRepositoryTest {

    @Test
    public void testJobExecutionPersistenceInMySQL() throws Exception {
        super.testJobExecutionPersistence();
    }

    @Test
    public void testJobExecutionUpdateInMySQL() throws Exception {
        super.testJobExecutionUpdate();
    }

    @Test
    public void testFindAllJobExecutionsFromMySQL() throws Exception {
        super.testFindAllJobExecutions();
    }
}
