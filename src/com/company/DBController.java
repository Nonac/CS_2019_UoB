package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class DBController {

    private DBParser DBParser=new DBParser();
    private ArrayList<TokenElement>token=new ArrayList<>();
    private boolean isEnterDB;
    private String currentDB;
    public BufferedWriter out;

    public DBController(){
        this.setIsEnterDB(false);
        this.setCurrentDB(null);
    }

    public void readCommand(String line, BufferedWriter out) throws IOException {
        this.out=out;
        token=DBParser.parseStringToToken(line);
        if((!this.checkSemicolonGrammar(out))||(!this.checkParenthesisIsComplete(out))){
            return;
        }
        this.excuteCommand(out);
        this.out.write("success\n");
    }

    public boolean checkSemicolonGrammar(BufferedWriter out) throws IOException {
        if(token.get(token.size()-1).getProperty()!=TokenType.SEMICOLON)
        {
            out.write("Semi colon missing at end of line\n");
            return false;
        }
        return true;
    }

    public boolean checkParenthesisIsComplete(BufferedWriter out) throws IOException {
        Stack<TokenElement> left=new Stack<TokenElement>();
        for (TokenElement tokenElement : token) {
            if (tokenElement.getProperty().equals(TokenType.LEFTPARENTHESIS)) {
                left.push(tokenElement);
            } else if (tokenElement.getProperty().equals(TokenType.RIGHTPARENTHESIS)) {
                if (left.isEmpty()) {
                    out.write("Invalid query\n");
                    return false;
                }
                TokenElement buff = left.pop();
                if (!buff.getProperty().equals(TokenType.LEFTPARENTHESIS)) {
                    out.write("Invalid query\n");
                    return false;
                }
            }
        }
        if(!left.isEmpty()){
            out.write("Invalid query\n");
            return false;
        }else return true;
    }

    public void setIsEnterDB(boolean isEnterDB) {
        this.isEnterDB = isEnterDB;
    }

    public boolean getIsEnterDB(){
        return this.isEnterDB;
    }

    public boolean isCreateDB(){
        if(this.token.size()==5){
            return (this.token.get(0).getProperty().equals(TokenType.CREATE)
                &&this.token.get(1).getProperty().equals(TokenType.DATABASE)
                &&this.token.get(2).getProperty().equals(TokenType.VALUE)
                &&this.token.get(3).getProperty().equals(TokenType.SEMICOLON));
        }
        return false;
    }

    public boolean isUseDB(){
        if(this.token.size()==4) {
            return (this.token.get(0).getProperty().equals(TokenType.USE)
                    && this.token.get(1).getProperty().equals(TokenType.VALUE)
                    && this.token.get(2).getProperty().equals(TokenType.SEMICOLON));
        }
        return false;
    }

    public boolean isCreateTable(){

        if(this.token.size()==4){
            return (this.token.get(0).getProperty().equals(TokenType.CREATE)
                    &&this.token.get(1).getProperty().equals(TokenType.TABLE)
                    &&this.token.get(2).getProperty().equals(TokenType.VALUE));
        }else if(this.token.size()>=7){
            return (this.token.get(0).getProperty().equals(TokenType.CREATE)
                    &&this.token.get(1).getProperty().equals(TokenType.TABLE)
                    &&this.token.get(2).getProperty().equals(TokenType.VALUE)
                    &&this.token.get(3).getProperty().equals(TokenType.LEFTPARENTHESIS)
                    &&this.token.get(this.token.size()-2).getProperty().equals(TokenType.RIGHTPARENTHESIS)
                    &&this.isLegalAttributeList(4,this.token.size()-2));
        }
        return false;
    }

    public boolean isLegalAttributeList(int begin,int end){
        Stack<TokenElement> stack=new Stack<>();
        Boolean RightOrder=false;
        for(int i=begin;i<end;i++){
            if(token.get(i).getProperty()==TokenType.VALUE){
                RightOrder= i == begin;
                stack.push(token.get(i));
            }else if(token.get(i).getProperty()==TokenType.COMMA){
                stack.pop();
            }
        }
        return RightOrder && stack.size()==1;
    }

    public void excuteCommand(BufferedWriter out) throws IOException {
        if(this.isCreateDB()){
            this.createDB(out);
        }else if(this.isUseDB()){
            this.useDB(out);
        }else if(this.isCreateTable()){
            this.createTable(out);
        }
    }

    public void createTable(BufferedWriter out){
        DBTable newTable=new DBTable();
        newTable.setTableName(token.get(2).getValue());
        if(token.get(3).getProperty()==TokenType.LEFTPARENTHESIS){
            for(int i=4;i<token.size()-2;i++){
                TableAttribute newTableAttrebute=new TableAttribute();
                newTableAttrebute.setAttributeName(token.get(i).getValue());
                newTable.setTable(newTableAttrebute);
                i++;
            }
        }
    }

    public void createDB(BufferedWriter out) throws IOException {
        File dir = new File(this.token.get(2).getValue());
        if (dir.exists()) {
            out.write("Database has existed.\n");
        }
        if (dir.mkdirs()) {
            out.write("OK\n");
        }
    }

    public void useDB(BufferedWriter out) throws IOException {
        File dir=new File(this.token.get(1).getValue());
        if(!dir.exists()){
            out.write("\"Database does not exist.\\n");
        }
        this.setCurrentDB(this.token.get(1).getValue());
        this.setIsEnterDB(true);
        out.write("OK\n");

    }

    public void setCurrentDB(String currentDB) {
        this.currentDB = currentDB;
    }

    public String getCurrentDB() {
        return currentDB;
    }
}
