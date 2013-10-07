package com.april1985.tool;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(FeatureToggleRunner.class)
@FeatureToggle(feature = "NotCompletedFeature")
public class FeatureIsNotReadyTest {
    @Test
    public void shouldNotRunThisTest() throws Exception {
        fail();
    }
}
