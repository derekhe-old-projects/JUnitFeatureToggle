package com.april1985.tool;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.fail;

@RunWith(FeatureToggleRunner.class)
public class FeatureIsPartiallyReadyTest {
    @Test
    @FeatureToggle(feature = "NotCompletedFeature")
    public void shouldNotRunThisTest() {
        fail();
    }

    @Test
    public void shouldRunThisTest() {

    }
}