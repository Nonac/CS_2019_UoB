package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Character {
    private Location location;
    private ArrayList<Artefact> Artefact=new ArrayList<>();

    public Player(Location location){
        this.location=location;
        this.setProperty("Player");
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
    public void setArtefact(Artefact artefact) {
        this.Artefact.add(artefact);
    }
    public ArrayList<Artefact> getArtefact() {
        return Artefact;
    }

    public Artefact getArtefact(String s){
        if(s==null||s.length()==0){
            return null;
        }
        for(Artefact artefact:this.Artefact){
            if(artefact.getName().equals(s)){
                return artefact;
            }
        }
        return null;
    }
    public void artefactDescribe(BufferedWriter out){
        try {
            out.write("Your health is "+this.getHealth()+".\n");
            if(Artefact.size()==0){
                out.write("There is nothing artefact currently carried by "+this.getName()+".\n");
            }else {
                out.write("There are artefacts currently carried by "+this.getName()+":\n");
                for(Artefact artefact:Artefact){
                    out.write("    There is "+(artefact.nameIsVowel()?"an ":"a ")+artefact.getName()+ ". " +
                            "It is "+(artefact.descriptionIsVowel()?"an ":"a ")+artefact.getDescription()+".\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeArtefact(Artefact artefact){
        this.Artefact.remove(artefact);
    }

    public void removeArtefact(String s){
        this.Artefact.removeIf(artefact -> artefact.getName().equals(s));
    }

    public boolean artefactContains(String s){
        for(Artefact artefact:this.Artefact){
            if(artefact.getName().equals(s)){
                return true;
            }
        }
        return false;
    }

    public void increaseHealth(){
        this.health++;
    }

    public void  reduceHealth(){
        this.health--;
    }
}
