package com.servicenow.config;

public class TimeoutConfig {
    private int navigation;
    private int element;
    private int script;

    public int getNavigation() { return navigation; }
    public void setNavigation(int navigation) { this.navigation = navigation; }
    
    public int getElement() { return element; }
    public void setElement(int element) { this.element = element; }
    
    public int getScript() { return script; }
    public void setScript(int script) { this.script = script; }
}
