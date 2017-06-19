package com.photobooth.config;

import com.photobooth.util.StateFlowConfiguration;

/**
 * Stores all configuration options
 */
public class Config {

    private static Config instance;

    private Config() {}

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }

        return instance;
    }

    private StateFlowConfiguration stateFlowConfiguration;

    public StateFlowConfiguration getStateFlowConfiguration() {
        return stateFlowConfiguration;
    }

    public void setStateFlowConfiguration(StateFlowConfiguration stateFlowConfiguration) {
        this.stateFlowConfiguration = stateFlowConfiguration;
    }
}
