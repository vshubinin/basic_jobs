package org.basic.jobs.user;

import org.basic.jobs.user.AbstractUserRepositoryTest;
import org.junit.Test;

public class UserRepositoryTest extends AbstractUserRepositoryTest {

    @Test
    public void testGetUserFromH2() throws Exception {
        super.testGetUserByNameAndPassword();
    }

}
