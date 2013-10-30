package com.april1985.tool;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FeatureToggleTest {

    private Properties prop;
    private FeatureToggleRule rule;
    private Statement statement;

    @Before
    public void setUp() throws Exception {
        rule = new FeatureToggleRule();
        prop = mock(Properties.class);
        statement = mock(Statement.class);
        rule.setProp(prop);
    }

    @Test
    public void shouldNotIgnoreMethodGivenToggleAnnotationOnMethodAndTurnOnProperty() {
        given(prop.getProperty("IncompleteFeature")).willReturn("ON");

        Statement result = rule.apply(statement, Description.createTestDescription(FeatureIsNotReadyTest.class, "test", FeatureIsNotReadyTest.class.getAnnotations()));

        assertThat(result, is(statement));
    }

    @Test
    public void shouldIgnoreMethodGivenToggleAnnotationOnMethodAndTurnOffProperty() {
        given(prop.getProperty("IncompleteFeature")).willReturn("OFF");

        Statement result = rule.apply(statement, Description.createTestDescription(FeatureIsNotReadyTest.class, "test", FeatureIsNotReadyTest.class.getAnnotations()));

        assertThat(result, not(statement));
    }

    @Test
    public void shouldNotIgnoreMethodGivenToggleAnnotationOnClassAndTurnOnProperty() {
        given(prop.getProperty("IncompleteFeature")).willReturn("ON");

        Statement result = rule.apply(statement, Description.createTestDescription(FeatureIsPartiallyReadyTest.class, "test"));

        assertThat(result, is(statement));
    }
}
