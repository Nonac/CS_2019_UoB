package com.company;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class DBController {

    private DBParser DBParser=new DBParser();
    private ArrayList<TokenElement>token=new ArrayList<>();

    public void main(){

    }
    public void test(){
        DBParser.parseStringToToken("INSERT INTO marks VALUES ('Steve', 65);");
        System.out.println(1);
    }

    public void readCommand(String line, BufferedWriter out){
        token=DBParser.parseStringToToken(line);

    }
}
