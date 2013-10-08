package com.april1985.tool;

import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.util.Properties;

public class FeatureToggleRule implements MethodRule {
    public static final String TOGGLE_OFF = "OFF";
    public static final String DEFAULT_PROPERTY_FILENAME = "feature-toggle.properties";

    class IgnoredMethodStatement extends Statement {
        private final String featureName;

        public IgnoredMethodStatement(String featureName) {
            this.featureName = featureName;
        }

        @Override
        public void evaluate() throws Throwable {
            throw new AssumptionViolatedException("This class is ignored due to " + featureName + " is turned off");
        }
    }

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        FeatureToggle annotationOnMethod = method.getAnnotation(FeatureToggle.class);
        FeatureToggle annotationOnClass = method.getMethod().getDeclaringClass().getAnnotation(FeatureToggle.class);

        if (annotationOnMethod == null && annotationOnClass != null)
            annotationOnMethod = annotationOnClass;

        String featureToggle = getToggleValue(annotationOnMethod);

        if (annotationOnMethod != null && TOGGLE_OFF.equals(featureToggle)) {
            return new IgnoredMethodStatement(annotationOnMethod.feature());
        } else {
            return base;
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
