package com.servicenow.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;

public class FormData {
    private Map<String, FormField> fields = new HashMap<>();

    @JsonAnySetter
    public void setField(String name, FormField value) {
        fields.put(name, value);
    }

    public Map<String, FormField> getFields() {
        return fields;
    }
}
