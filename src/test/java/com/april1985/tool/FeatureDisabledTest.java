package com.april1985.tool;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(ConcordionRunner.class)
@FeatureToggle(feature = "IncompleteFeature")
public class FeatureDisabledTest {
    @Rule
    public FeatureToggleRule featureToggleRule = new FeatureToggleRule();

    public String getGreeting() {
        return "In complete feature";
    }
}
