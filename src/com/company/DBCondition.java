package com.company;

import com.sun.source.doctree.ValueTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class DBCondition {

    public DBTable calulateCondition(TokenElement left, TokenElement right, TokenType symbol, DBTable table,
    BufferedWriter out) throws IOException {
        switch (symbol){
            case EQUAL:
                return calEqual(left,right,table,out);
            case NOTEQUAL:
                return calNotEqual(left,right,table,out);
           case GREATER:
                return calGreat(left,right,table,out);
            case LESS:
                return calLess(left,right,table,out);
            case GREATEREQUAL:
                return calGreatEqual(left,right,table,out);
            case LESSEQUAL:
                return calLessEqual(left,right,table,out);
            case LIKE:
                return calLike(left,right,table,out);
        }
        return null;
    }
    
    public DBTable logicCondition(DBTable left,DBTable right,TokenType symbol,BufferedWriter out){
        switch (symbol){
            case AND:
                return logicAnd(left,right,out);
            case OR:
                return logicOr(left,right,out);
        }
        return null;
    }

    public DBTable logicOr(DBTable left, DBTable right, BufferedWriter out) {
        DBTable res=new DBTable();
        int left_index=0,right_index=0;
        while (left_index<left.getTable().get(0).getAttribute().size()
                &&right_index<right.getTable().get(0).getAttribute().size()) {
            if (Float.parseFloat(left.getTable().get(0).getAttribute().get(left_index).getValue())
                    < Float.parseFloat(right.getTable().get(0).getAttribute().get(right_index).getValue())) {
                res.insertData(left, left_index);
                left_index++;
            } else if (Float.parseFloat(left.getTable().get(0).getAttribute().get(left_index).getValue())
                    == Float.parseFloat(right.getTable().get(0).getAttribute().get(right_index).getValue())) {
                res.insertData(left, left_index);
                left_index++;
                right_index++;
            } else if (Float.parseFloat(left.getTable().get(0).getAttribute().get(left_index).getValue())
                    > Float.parseFloat(right.getTable().get(0).getAttribute().get(right_index).getValue())) {
                res.insertData(right, right_index);
                right_index++;
            }
        }
        while (left_index<left.getTable().get(0).getAttribute().size()){
            res.insertData(left, left_index);
            left_index++;
        }
        while (right_index<right.getTable().get(0).getAttribute().size()){
            res.insertData(right, right_index);
            right_index++;
        }
        res.setEmptyTable(false);
        res.setTableName(left.getTableName());
        return res;
    }

    public DBTable logicAnd(DBTable left, DBTable right, BufferedWriter out) {
        DBTable res=new DBTable();
        int left_index=0,right_index=0;
        while (left_index<left.getTable().get(0).getAttribute().size()
                &&right_index<right.getTable().get(0).getAttribute().size()){
            if(Float.parseFloat(left.getTable().get(0).getAttribute().get(left_index).getValue())
                    <Float.parseFloat(right.getTable().get(0).getAttribute().get(right_index).getValue())){
                //res.insertDate(left,left_index);
                left_index++;
            }else if(Float.parseFloat(left.getTable().get(0).getAttribute().get(left_index).getValue())
                    ==Float.parseFloat(right.getTable().get(0).getAttribute().get(right_index).getValue())){
                res.insertData(left,left_index);
                left_index++;
                right_index++;
            }else if(Float.parseFloat(left.getTable().get(0).getAttribute().get(left_index).getValue())
                    >Float.parseFloat(right.getTable().get(0).getAttribute().get(right_index).getValue())){
                //res.insertDate(right,left_index);
                right_index++;
            }
        }
        while (left_index<left.getTable().get(0).getAttribute().size()){
            res.insertData(left, left_index);
            left_index++;
        }
        while (right_index<right.getTable().get(0).getAttribute().size()){
            res.insertData(right, right_index);
            right_index++;
        }
        res.setEmptyTable(false);
        res.setTableName(left.getTableName());
        return res;
    }

    public DBTable logicAnd(DBTable left, DBTable right,String leftAttribute,String rightAttribut
            , BufferedWriter out) {
        DBTable res=new DBTable();
        int leftAttributeIndex=left.findIndexOfAttribute(leftAttribute);
        int rightAttributeIndex=right.findIndexOfAttribute(rightAttribut);

        TableAttribute newTableAttribute=new TableAttribute();
        newTableAttribute.setAttributeName("id");
        res.setTable(newTableAttribute);

        for(int i=1;i<(left.getTable().size());i++){
            TableAttribute tableAttribute=new TableAttribute();
            tableAttribute.setAttributeName(""+left.getTableName()+"."
                    +left.getTable().get(i).getAttributeName());
            res.setTable(tableAttribute);
        }
        for(int i=1;i<(right.getTable().size());i++){
            TableAttribute tableAttribute=new TableAttribute();
            tableAttribute.setAttributeName(""+right.getTableName()+"."
                    +right.getTable().get(i).getAttributeName());
            res.setTable(tableAttribute);
        }

        for(int i=0;i<left.getTable().get(leftAttributeIndex).getAttribute().size();i++){
            for(int j=0;j<right.getTable().get(rightAttributeIndex).getAttribute().size();j++){
                if(left.getTable().get(leftAttributeIndex).getAttribute().get(i).getValue().equals(
                        right.getTable().get(rightAttributeIndex).getAttribute().get(j).getValue())){
                    res.insertData(left,right,i,j,out);
                }
            }
        }

        res.setEmptyTable(false);
        res.setTableName(left.getTableName());
        return res;
    }

    public DBTable logicNot(DBTable left,DBTable right,BufferedWriter out){
        DBTable res=new DBTable();
        int left_index=0,right_index=0;
        while (left_index<left.getTable().get(0).getAttribute().size()
                &&right_index<right.getTable().get(0).getAttribute().size()){
            if(Float.parseFloat(left.getTable().get(0).getAttribute().get(left_index).getValue())
                    <Float.parseFloat(right.getTable().get(0).getAttribute().get(right_index).getValue())){
                res.insertData(left,left_index);
                left_index++;
            }else if(Float.parseFloat(left.getTable().get(0).getAttribute().get(left_index).getValue())
                    ==Float.parseFloat(right.getTable().get(0).getAttribute().get(right_index).getValue())){
                //res.insertData(left,left_index);
                left_index++;
                right_index++;
            }else if(Float.parseFloat(left.getTable().get(0).getAttribute().get(left_index).getValue())
                    >Float.parseFloat(right.getTable().get(0).getAttribute().get(right_index).getValue())){
                res.insertData(right,right_index);
                right_index++;
            }
        }
        while (left_index<left.getTable().get(0).getAttribute().size()){
            res.insertData(left, left_index);
            left_index++;
        }
        while (right_index<right.getTable().get(0).getAttribute().size()){
            res.insertData(right, right_index);
            right_index++;
        }
        res.setEmptyTable(false);
        res.setTableName(left.getTableName());
        return res;
    }

    private DBTable calLike(TokenElement left, TokenElement right, DBTable table, BufferedWriter out) throws IOException {
        int leftindex=table.findIndexOfAttribute(left.getValue());
        if(leftindex==0){
            out.write("Attribute does not exist\n");
            return null;
        }else if((table.getTable().get(leftindex).getVariableType()!=VariableType.STRING)
                ||(right.getVariableType()!=VariableType.STRING)){
            out.write("String expected\n");
            return null;
        }

        for(int i=0,len=table.getTable().get(leftindex).getAttribute().size();i<len;++i){
            if(!table.getTable().get(leftindex).getAttribute().get(i).getValue().contains(right.getValue())){
                for(int j=0;j<table.getTable().size();j++){
                    table.getTable().get(j).getAttribute().remove(i);
                }
                --len;
                --i;
                table.setRecordCnt(table.getRecordCnt()-1);
            }
        }
        return table;
    }

    private DBTable calLessEqual(TokenElement left, TokenElement right, DBTable table, BufferedWriter out) throws IOException {
        int leftindex=table.findIndexOfAttribute(left.getValue());
        if(leftindex==0){
            out.write("Attribute does not exist\n");
            return null;
        }else if((table.getTable().get(leftindex).getVariableType()!=VariableType.FLOAT)
                &&(table.getTable().get(leftindex).getVariableType()!=VariableType.INTEGER)){
            out.write("Attribute cannot be converted to number\n");
            return null;
        }

        for(int i=0,len=table.getTable().get(leftindex).getAttribute().size();i<len;++i){
            if(Float.parseFloat(table.getTable().get(leftindex).getAttribute().get(i).getValue())
                    >Float.parseFloat(right.getValue())){
                for(int j=0;j<table.getTable().size();j++){
                    table.getTable().get(j).getAttribute().remove(i);
                }
                --len;
                --i;
                table.setRecordCnt(table.getRecordCnt()-1);
            }
        }
        return table;
    }

    private DBTable calGreatEqual(TokenElement left, TokenElement right, DBTable table, BufferedWriter out) throws IOException {
        int leftindex=table.findIndexOfAttribute(left.getValue());
        if(leftindex==0){
            out.write("Attribute does not exist\n");
            return null;
        }else if((table.getTable().get(leftindex).getVariableType()!=VariableType.FLOAT)
                &&(table.getTable().get(leftindex).getVariableType()!=VariableType.INTEGER)){
            out.write("Attribute cannot be converted to number\n");
            return null;
        }

        for(int i=0,len=table.getTable().get(leftindex).getAttribute().size();i<len;++i){
            if(Float.parseFloat(table.getTable().get(leftindex).getAttribute().get(i).getValue())
                    <Float.parseFloat(right.getValue())){
                for(int j=0;j<table.getTable().size();j++){
                    table.getTable().get(j).getAttribute().remove(i);
                }
                --len;
                --i;
                table.setRecordCnt(table.getRecordCnt()-1);
            }
        }
        return table;
    }

    private DBTable calLess(TokenElement left, TokenElement right, DBTable table, BufferedWriter out) throws IOException {
        int leftindex=table.findIndexOfAttribute(left.getValue());
        if(leftindex==0){
            out.write("Attribute does not exist\n");
            return null;
        }else if((table.getTable().get(leftindex).getVariableType()!=VariableType.FLOAT)
                &&(table.getTable().get(leftindex).getVariableType()!=VariableType.INTEGER)){
            out.write("Attribute cannot be converted to number\n");
            return null;
        }

        for(int i=0,len=table.getTable().get(leftindex).getAttribute().size();i<len;++i){
            if(Float.parseFloat(table.getTable().get(leftindex).getAttribute().get(i).getValue())
                    >=Float.parseFloat(right.getValue())){
                for(int j=0;j<table.getTable().size();j++){
                    table.getTable().get(j).getAttribute().remove(i);
                }
                --len;
                --i;
                table.setRecordCnt(table.getRecordCnt()-1);
            }
        }
        return table;
    }

    private DBTable calGreat(TokenElement left, TokenElement right, DBTable table, BufferedWriter out) throws IOException {
        int leftindex=table.findIndexOfAttribute(left.getValue());
        if(leftindex==0){
            out.write("Attribute does not exist\n");
            return null;
        }else if((table.getTable().get(leftindex).getVariableType()!=VariableType.FLOAT)
                &&(table.getTable().get(leftindex).getVariableType()!=VariableType.INTEGER)){
            out.write("Attribute cannot be converted to number\n");
            return null;
        }

        for(int i=0,len=table.getTable().get(leftindex).getAttribute().size();i<len;++i){
            if(Float.parseFloat(table.getTable().get(leftindex).getAttribute().get(i).getValue())
                    <=Float.parseFloat(right.getValue())){
                for(int j=0;j<table.getTable().size();j++){
                    table.getTable().get(j).getAttribute().remove(i);
                }
                --len;
                --i;
                table.setRecordCnt(table.getRecordCnt()-1);
            }
        }
        return table;
    }

    private DBTable calNotEqual(TokenElement left, TokenElement right, DBTable table, BufferedWriter out) throws IOException {
        int leftindex=table.findIndexOfAttribute(left.getValue());
        if(leftindex==0){
            out.write("Attribute does not exist\n");
            return null;
        }
        if(!table.getTable().get(leftindex).getVariableType().equals(right.getVariableType())){
            out.write("Wrong variable type\n");
            return null;
        }
        for(int i=0,len=table.getTable().get(leftindex).getAttribute().size();i<len;++i){
            if(table.getTable().get(leftindex).getAttribute().get(i).getValue().equals(right.getValue())){
                for(int j=0;j<table.getTable().size();j++){
                    table.getTable().get(j).getAttribute().remove(i);
                }
                --len;
                --i;
                table.setRecordCnt(table.getRecordCnt()-1);
            }
        }
        return table;
    }

    public DBTable calEqual(TokenElement left, TokenElement right, DBTable table, BufferedWriter out) throws IOException {
        int leftindex=table.findIndexOfAttribute(left.getValue());
        if(leftindex==0){
            out.write("Attribute does not exist\n");
            return null;
        }
        if(!table.getTable().get(leftindex).getVariableType().equals(right.getVariableType())){
            out.write("Wrong variable type\n");
            return null;
        }
        for(int i=0,len=table.getTable().get(leftindex).getAttribute().size();i<len;++i){
            if(!table.getTable().get(leftindex).getAttribute().get(i).getValue().equals(right.getValue())){
                for(int j=0;j<table.getTable().size();j++){
                    table.getTable().get(j).getAttribute().remove(i);
                }
                --len;
                --i;
                table.setRecordCnt(table.getRecordCnt()-1);
            }
        }
        return table;
    }
}
