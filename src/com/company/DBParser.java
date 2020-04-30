package com.company;

import java.util.ArrayList;

public class DBParser {
    private ArrayList<TokenElement> token;
    private int beginCharacter,endCharacter;
    boolean stringswitch;

    public  DBParser(){

    }

    public ArrayList<TokenElement> parseStringToToken(String in){
        this.token=new ArrayList<>();
        this.stringswitch=false;
        this.setBeginCharacter(0);
        while (in.charAt(beginCharacter)==' '){
            beginCharacter++;
        }
        this.setEndCharacter(beginCharacter);

        while (this.endCharacter<in.length()) {
            String buff;
            this.interceptWord(in);
            buff = in.substring(beginCharacter, endCharacter);
            TokenElement tokenElement=new TokenElement();
            tokenElement.setProperty(this.identifyToken(buff));
            if(tokenElement.propertyIsValue()){
                tokenElement.setValue(buff);
            }
            if(tokenElement.getProperty()!=TokenType.SPACE){
                this.token.add(tokenElement);
            }
            this.setBeginCharacter(this.endCharacter);
        }

        return token;
    }

    public TokenType identifyToken(String buff) {
        if (buff.equalsIgnoreCase("Use")) {
            return TokenType.USE;
        } else if (buff.equalsIgnoreCase("Create")) {
            return TokenType.CREATE;
        } else if (buff.equalsIgnoreCase("Drop")) {
            return TokenType.DROP;
        } else if (buff.equalsIgnoreCase("Alter")) {
            return TokenType.ALTER;
        } else if (buff.equalsIgnoreCase("Insert")) {
            return TokenType.INSERT;
        } else if (buff.equalsIgnoreCase("Select")) {
            return TokenType.SELECT;
        } else if (buff.equalsIgnoreCase("Update")) {
            return TokenType.UPDATE;
        } else if (buff.equalsIgnoreCase("Delete")) {
            return TokenType.DELETE;
        } else if (buff.equalsIgnoreCase("Join")) {
            return TokenType.JOIN;
        } else if (buff.equalsIgnoreCase("And")) {
            return TokenType.AND;
        } else if (buff.equalsIgnoreCase("Or")) {
            return TokenType.OR;
        }else if(buff.equalsIgnoreCase("Table")){
            return TokenType.TABLE;
        }else if(buff.equalsIgnoreCase("Database")){
            return TokenType.DATABASE;
        }else if(buff.equalsIgnoreCase("Values")){
            return TokenType.VALUES;
        }else if(buff.equalsIgnoreCase("Where")){
            return TokenType.WHERE;
        }else if(buff.equalsIgnoreCase("From")){
            return TokenType.FROM;
        }else if(buff.equalsIgnoreCase("Like")){
            return TokenType.LIKE;
        }else if(buff.equalsIgnoreCase("Into")){
            return TokenType.INTO;
        }else if(buff.equalsIgnoreCase("Add")){
            return TokenType.ADD;
        }else if(buff.equalsIgnoreCase("Set")){
            return TokenType.SET;
        } else if(buff.equalsIgnoreCase("On")){
            return TokenType.ON;
        }else if(buff.equals("(")){
            return TokenType.LEFTPARENTHESIS;
        }else if(buff.equals(")")){
            return TokenType.RIGHTPARENTHESIS;
        }else if(buff.equals(",")){
            return TokenType.COMMA;
        }else if(buff.equals(";")){
            return TokenType.SEMICOLON;
        }else if(buff.equals("*")){
            return TokenType.MULTIPLY;
        }else if(buff.equals(" ")){
            return TokenType.SPACE;
        }else if(buff.equals(">")){
            return TokenType.GREATER;
        }else if(buff.equals("<")){
            return TokenType.LESS;
        }else if(buff.equals(">=")){
            return TokenType.GREATEREQUAL;
        }else if(buff.equals("<=")){
            return TokenType.LESSEQUAL;
        }else if(buff.equals("==")){
            return TokenType.EQUAL;
        }else if(buff.equals("!=")){
            return TokenType.NOTEQUAL;
        }else if(buff.equals("=")){
            return TokenType.ASSIGN;
        }
        return TokenType.VALUE;
    }


    public void interceptWord(String in) {

        while (endCharacter < in.length()) {
            switch (in.charAt(endCharacter)) {
                case '(':
                case ')':
                case ';':
                case ',':
                case ' ':
                case '*':
                    if (beginCharacter == endCharacter) {
                        endCharacter++;
                    }
                    if(!stringswitch){
                        return;
                    }else {
                        endCharacter++;
                    }
                case '>':
                case '<':
                case '!':
                case '=':
                    if (in.charAt(endCharacter + 1) == '=') {
                        endCharacter += 2;
                    }
                    if (beginCharacter == endCharacter) {
                        endCharacter++;
                    }
                    if(!stringswitch){
                        return;
                    }else {
                        endCharacter++;
                    }
            }

            if (Character.isLowerCase(in.charAt(endCharacter)) ||
                    Character.isUpperCase(in.charAt(endCharacter)) ||
                    Character.isDigit(in.charAt(endCharacter)) ||
                    in.charAt(endCharacter) == '.' ||
                    in.charAt(endCharacter) == '_') {
                this.endCharacter++;
            } else if ((in.charAt(endCharacter) == '\'') && (!stringswitch)) {
                this.stringswitch = true;
                this.endCharacter++;
            } else if ((in.charAt(endCharacter) == '\'') && (stringswitch)) {
                this.stringswitch = false;
                this.endCharacter++;
            }
        }
    }


    public void setBeginCharacter(int beginCharacter) {
        this.beginCharacter = beginCharacter;
    }

    public int getBeginCharacter() {
        return beginCharacter;
    }

    public void setEndCharacter(int endCharacter) {
        this.endCharacter = endCharacter;
    }

    public int getEndCharacter() {
        return endCharacter;
    }
}
