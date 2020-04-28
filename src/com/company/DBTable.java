package com.company;

import java.util.ArrayList;

public class DBTable {
    private ArrayList<TableAttribute> Table;
    private int RecordCnt;
    private String TableName;

    public void main(){
        this.Table=new ArrayList<>();
        this.setRecordCnt(0);
        this.TableName=null;
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

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }
}
