package org.basic.jobs.job;

import org.basic.jobs.job.AbstractJobRepositoryTest;
import org.junit.Test;

public class JobRepositoryTest extends AbstractJobRepositoryTest {

    @Test
    public void testJobPersistenceInMySQL() throws Exception {
        super.testJobPersistence();
    }

    @Test
    public void testFindAllFromMySQL() throws Exception {
        super.testFindAllJobs();
    }

    @Test
    public void testGetByJobIdlFromMySQL() throws Exception {
        super.testFindByJobId();
    }
}
