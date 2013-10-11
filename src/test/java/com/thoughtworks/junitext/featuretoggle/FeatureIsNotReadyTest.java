package com.thoughtworks.junitext.featuretoggle;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.fail;

@FeatureToggle(feature = "IncompleteFeature")
public class FeatureIsNotReadyTest {
    @Rule
    public FeatureToggleRule featureToggleRule = new FeatureToggleRule();

    @Test
    public void shouldNotRunThisTest() throws Exception {
        fail();
    }
}
