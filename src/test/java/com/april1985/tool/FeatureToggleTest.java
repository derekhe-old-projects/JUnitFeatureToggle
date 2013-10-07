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
    public void testShouldIgnoreTestMethodWhenFeatureIsOff() throws Exception {
        Method method = RACIsPartiallyReadyTest.class.getMethod("shouldIgnoreThisTest");
        FrameworkMethod frameworkMethod = new FrameworkMethod(method);

        RunNotifier runNotifier = mock(RunNotifier.class);

        FeatureToggleRunner featureToggleRunner = new FeatureToggleRunner(RACIsNotReadyTest.class);
        featureToggleRunner.runChild(frameworkMethod, runNotifier);

        verify(runNotifier).fireTestIgnored(any(Description.class));
    }

    @Test
    public void testShouldIgnoreAllTestMethodsWhenFeatureIsOffOnClass() throws Exception {

        Method method = RACIsNotReadyTest.class.getMethod("shouldNotRunThisTest");
        FrameworkMethod frameworkMethod = new FrameworkMethod(method);

        RunNotifier runNotifier = mock(RunNotifier.class);

        FeatureToggleRunner featureToggleRunner = new FeatureToggleRunner(RACIsNotReadyTest.class);
        featureToggleRunner.runChild(frameworkMethod, runNotifier);

        verify(runNotifier).fireTestIgnored(any(Description.class));
    }

    @Test
    public void testShouldRunTestWhenNoFeatureToggleAnnotation() throws NoSuchMethodException, InitializationError {
        Method method = RACIsPartiallyReadyTest.class.getMethod("shouldRunThisTest");
        FrameworkMethod frameworkMethod = new FrameworkMethod(method);

        RunNotifier runNotifier = mock(RunNotifier.class);

        FeatureToggleRunner featureToggleRunner = new FeatureToggleRunner(RACIsPartiallyReadyTest.class);
        featureToggleRunner.runChild(frameworkMethod, runNotifier);

        verify(runNotifier, times(0)).fireTestIgnored(any(Description.class));
    }
}
