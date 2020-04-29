package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class TableAttribute implements Serializable {
    private ArrayList<ValueLiteral> Attribute;
    private int RecordCnt;
    private String AttributeName;
    private VariableType variableType;

    public TableAttribute(){
        this.setRecordCnt(0);
        this.AttributeName=null;
        this.Attribute=new ArrayList<>();
    }

    public void add(ValueLiteral value){
        Attribute.add(value);
        RecordCnt++;
    }

    public ArrayList<ValueLiteral> getAttribute() {
        return Attribute;
    }

    public int getRecordCnt() {
        return RecordCnt;
    }

    public void setRecordCnt(int recordCnt) {
        RecordCnt = recordCnt;
    }

    public void setAttribute(ArrayList<ValueLiteral> attribute) {
        Attribute = attribute;
    }

    public void setAttributeName(String attributeName) {
        AttributeName = attributeName;
    }

    public String getAttributeName() {
        return AttributeName;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    public VariableType getVariableType() {
        return variableType;
    }
}
