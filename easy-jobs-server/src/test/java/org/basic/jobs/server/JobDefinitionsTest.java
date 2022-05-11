package org.basic.jobs.server;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.basic.jobs.server.JobDefinitions;

public class JobDefinitionsTest {

    @Test
    public void testGetSimpleName() throws Exception {
        String simpleName = JobDefinitions.getSimpleNameFrom("org.mycompany.jobs.MyFirstJob");
        assertThat(simpleName).isEqualTo("MyFirstJob");

        simpleName = JobDefinitions.getSimpleNameFrom("MySecondJob");
        assertThat(simpleName).isEqualTo("MySecondJob");
    }

}