package com.company;

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

    private DBTable logicOr(DBTable left, DBTable right, BufferedWriter out) {
    }

    private DBTable logicAnd(DBTable left, DBTable right, BufferedWriter out) {
    }

    private DBTable calLike(TokenElement left, TokenElement right, DBTable table, BufferedWriter out) throws IOException {
        int leftindex=table.findIndexOfAttribute(left.getValue());
        if(leftindex==0){
            out.write("Attribute does not exist\n");
            return null;
        }else if(table.getTable().get(leftindex).getVariableType()!=VariableType.STRING){
            out.write(" String expected\n");
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
        }else if(table.getTable().get(leftindex).getVariableType()!=VariableType.FLOAT
                ||table.getTable().get(leftindex).getVariableType()!=VariableType.INTEGER){
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
        }else if(table.getTable().get(leftindex).getVariableType()!=VariableType.FLOAT
                ||table.getTable().get(leftindex).getVariableType()!=VariableType.INTEGER){
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
        }else if(table.getTable().get(leftindex).getVariableType()!=VariableType.FLOAT
                ||table.getTable().get(leftindex).getVariableType()!=VariableType.INTEGER){
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
        }else if(table.getTable().get(leftindex).getVariableType()!=VariableType.FLOAT
                ||table.getTable().get(leftindex).getVariableType()!=VariableType.INTEGER){
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
