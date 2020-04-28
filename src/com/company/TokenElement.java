package com.company;

public class TokenElement {
    private TokenType property;
    private String value;
    private VariableType variableType;

    private void main(String[] args) {

    }

    public void setProperty(TokenType property) {
        this.property = property;
    }

    public TokenType getProperty() {
        return property;
    }

    public void setValue(String value) {
        if(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("false")){
            this.setVariableType(VariableType.BOOLEAN);
            this.value = value;
        }else if(value.charAt(0)=='\''&&value.charAt(value.length()-1)=='\''){
            this.setVariableType(VariableType.STRING);
            this.value = value.substring(1,value.length()-1);
        }else if(this.isStr2Num(value)){
            this.setVariableType(VariableType.INTEGER);
            this.value = value;
        }else if(value.matches("^[0-9]+(.[0-9]+)?$")){
            this.setVariableType(VariableType.FLOAT);
            this.value=value;
        }else {
            this.value=value;
        }
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    public String getValue() {
        return value;
    }

    public boolean propertyIsValue(){
        return this.property == TokenType.VALUE;
    }

    public boolean isStr2Num(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
