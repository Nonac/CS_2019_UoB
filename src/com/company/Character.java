package com.company;

import java.io.BufferedWriter;
import java.io.IOException;

public class Character extends Entity {
    public int health=3;
    public Character(){
        this.setProperty("character");
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void getHealth(BufferedWriter out) throws IOException {
        out.write("The health of "+this.getName()+" is "+this.getHealth()+".\n");
    }

    public void characterDescribe(BufferedWriter out) throws IOException {
        out.write("There is "+(this.nameIsVowel()?"an ":"a ")+this.getName()+
                ". It is "+(this.descriptionIsVowel()?"an ":"a ")+this.getDescription()+". ");
        out.write("The health of "+this.getName()+" is "+this.getHealth()+".\n");
    }

    public boolean isDead(){
        return (this.health<=0);
    }
}
