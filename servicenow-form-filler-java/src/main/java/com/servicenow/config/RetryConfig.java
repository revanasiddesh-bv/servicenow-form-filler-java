package com.servicenow.config;

public class RetryConfig {
    private boolean enabled;
    private int maxAttempts;
    private int delayBetweenAttempts;

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    
    public int getMaxAttempts() { return maxAttempts; }
    public void setMaxAttempts(int maxAttempts) { this.maxAttempts = maxAttempts; }
    
    public int getDelayBetweenAttempts() { return delayBetweenAttempts; }
    public void setDelayBetweenAttempts(int delayBetweenAttempts) { 
        this.delayBetweenAttempts = delayBetweenAttempts; 
    }
}
