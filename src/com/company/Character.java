package com.company;

public class Character extends Entity {
    private int health=3;
    public Character(){
        this.setProperty("character");
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
