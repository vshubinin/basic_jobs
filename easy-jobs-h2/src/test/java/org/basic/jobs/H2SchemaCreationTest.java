package org.basic.jobs;

import org.basic.jobs.SchemaCreationTest;
import org.junit.Test;

public class H2SchemaCreationTest extends SchemaCreationTest {

    @Test
    public void canExecuteSQLScriptAgainstH2Database() throws Exception {
        super.canExecuteSQLScript();
    }

}