package org.basic.jobs.job;

import org.basic.jobs.job.AbstractJobRepositoryTest;
import org.junit.Test;


public class JobRepositoryTest extends AbstractJobRepositoryTest {

    @Test
    public void testJobPersistenceInH2() throws Exception {
        super.testJobPersistence();
    }

    @Test
    public void testFindAllFromH2() throws Exception {
        super.testFindAllJobs();
    }

    @Test
    public void testFindByJobIdlFromH2() throws Exception {
        super.testFindByJobId();
    }
}
