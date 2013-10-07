package com.april1985.tool;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.fail;

@RunWith(FeatureToggleRunner.class)
public class RACIsPartiallyReadyTest {
    @Test
    @FeatureToggle(feature = "RAC")
    public void shouldIgnoreThisTest() {
        fail();
    }

    @Test
    public void shouldRunThisTest() {

    }
}
