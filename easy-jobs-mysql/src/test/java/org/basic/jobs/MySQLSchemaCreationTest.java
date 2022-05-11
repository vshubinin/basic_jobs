package org.basic.jobs;

import org.basic.jobs.SchemaCreationTest;
import org.junit.Test;

public class MySQLSchemaCreationTest extends SchemaCreationTest {

    @Test
    public void canExecuteSQLScriptAgainstMySQLDatabase() throws Exception {
        super.canExecuteSQLScript();
    }

}