package com.april1985.tool;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.io.IOException;
import java.util.Properties;

public class FeatureToggleRunner extends BlockJUnit4ClassRunner {
    public static final String TOGGLE_OFF = "OFF";
    public static final String DEFAULT_PROPERTY_FILENAME = "feature-toggle.properties";

    public FeatureToggleRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        FeatureToggle annotationOnMethod = method.getAnnotation(FeatureToggle.class);
        FeatureToggle annotationOnClass = method.getMethod().getDeclaringClass().getAnnotation(FeatureToggle.class);

        if (annotationOnMethod == null && annotationOnClass != null)
            annotationOnMethod = annotationOnClass;

        String featureToggle = getToggleValue(annotationOnMethod);

        if (annotationOnMethod != null && TOGGLE_OFF.equals(featureToggle)) {
            notifier.fireTestIgnored(describeChild(method));
        } else {
            super.runChild(method, notifier);
        }

    }

    private String getToggleValue(FeatureToggle annotation) {
        if (annotation == null) {
            return null;
        }

        Properties prop = new Properties();
        try {
            prop.load(ClassLoader.getSystemResourceAsStream(DEFAULT_PROPERTY_FILENAME));
            return prop.getProperty(annotation.feature());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
