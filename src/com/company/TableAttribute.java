package com.company;

import java.util.ArrayList;

public class TableAttribute<E> {
    private ArrayList<E> Attribute;
    private int RecordCnt;

    private void main(){
        Attribute=new ArrayList<>();
        this.setRecordCnt(0);
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
}
