package com.servicenow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormField {
    private String value;
    private String type;
    private String lookup;

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getLookup() { return lookup; }
    public void setLookup(String lookup) { this.lookup = lookup; }
}
