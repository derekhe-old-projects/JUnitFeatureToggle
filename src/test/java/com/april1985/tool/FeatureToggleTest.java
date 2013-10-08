package com.april1985.tool;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class FeatureToggleTest {
    @Test
    public void shouldNotRunTestMethodWhenFeatureIsOff() throws Throwable {
        Method method = FeatureIsPartiallyReadyTest.class.getMethod("shouldNotRunThisTest");
        FrameworkMethod frameworkMethod = new FrameworkMethod(method);

        Statement statement = new FeatureToggleRule().apply(mock(Statement.class), frameworkMethod, null);

        assertTrue(statement.getClass().equals(FeatureToggleRule.IgnoredMethodStatement.class));
    }

    @Test
    public void shouldNotRunAllTestMethodsWhenFeatureIsOffOnClass() throws Throwable {
        Method method = FeatureIsNotReadyTest.class.getMethod("shouldNotRunThisTest");
        FrameworkMethod frameworkMethod = new FrameworkMethod(method);

        Statement statement = new FeatureToggleRule().apply(mock(Statement.class), frameworkMethod, null);

        assertTrue(statement.getClass().equals(FeatureToggleRule.IgnoredMethodStatement.class));
    }

    @Test
    public void shouldRunTestWhenNoFeatureToggleAnnotation() throws NoSuchMethodException, InitializationError {
        Method method = FeatureIsPartiallyReadyTest.class.getMethod("shouldRunThisTest");
        FrameworkMethod frameworkMethod = new FrameworkMethod(method);

        Statement mockStatement = mock(Statement.class);
        Statement statement = new FeatureToggleRule().apply(mockStatement, frameworkMethod, null);

        assertThat(statement, is(mockStatement));
    }
}
