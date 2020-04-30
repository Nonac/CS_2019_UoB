package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class TableAttribute implements Serializable {
    private static final long serialVersionUID = -6743567631108323096L;
    private ArrayList<ValueLiteral> Attribute;
    private int RecordCnt;
    private String AttributeName;
    private VariableType variableType;
    private int MaxStringSize;

    public TableAttribute(){
        this.setRecordCnt(0);
        this.AttributeName=null;
        this.Attribute=new ArrayList<>();
        this.MaxStringSize=0;
    }

    public void setMaxStringSize(int maxStringSize) {
        MaxStringSize = maxStringSize;
    }

    public int getMaxStringSize() {
        return MaxStringSize;
    }

    public void add(ValueLiteral value){
        Attribute.add(value);
        RecordCnt++;
        if(value.getValue().length()>MaxStringSize){
            this.setMaxStringSize(value.getValue().length());
        }
    }

    public int findIndex(ValueLiteral valueLiteral){
        int cnt=-1;
        while (cnt<this.Attribute.size()-1){
            if(Attribute.get(cnt+1).getValue().equals(valueLiteral.getValue())){
                return cnt+1;
            }else {
                cnt++;
            }
        }
        return cnt;
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
        if(attributeName.length()>this.MaxStringSize){
            this.setMaxStringSize(attributeName.length());
        }
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
