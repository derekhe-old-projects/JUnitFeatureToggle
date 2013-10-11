package com.thoughtworks.junitext.featuretoggle;

import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.util.Properties;

public class FeatureToggleRule implements TestRule {

    public static final String TOGGLE_OFF = "OFF";
    public static final String DEFAULT_PROPERTY_FILENAME = "feature-toggle.properties";

    @Override
    public Statement apply(Statement base, Description description) {
        FeatureToggle annotationOnMethod = description.getAnnotation(FeatureToggle.class);
        FeatureToggle annotationOnClass = description.getTestClass().getAnnotation(FeatureToggle.class);

        if (annotationOnMethod == null && annotationOnClass != null)
            annotationOnMethod = annotationOnClass;

        if (annotationOnMethod != null && isToggleOff(annotationOnMethod)) {
            return new IgnoredMethodStatement(annotationOnMethod.feature());
        } else {
            return base;
        }
    }

    class IgnoredMethodStatement extends Statement {
        private final String featureName;

        public IgnoredMethodStatement(String featureName) {
            this.featureName = featureName;
        }

        @Override
        public void evaluate() throws Throwable {
            throw new AssumptionViolatedException("This is ignored due to " + featureName + " is turned off");
        }
    }

    private boolean isToggleOff(FeatureToggle featureToggle) {
        return TOGGLE_OFF.equals(getToggleValue(featureToggle));
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
