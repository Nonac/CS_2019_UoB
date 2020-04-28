package com.company;

import java.util.ArrayList;

public class DBDatabase {
    private ArrayList<DBTable> database;
    private int RecordCnt;

    private DBDatabase(){
        this.setRecordCnt(0);
        this.database=new ArrayList<>();
    }

    public void setRecordCnt(int recordCnt) {
        RecordCnt = recordCnt;
    }

    public int getRecordCnt() {
        return RecordCnt;
    }

    public ArrayList<DBTable> getDatabase() {
        return database;
    }

    public void setDatabase(ArrayList<DBTable> database) {
        this.database = database;
    }
}
