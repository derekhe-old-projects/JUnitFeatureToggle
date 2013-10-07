package com.april1985.tool;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

public class FeatureToggleTest {
    @Test
    public void shouldNotRunTestMethodWhenFeatureIsOff() throws Exception {
        Method method = FeatureIsPartiallyReadyTest.class.getMethod("shouldNotRunThisTest");
        FrameworkMethod frameworkMethod = new FrameworkMethod(method);
        RunNotifier runNotifier = mock(RunNotifier.class);
        FeatureToggleRunner featureToggleRunner = new FeatureToggleRunner(FeatureIsNotReadyTest.class);

        featureToggleRunner.runChild(frameworkMethod, runNotifier);

        verify(runNotifier).fireTestIgnored(any(Description.class));
    }

    @Test
    public void shouldNotRunAllTestMethodsWhenFeatureIsOffOnClass() throws Exception {
        Method method = FeatureIsNotReadyTest.class.getMethod("shouldNotRunThisTest");
        FrameworkMethod frameworkMethod = new FrameworkMethod(method);
        RunNotifier runNotifier = mock(RunNotifier.class);
        FeatureToggleRunner featureToggleRunner = new FeatureToggleRunner(FeatureIsNotReadyTest.class);

        featureToggleRunner.runChild(frameworkMethod, runNotifier);

        verify(runNotifier).fireTestIgnored(any(Description.class));
    }

    @Test
    public void shouldRunTestWhenNoFeatureToggleAnnotation() throws NoSuchMethodException, InitializationError {
        Method method = FeatureIsPartiallyReadyTest.class.getMethod("shouldRunThisTest");
        FrameworkMethod frameworkMethod = new FrameworkMethod(method);
        RunNotifier runNotifier = mock(RunNotifier.class);
        FeatureToggleRunner featureToggleRunner = new FeatureToggleRunner(FeatureIsPartiallyReadyTest.class);

        featureToggleRunner.runChild(frameworkMethod, runNotifier);

        verify(runNotifier, times(0)).fireTestIgnored(any(Description.class));
    }
}
