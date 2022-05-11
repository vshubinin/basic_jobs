package org.basic.jobs.server;

import org.basic.jobs.job.JobDefinition;
import org.basic.jobs.server.JobDefinitions;
import org.junit.Test;

public class ValidatorTest {

    private JobDefinitions.Validator validator = new JobDefinitions.Validator(ValidatorTest.class.getClassLoader());

    @Test(expected = JobDefinitions.InvalidJobDefinitionException.class)
    public void jobClassMustProvideDefaultConstructor() throws Exception {
        // given
        JobDefinition jobDefinition = new JobDefinition();
        jobDefinition.setName("my job");
        jobDefinition.setClazz("org.jeasy.jobs.server.JobWithNoDefaultConstructor");

        // when
        validator.validate(jobDefinition);

        // then
        // expected exception
    }

    @Test(expected = JobDefinitions.InvalidJobDefinitionException.class)
    public void executionMethodMustBePublic() throws Exception {
        // given
        JobDefinition jobDefinition = new JobDefinition();
        jobDefinition.setName("my job");
        jobDefinition.setMethod("doWork");
        jobDefinition.setClazz("org.jeasy.jobs.server.JobWithNoPublicExecutionMethod");

        // when
        validator.validate(jobDefinition);

        // then
        // expected exception
    }

}