package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class DBTable implements Serializable {
    private static final long serialVersionUID = -6743567631108323096L;
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
        return ((index==this.tableAttributes.size()) && !findswitch)?-1:index;
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

    public void insertData(ArrayList<TokenElement>token,BufferedWriter out) throws IOException {

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
            if(valueLiteral.getValue().length()>this.tableAttributes.get((i - 3) / 2).getMaxStringSize()){
                this.tableAttributes.get((i - 3) / 2).setMaxStringSize(valueLiteral.getValue().length());
            }
            i++;
        }
    }

    public void insertData(DBTable target,int index){
        if(this.getTable().size()==0){
            for(int i=0;i<target.getTable().size();i++){
                TableAttribute tableAttribute=new TableAttribute();
                tableAttribute.setRecordCnt(0);
                tableAttribute.setVariableType(target.getTable().get(i).getVariableType());
                tableAttribute.setAttributeName(target.getTable().get(i).getAttributeName());
                this.getTable().add(tableAttribute);
            }
        }
        for(int i=0;i<target.getTable().size();i++){
            ValueLiteral valueLiteral;
            valueLiteral=target.getTable().get(i).getAttribute().get(index);
            this.getTable().get(i).getAttribute().add(valueLiteral);
            if(valueLiteral.getValue().length()>this.getTable().get(i).getMaxStringSize()){
                this.getTable().get(i).setMaxStringSize(valueLiteral.getValue().length());
            }
        }
        this.setRecordCnt(this.RecordCnt+1);
    }

    public void insertData(DBTable left,DBTable right,int leftindex,int rightindex,BufferedWriter out){
        ValueLiteral valueLiteral=new ValueLiteral();
        valueLiteral.setValue((RecordCnt+1)+"");
        valueLiteral.setVariableType(VariableType.ID);
        RecordCnt++;
        this.tableAttributes.get(0).add(valueLiteral);

        for(int i=1;i<left.getTable().size();i++){
            valueLiteral=new ValueLiteral();
            valueLiteral.setValue(left.getTable().get(i).getAttribute().get(leftindex).getValue());
            valueLiteral.setVariableType(left.getTable().get(i).getAttribute().get(leftindex).getVariableType());
            this.tableAttributes.get(i).add(valueLiteral);
        }

        for(int i=left.getTable().size();i<(left.getTable().size()+right.getTable().size()-1);i++){
            valueLiteral=new ValueLiteral();
            valueLiteral.setValue(right.getTable().get(i-left.getTable().size()+1).getAttribute().get(rightindex).getValue());
            valueLiteral.setVariableType(right.getTable().get(i-left.getTable().size()+1).getAttribute().get(rightindex).getVariableType());
            this.tableAttributes.get(i).add(valueLiteral);
        }

    }

    public void printAll(BufferedWriter out) throws IOException {
        for (TableAttribute tableAttribute : this.tableAttributes) {
            out.write("" + tableAttribute.getAttributeName());
            for(int i=0;i<(tableAttribute.getMaxStringSize()
                    -tableAttribute.getAttributeName().length()+4);i++){
                out.write(" ");
            }
        }
        out.newLine();
        for(int i=0;i<this.tableAttributes.get(0).getAttribute().size();i++){
            for (TableAttribute tableAttribute : this.tableAttributes) {
                out.write(tableAttribute.getAttribute().get(i).getValue());
                for(int j=0;j<(tableAttribute.getMaxStringSize()
                        -tableAttribute.getAttribute().get(i).getValue().length()+4);j++){
                    out.write(" ");
                }
            }
            out.newLine();
        }
        out.newLine();
    }

    public void printPart(ArrayList<Integer> index,BufferedWriter out) throws IOException {
        index  = new ArrayList<Integer>(new HashSet<Integer>(index));
        for(int i=0;i<index.size();i++){
            out.write("" + this.tableAttributes.get(index.get(i)).getAttributeName());
            for(int j=0;j<(this.tableAttributes.get(i).getMaxStringSize()-
                    this.tableAttributes.get(index.get(i)).getAttributeName().length()+4);j++){
                out.write(" ");
            }
        }
        out.newLine();
        for(int i=0;i<this.tableAttributes.get(0).getAttribute().size();i++){
            for(int j=0;j<index.size();j++){
                out.write(this.tableAttributes.get(index.get(j)).getAttribute().get(i).getValue());
                for(int k=0;k<((this.tableAttributes.get(i).getMaxStringSize()
                        -this.tableAttributes.get(index.get(j)).getAttribute().get(i).getValue().length())+4)
                        ;k++){
                    out.write(" ");
                }
            }
            out.newLine();
        }
        out.newLine();
    }
}
