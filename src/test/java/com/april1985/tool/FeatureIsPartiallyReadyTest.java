package com.april1985.tool;

import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.fail;

public class FeatureIsPartiallyReadyTest {
    @Rule
    public FeatureToggleRule featureToggleRule = new FeatureToggleRule();

    @Test
    @FeatureToggle(feature = "NotCompletedFeature")
    public void shouldNotRunThisTest() {
        fail();
    }

    @Test
    public void shouldRunThisTest() {

    }
}