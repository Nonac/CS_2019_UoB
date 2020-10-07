package com.company;

import java.io.Serializable;

public class ValueLiteral implements Serializable {
    private static final long serialVersionUID = -6743567631108323096L;
    private VariableType variableType;
    private String value;

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
