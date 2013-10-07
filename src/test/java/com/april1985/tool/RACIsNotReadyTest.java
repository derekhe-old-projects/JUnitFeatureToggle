package com.april1985.tool;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@FeatureToggle(feature = "RAC")
@RunWith(FeatureToggleRunner.class)
public class RACIsNotReadyTest {
    @Test
    public void shouldNotRunThisTest() throws Exception {
        fail();
    }
}
