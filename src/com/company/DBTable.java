package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class DBTable implements Serializable {
    private ArrayList<TableAttribute> tableAttributes;
    private int RecordCnt;
    private String TableName;
    private Boolean EmptyTable = true;

    public DBTable() {
        this.tableAttributes = new ArrayList<>();
        this.setRecordCnt(0);
        this.TableName = null;
    }

    public void setRecordCnt(int recordCnt) {
        RecordCnt = recordCnt;
    }

    public int getRecordCnt() {
        return RecordCnt;
    }

    public void setTable(TableAttribute attributes) {
        tableAttributes.add(attributes);
    }

    public void deleteTableAttribute(String attributename){
        for(int i = 0 , len= tableAttributes.size();i<len;++i){
            if(tableAttributes.get(i).getAttributeName().equals(attributename)){
                tableAttributes.remove(i);
                --len;
                --i;
            }
        }
    }

    public int findIndexOfAttribute(String attributename){
        int index=0;
        boolean findswitch=false;
        for(TableAttribute tableAttribute:this.tableAttributes){
            if(tableAttribute.getAttributeName().equals(attributename)){
                findswitch=true;
                break;
            }
            index++;
        }
        return (index==this.tableAttributes.size() && findswitch)?0:index;
    }

    public ArrayList<TableAttribute> getTable() {
        return tableAttributes;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public void setTable(ArrayList<TableAttribute> table) {
        tableAttributes = table;
    }

    public Boolean getEmptyTable() {
        return EmptyTable;
    }

    public void setEmptyTable(Boolean emptyTable) {
        EmptyTable = emptyTable;
    }

    public void setTableAttributeFromInsertToken(ArrayList<TokenElement> token) {
        for (int i = 5; i < token.size() - 2; i++) {
            if (token.get(i).getVariableType() == VariableType.BOOLEAN) {
                this.tableAttributes.get((i - 3) / 2).setVariableType(VariableType.BOOLEAN);
            } else if (token.get(i).getVariableType() == VariableType.STRING) {
                this.tableAttributes.get((i - 3) / 2).setVariableType(VariableType.STRING);
            } else if (token.get(i).getVariableType() == VariableType.FLOAT) {
                this.tableAttributes.get((i - 3) / 2).setVariableType(VariableType.FLOAT);
            } else if (token.get(i).getVariableType() == VariableType.INTEGER) {
                this.tableAttributes.get((i - 3) / 2).setVariableType(VariableType.INTEGER);
            }
            i++;
        }
        this.EmptyTable=false;
    }

    public void insertDate(ArrayList<TokenElement>token,BufferedWriter out) throws IOException {

            ValueLiteral valueLiteral=new ValueLiteral();
            valueLiteral.setValue((RecordCnt+1)+"");
            valueLiteral.setVariableType(VariableType.ID);
            RecordCnt++;
            this.tableAttributes.get(0).add(valueLiteral);

        for (int i = 5; i < token.size() - 2; i++) {
            if(token.get(i).getVariableType()!=this.tableAttributes.get((i - 3) / 2).getVariableType()){
                out.write("Wrong literal type\n");
            }
            valueLiteral=new ValueLiteral();
            valueLiteral.setValue(token.get(i).getValue());
            valueLiteral.setVariableType(token.get(i).getVariableType());
            this.tableAttributes.get((i - 3) / 2).add(valueLiteral);
            i++;
        }
    }
}
