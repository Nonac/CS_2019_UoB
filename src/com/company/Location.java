package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

class Location extends Entity {
    private ArrayList<Artefact> Artefact=new ArrayList<>();
    private ArrayList<Furniture> Furniture=new ArrayList<>();
    private ArrayList<Location> Path= new ArrayList<>();
    private ArrayList<Character> Character=new ArrayList<>();
    public Location(){
        this.setProperty("location");
    }
    public void setArtefact(Artefact artefact) {
        this.Artefact.add(artefact);
    }

    public void setFurniture(Furniture furniture) {
        this.Furniture.add(furniture);
    }
    public void setCharacter(Character character){
        this.Character.add(character);
    }

    public void setPath(Location path) {
        this.Path.add(path);
    }

    public ArrayList<Location> getPath() {
        return Path;
    }

    public ArrayList<Artefact> getArtefact() {
        return Artefact;
    }

    public ArrayList<Furniture> getFurniture() {
        return Furniture;
    }

    public ArrayList<Character> getCharacter() {
        return Character;
    }

    public void loctionDescribe(BufferedWriter out){
        try {
            out.write("You are at the "+this.getName()+".\n");
            for(Artefact artefact:Artefact){
                out.write("There is "+(artefact.nameIsVowel()?"an ":"a ")+artefact.getName()+ " which you could collect. " +
                        "It is "+(artefact.descriptionIsVowel()?"an ":"a ")+artefact.getDescription()+".\n");
            }
            for(Furniture furniture:Furniture){
                out.write("There is "+(furniture.nameIsVowel()?"an ":"a ")+furniture.getName()+
                        ". It is "+(furniture.descriptionIsVowel()?"an ":"a ")+furniture.getDescription()+".\n");
            }
            for(Character character:Character){
                out.write("There is "+(character.nameIsVowel()?"an ":"a ")+character.getName()+
                        ". It is a/an "+(character.descriptionIsVowel()?"an ":"a ")+character.getDescription()+".\n");
            }
            for(Location path:Path){
                out.write("There is a way to "+path.getName()+".\n");
            }
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeArtefact(Artefact artefact){
        this.Artefact.remove(artefact);
    }
}
