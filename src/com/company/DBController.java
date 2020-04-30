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
       // this.out.write("success\n");
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
        boolean RightOrder=false;
        boolean ignorePar=true;
        for(int i=begin;i<end;i++){
            if(token.get(i).getProperty()==TokenType.VALUE
                    ||token.get(i).getProperty()==TokenType.MULTIPLY){
                if((!RightOrder)&&ignorePar) RightOrder= true;
                stack.push(token.get(i));
            }else if(token.get(i).getProperty()==TokenType.COMMA){
                stack.pop();
                ignorePar=false;
            }
        }
        return RightOrder && stack.size()==1;
    }

    public boolean isLegalNameValueList(int begin,int end){
        int cnt=0;
        boolean left=false;
        boolean assign=false;
        boolean right=false;
        for(int i=begin;i<end;i++){
            if(cnt==3){
                if(!(left&&assign&&right)){
                    return false;
                }
                left=false;
                assign=false;
                right=false;
                cnt=0;
            }
            if(token.get(i).getProperty()==TokenType.VALUE&&(!left)){
                left=true;
                cnt++;
                continue;
            }else if(token.get(i).getProperty()==TokenType.ASSIGN){
                assign=true;
                cnt++;
                continue;
            }else if ((token.get(i).getProperty()==TokenType.VALUE)&&left){
                right=true;
                cnt++;
                continue;
            }
        }
        return (left&&assign&&right)&&(cnt==3);
    }

    public boolean isLegalCondition(int begin,int end){
        Stack<TokenElement> stack=new Stack<>();
        boolean RightOrder=false;
        boolean ignorePar=true;
        for(int i=begin;i<end;i++){
            if(token.get(i).getProperty()==TokenType.VALUE){
                if((!RightOrder)&&ignorePar) RightOrder= true;
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
                    ignorePar=false;
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

    public boolean isSelect() {
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

    public boolean isDelete(){
        int whereindex=findWhere();
        if(whereindex!=token.size()){
            return (this.token.get(0).getProperty().equals(TokenType.DELETE)
                    &&this.token.get(1).getProperty().equals(TokenType.FROM)
                    &&this.token.get(2).getProperty().equals(TokenType.VALUE)
                    &&this.token.get(3).getProperty().equals(TokenType.WHERE))
                    &&isLegalCondition(4,token.size()-1);
        }
        return false;
    }

    public boolean isUpdate(){
        int whereindex=findWhere();
        if(whereindex!=token.size()){
            return (this.token.get(0).getProperty().equals(TokenType.UPDATE)
                    &&this.token.get(1).getProperty().equals(TokenType.VALUE)
                    &&this.token.get(2).getProperty().equals(TokenType.SET))
                    &&isLegalCondition(whereindex+1,token.size()-1)
                    &&isLegalNameValueList(3,whereindex);
        }
        return false;
    }

    public boolean isJoin(){
        return (token.size()==9
                &&this.token.get(0).getProperty().equals(TokenType.JOIN)
                &&this.token.get(1).getProperty().equals(TokenType.VALUE)
                &&this.token.get(2).getProperty().equals(TokenType.AND)
                &&this.token.get(3).getProperty().equals(TokenType.VALUE)
                &&this.token.get(4).getProperty().equals(TokenType.ON)
                &&this.token.get(5).getProperty().equals(TokenType.VALUE)
                &&this.token.get(6).getProperty().equals(TokenType.AND)
                &&this.token.get(7).getProperty().equals(TokenType.VALUE));
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
        }else if(isSelect()){
            selectDB(out);
        }else if(isDelete()){
            deleteDB(out);
        }else if(isUpdate()){
            updateDB(out);
        }else if(isJoin()){
            joinDB(out);
        }
        System.out.println(1);
    }

    public void joinDB(BufferedWriter out) throws IOException, ClassNotFoundException {
        DBTable leftDB=loadTableFromHD(token.get(1).getValue(),out);
        DBTable rightDB=loadTableFromHD(token.get(3).getValue(),out);

        dbCondition.logicAnd(leftDB,rightDB,token.get(5).getValue(),token.get(7).getValue(),out).printAll(out);
    }

    public void updateDB(BufferedWriter out) throws IOException, ClassNotFoundException {
        int whereindex=findWhere();
        DBTable currentTable=loadTableFromHD(token.get(1).getValue(),out);
        DBTable TempTable=interperteWhere(out,1);
        currentTable=dbCondition.logicNot(TempTable,currentTable,out);
        if(TempTable==null){
            return;
        }

        for(int j=3;j<whereindex;j++){
            if(token.get(j).getProperty()==TokenType.ASSIGN){
                int attributeIndex=TempTable.findIndexOfAttribute(token.get(j-1).getValue());
                if(attributeIndex==0){
                    out.write("Attribute does not exist\n");
                    return;
                }
                for(int i=0;i<TempTable.getTable().get(0).getAttribute().size();i++){
                    TempTable.getTable().get(attributeIndex).getAttribute().get(i).setValue(
                            token.get(j+1).getValue());
                }
            }
        }
        this.saveTableToHD(dbCondition.logicOr(currentTable,TempTable,out),out);
        out.write("OK\n");
    }

    public void deleteDB(BufferedWriter out) throws IOException, ClassNotFoundException {
        int whereindex=findWhere();
        DBTable currentTable=loadTableFromHD(token.get(whereindex-1).getValue(),out);
        DBTable TempTable=interperteWhere(out,whereindex-1);
        if(TempTable==null){
            return;
        }
        this.saveTableToHD(dbCondition.logicNot(TempTable,currentTable,out),out);
        out.write("OK\n");
    }

    public void selectDB(BufferedWriter out) throws IOException, ClassNotFoundException {
        int whereindex=findWhere();
        DBTable TempTable;
        if(whereindex!=token.size()){
            TempTable=interperteWhere(out,whereindex-1);
        }else {
            TempTable=loadTableFromHD(token.get(token.size()-2).getValue(),out);
        }

        if(TempTable==null){
            return;
        }

        if(this.token.get(1).getProperty().equals(TokenType.MULTIPLY)){
            TempTable.printAll(out);
        }else {
            ArrayList<Integer> printIndex=new ArrayList<>();
            printIndex.add(0);
            for(int i=1;token.get(i).getProperty()!=TokenType.FROM;i++){
                if(token.get(i).getProperty()==TokenType.VALUE){
                    int temp=TempTable.findIndexOfAttribute(token.get(i).getValue());
                    if(temp==-1){
                        out.write("Attribute does not exist\n");
                        return;
                    }
                    printIndex.add(temp);
                }
            TempTable.printPart(printIndex,out);
            }
        }
    }

    public DBTable interperteWhere(BufferedWriter out,int tablenameIndex) throws IOException, ClassNotFoundException {
        DBTable TempTable;
        ArrayList<TokenElement> RNP;
        Stack<TokenElement> tokenElementStack=new Stack<>();;
        Stack<DBTable> dbTableStack=new Stack<>();;
        RNP=tokentoRPN(out);

        for(TokenElement tokenElement:RNP){
            TempTable=loadTableFromHD(token.get(tablenameIndex).getValue(),out);
            if(tokenElement.getProperty()==TokenType.VALUE){
                tokenElementStack.push(tokenElement);
            }else if(tokenElement.getProperty()==TokenType.EQUAL
                    ||tokenElement.getProperty()==TokenType.GREATER
                    ||tokenElement.getProperty()==TokenType.GREATEREQUAL
                    ||tokenElement.getProperty()==TokenType.LESS
                    ||tokenElement.getProperty()==TokenType.LESSEQUAL
                    ||tokenElement.getProperty()==TokenType.NOTEQUAL
                    ||tokenElement.getProperty()==TokenType.LIKE){
                if(tokenElementStack.size()<2){
                    out.write("Invalid query");
                    return null;
                }
                TokenElement rightvalue=tokenElementStack.pop();
                TokenElement leftvalue=tokenElementStack.pop();
                dbTableStack.push(dbCondition.calulateCondition(leftvalue,rightvalue,
                        tokenElement.getProperty(),TempTable,out));
            }else if(tokenElement.getProperty()==TokenType.AND
                    ||tokenElement.getProperty()==TokenType.OR){
                if(dbTableStack.size()<2){
                    out.write("Invalid query");
                    return null;
                }
                DBTable rightDBTable=dbTableStack.pop();
                DBTable leftDBtable=dbTableStack.pop();
                dbTableStack.push(dbCondition.logicCondition(leftDBtable,rightDBTable,
                        tokenElement.getProperty(),out));
            }
        }
        if(dbTableStack.size()!=1){
            out.write("Invalid query");
            return null;
        }
        return dbTableStack.pop();
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
            for(int i=0;i<currentTable.getTable().get(0).getAttribute().size();i++)
            {
                ValueLiteral valueLiteral=new ValueLiteral();
                valueLiteral.setValue("");
                newTableAttribute.getAttribute().add(valueLiteral);
            }
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
        currentTable.insertData(token,out);
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
        this.setCurrentDB(this.token.get(1).getValue());
        this.setIsEnterDB(true);
        if(!dir.exists()){
            out.write("Unknown database\n");
        }else {
            out.write("OK\n");
        }
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

    public DBTable loadTableFromHD(String TableName, BufferedWriter out) throws IOException {

        if(currentDB==null){
            out.write("Database does not exist\n");
        }
        File file = new File("./"+this.currentDB+"/"+TableName+".db");

        DBTable newTable=null;
        try {
            FileInputStream fileIn = new FileInputStream("./"+this.currentDB+"/"+TableName+".db");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            newTable = (DBTable) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException | FileNotFoundException ignored) {
            out.write("Table does not exist\n");
        }

        return newTable;
    }

    public void setCurrentDB(String currentDB) {
        this.currentDB = currentDB;
    }

    public String getCurrentDB() {
        return currentDB;
    }
}
