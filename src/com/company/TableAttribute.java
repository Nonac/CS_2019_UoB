package com.company;

import java.util.ArrayList;

public class TableAttribute<E> {
    private ArrayList<E> Attribute;
    private int RecordCnt;
    private String AttributeName;

    public TableAttribute(){
        Attribute=new ArrayList<>();
        this.setRecordCnt(0);
        this.AttributeName=null;
    }

    public void add(E value){
        Attribute.add(RecordCnt,value);
        RecordCnt++;
    }

    public ArrayList<E> getAttribute() {
        return Attribute;
    }

    public int getRecordCnt() {
        return RecordCnt;
    }

    public void setRecordCnt(int recordCnt) {
        RecordCnt = recordCnt;
    }

    public void setAttribute(ArrayList<E> attribute) {
        Attribute = attribute;
    }

    public void setAttributeName(String attributeName) {
        AttributeName = attributeName;
    }

    public String getAttributeName() {
        return AttributeName;
    }

}
