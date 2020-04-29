package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class DBController {

    private DBParser DBParser=new DBParser();
    private ArrayList<TokenElement>token=new ArrayList<>();
    private boolean isEnterDB;
    private String currentDB;
    public BufferedWriter out;
    private DBCondition dbCondition;

    public DBController(){
        this.setIsEnterDB(false);
        this.setCurrentDB(null);
        this.dbCondition=new DBCondition();
    }

    public void readCommand(String line, BufferedWriter out) throws IOException, ClassNotFoundException {
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
        if(this.token.size()==4){
            return (this.token.get(0).getProperty().equals(TokenType.CREATE)
                &&this.token.get(1).getProperty().equals(TokenType.DATABASE)
                &&this.token.get(2).getProperty().equals(TokenType.VALUE)
                &&this.token.get(3).getProperty().equals(TokenType.SEMICOLON));
        }
        return false;
    }

    public boolean isUseDB(){
        if(this.token.size()==3) {
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
            if(token.get(i).getProperty()==TokenType.VALUE
                    ||token.get(i).getProperty()==TokenType.MULTIPLY){
                if(!RightOrder) RightOrder= i == begin;
                stack.push(token.get(i));
            }else if(token.get(i).getProperty()==TokenType.COMMA){
                stack.pop();
            }
        }
        return RightOrder && stack.size()==1;
    }

    public boolean isLegalCondition(int begin,int end){
        Stack<TokenElement> stack=new Stack<>();
        boolean RightOrder=false;
        for(int i=begin;i<end;i++){
            if(token.get(i).getProperty()==TokenType.VALUE){
                if(!RightOrder) RightOrder= i == begin;
                stack.push(token.get(i));
            }
            switch (token.get(i).getProperty()){
                case AND:
                case OR:
                case EQUAL:
                case GREATER:
                case LESS:
                case LESSEQUAL:
                case GREATEREQUAL:
                case NOTEQUAL:
                case LIKE:
                stack.pop();
            }
        }
        return RightOrder && stack.size()==1;
    }

    public boolean isInsert() {
        if(this.token.size()>=8){
            return (this.token.get(0).getProperty().equals(TokenType.INSERT)
                    &&this.token.get(1).getProperty().equals(TokenType.INTO)
                    &&this.token.get(2).getProperty().equals(TokenType.VALUE)
                    &&this.token.get(3).getProperty().equals(TokenType.VALUES)
                    &&this.token.get(4).getProperty().equals(TokenType.LEFTPARENTHESIS)
                    &&this.token.get(this.token.size()-2).getProperty().equals(TokenType.RIGHTPARENTHESIS)
                    &&this.isLegalAttributeList(5,this.token.size()-2));
        }
        return false;
    }

    public boolean isDropTable() {
        if(this.token.size()==4){
            return (this.token.get(0).getProperty().equals(TokenType.DROP)
                    &&this.token.get(1).getProperty().equals(TokenType.TABLE)
                    &&this.token.get(2).getProperty().equals(TokenType.VALUE));
        }
        return false;
    }

    public boolean isDropDB() {
        if(this.token.size()==4){
            return (this.token.get(0).getProperty().equals(TokenType.DROP)
                    &&this.token.get(1).getProperty().equals(TokenType.DATABASE)
                    &&this.token.get(2).getProperty().equals(TokenType.VALUE));
        }
        return false;
    }

    public boolean isAlter() {
        if(this.token.size()==6){
            return (this.token.get(0).getProperty().equals(TokenType.ALTER)
                    &&this.token.get(1).getProperty().equals(TokenType.TABLE)
                    &&this.token.get(2).getProperty().equals(TokenType.VALUE)
                    &&this.token.get(4).getProperty().equals(TokenType.VALUE))
                    &&(this.token.get(3).getProperty().equals(TokenType.ADD)
                    ||this.token.get(3).getProperty().equals(TokenType.DROP));
        }
        return false;
    }

    public int findWhere(){
        int whereindex=0;
        for(TokenElement tokenElement:token){
            if(tokenElement.getProperty()==TokenType.WHERE)
            {
                break;
            }
            whereindex++;
        }
        return whereindex;
    }

    public boolean isSelete() {
        int whereindex=findWhere();

        if(whereindex==token.size()){
            return (this.token.get(0).getProperty().equals(TokenType.SELECT)
                    &&this.token.get(token.size()-3).getProperty().equals(TokenType.FROM)
                    &&this.token.get(token.size()-2).getProperty().equals(TokenType.VALUE))
                    &&isLegalAttributeList(1,token.size()-3);
        }else{
            return (this.token.get(0).getProperty().equals(TokenType.SELECT)
                    &&this.token.get(whereindex-2).getProperty().equals(TokenType.FROM)
                    &&this.token.get(whereindex-1).getProperty().equals(TokenType.VALUE)
                    &&this.token.get(whereindex).getProperty().equals(TokenType.WHERE))
                    &&isLegalAttributeList(1,whereindex-2)
                    &&isLegalCondition(whereindex+1,token.size()-1);
        }
    }

    public void excuteCommand(BufferedWriter out) throws IOException, ClassNotFoundException {
        if(this.isCreateDB()){
            this.createDB(out);
        }else if(this.isUseDB()){
            this.useDB(out);
        }else if(this.isCreateTable()){
            this.createTable(out);
        }else if(this.isInsert()){
            this.insertDB(out);
        }else if(this.isDropTable()){
            this.dropTable(out);
        }else if(this.isDropDB()) {
            this.dropDB(out);
        }else if(this.isAlter()) {
            this.alterTable(out);
        }else if(isSelete()){
            seleteDB(out);
        }
        System.out.println(1);
    }

    public void seleteDB(BufferedWriter out) throws IOException, ClassNotFoundException {
        int whereindex=findWhere();
        ArrayList<TokenElement> RNP;
        DBTable TempTable;
        Stack<TokenElement> tokenElementStack;
        Stack<DBTable> dbTableStack;
        if(whereindex!=token.size()){
            RNP=tokentoRPN(out);
            tokenElementStack=new Stack<>();
            dbTableStack=new Stack<>();

            for(TokenElement tokenElement:RNP){
                TempTable=loadTableFromHD(token.get(whereindex-1).getValue(),out);
                if(tokenElement.getProperty()==TokenType.VALUE){
                    tokenElementStack.push(tokenElement);
                }else if(tokenElement.getProperty()==TokenType.EQUAL
                        ||tokenElement.getProperty()==TokenType.GREATER
                        ||tokenElement.getProperty()==TokenType.GREATEREQUAL
                        ||tokenElement.getProperty()==TokenType.LESS
                        ||tokenElement.getProperty()==TokenType.LESSEQUAL
                        ||tokenElement.getProperty()==TokenType.NOTEQUAL
                        ||tokenElement.getProperty()==TokenType.LIKE){
                    TokenElement rightvalue=tokenElementStack.pop();
                    TokenElement leftvalue=tokenElementStack.pop();
                    dbTableStack.push(dbCondition.calulateCondition(leftvalue,rightvalue,
                            tokenElement.getProperty(),TempTable,out));
                }else if(tokenElement.getProperty()==TokenType.AND
                        ||tokenElement.getProperty()==TokenType.OR){
                    DBTable rightDBTable=dbTableStack.pop();
                    DBTable leftDBtable=dbTableStack.pop();
                    dbTableStack.push(dbCondition.logicCondition(leftDBtable,rightDBTable,
                            tokenElement.getProperty(),out));
                }
            }



        }else {
            TempTable=loadTableFromHD(token.get(token.size()-2).getValue(),out);
        }
    }

     public ArrayList<TokenElement> tokentoRPN(BufferedWriter out){
        int begin = 0,end=token.size()-1;
         for (TokenElement tokenElement : token) {
             if (tokenElement.getProperty().equals(TokenType.WHERE)) {
                 begin=token.indexOf(tokenElement);
                 break;
             }
         }
         Stack<TokenElement> stack=new Stack<>();
         ArrayList<TokenElement> res=new ArrayList<>();

         for(int i=begin;i<end;i++){
             if(token.get(i).getProperty().equals(TokenType.VALUE)){
                 res.add(token.get(i));
                 continue;
             }
             switch (token.get(i).getProperty()){
                 case AND:
                 case OR:
                 case EQUAL:
                 case GREATER:
                 case LESS:
                 case LESSEQUAL:
                 case GREATEREQUAL:
                 case NOTEQUAL:
                 case LIKE:
                 case LEFTPARENTHESIS:
                     stack.push(token.get(i));
                     break;
             }
             if(token.get(i).getProperty().equals(TokenType.RIGHTPARENTHESIS)){
                 TokenElement temp=stack.pop();
                 while (temp.getProperty()!=TokenType.LEFTPARENTHESIS){
                     res.add(temp);
                     temp=stack.pop();
                 }
             }
         }
         while (!stack.empty())
         {
             res.add(stack.pop());
         }
         return res;
     }

    public void alterTable(BufferedWriter out) throws IOException, ClassNotFoundException {
        DBTable currentTable=loadTableFromHD(this.token.get(2).getValue(),out);
        if(this.token.get(3).getProperty().equals(TokenType.ADD)){
            TableAttribute newTableAttribute=new TableAttribute();
            newTableAttribute.setAttributeName(token.get(4).getValue());
            currentTable.setTable(newTableAttribute);
            currentTable.setEmptyTable(true);
        }else if(this.token.get(3).getProperty().equals(TokenType.DROP)){
            currentTable.deleteTableAttribute(token.get(4).getValue());
        }
        this.saveTableToHD(currentTable,out);
        out.write("OK\n");
    }

    public void dropDB(BufferedWriter out) throws IOException {
        File dir = new File("./"+this.currentDB);
        if (!dir.exists()) {
            out.write("Database does not exist.\n");
        }
        if(deleteFile(dir)){
            out.write("OK\n");
        }
    }

    public static boolean deleteFile(File dirFile) {
        if (!dirFile.exists()) {
            return false;
        }
        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : Objects.requireNonNull(dirFile.listFiles())) {
                deleteFile(file);
            }
        }
        return dirFile.delete();
    }

    public void dropTable(BufferedWriter out) throws IOException {
        File dir = new File("./"+this.currentDB+"/"+this.token.get(2).getValue()+".db");
        if (!dir.exists()) {
            out.write("Table does not exist.\n");
        }
        if(dir.delete()){
            currentDB=null;
            out.write("OK\n");
        }
    }

    public void insertDB(BufferedWriter out) throws IOException, ClassNotFoundException {

        DBTable currentTable=loadTableFromHD(this.token.get(2).getValue(),out);
        if(currentTable.getEmptyTable()){
            currentTable.setTableAttributeFromInsertToken(token);
        }
        currentTable.insertDate(token,out);
        this.saveTableToHD(currentTable,out);
        out.write("OK\n");
    }

    public void createTable(BufferedWriter out) throws IOException, ClassNotFoundException {
        File dir = new File("./"+this.currentDB+"/"+this.token.get(2).getValue()+".db");
        if (dir.exists()) {
            out.write("Table has existed.\n");
        }
        DBTable newTable=new DBTable();
        newTable.setTableName(token.get(2).getValue());
        TableAttribute newTableAttribute=new TableAttribute();
        newTableAttribute.setAttributeName("id");
        newTable.setTable(newTableAttribute);
        if(token.get(3).getProperty()==TokenType.LEFTPARENTHESIS){
            for(int i=4;i<token.size()-2;i++){
                newTableAttribute=new TableAttribute();
                newTableAttribute.setAttributeName(token.get(i).getValue());
                newTable.setTable(newTableAttribute);
                i++;
            }
        }
        this.saveTableToHD(newTable,out);
        out.write("OK\n");
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

    public void saveTableToHD(DBTable newTable,BufferedWriter out) throws IOException {
        if(currentDB==null){
            out.write("Database does not exist\n");
        }
        FileOutputStream fileOut = new FileOutputStream("./"+this.currentDB+"/"+newTable.getTableName()+".db");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
        outputStream.writeObject(newTable);
        outputStream.close();
        fileOut.close();
    }

    public DBTable loadTableFromHD(String TableName, BufferedWriter out) throws IOException, ClassNotFoundException {

        if(currentDB==null){
            out.write("Database does not exist\n");
        }
        File file = new File("./"+this.currentDB+"/"+TableName+".db");
        if(!file.exists()){
            out.write("Table does not exist\n");
        }

        DBTable newTable;
        FileInputStream fileIn = new FileInputStream("./"+this.currentDB+"/"+TableName+".db");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        newTable = (DBTable) in.readObject();
        in.close();
        fileIn.close();
        return newTable;
    }

    public void setCurrentDB(String currentDB) {
        this.currentDB = currentDB;
    }

    public String getCurrentDB() {
        return currentDB;
    }
}
