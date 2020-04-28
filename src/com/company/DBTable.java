package com.company;

import java.util.ArrayList;

public class DBTable {
    private ArrayList<TableAttribute> Table;
    private int RecordCnt;

    public void main(){
        Table=new ArrayList<>();
        this.setRecordCnt(0);
    }

    public void setRecordCnt(int recordCnt) {
        RecordCnt = recordCnt;
    }

    public int getRecordCnt() {
        return RecordCnt;
    }

    public void setTable(TableAttribute attributes) {
        Table.add(RecordCnt,attributes);
        RecordCnt++;
    }

    public ArrayList<TableAttribute> getTable() {
        return Table;
    }
}
