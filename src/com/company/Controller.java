package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private World world;
    private boolean firstLogIn=true;
    private Player currentPlayer;
    private String[] command;
    BufferedWriter out;
    public Controller(World world){
        this.world=world;
    }
    void readCommand(String in, BufferedWriter out){

        String[] temp = in.split(":\\s|\\s");
        world.setPlayer(temp[0]);
        currentPlayer=world.getPlayer(temp[0]);
        setCommand(temp);
        this.out=out;

        try {

            if(firstLogIn){
                out.write("Welcome to the world of Stag.\n");
                out.newLine();
                firstLogIn=false;
            }
            if(!(inventory()||get()||look()||drop()||goTo()||action())){
                out.write("I can not understand what is your mean.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setCommand(String[] temp){
        command=new String[temp.length-1];
        System.arraycopy(temp, 1, this.command, 0, temp.length - 1);
    }

    boolean inventory(){
        if(Arrays.asList(this.command).contains("inventory")||Arrays.asList(this.command).contains("inv")){
            currentPlayer.artefactDescribe(this.out);
            return true;
        }
        return false;
    }

    boolean look(){
        if(Arrays.asList(this.command).contains("look")){
            currentPlayer.getLocation().loctionDescribe(this.out);
            return true;
        }
        return false;
    }

    boolean get(){
        boolean getFlag=false;
        Artefact getArtefact = null;
        try {
            if (Arrays.asList(this.command).contains("get")) {
                for(String s:this.command){
                    for(Artefact artefact:currentPlayer.getLocation().getArtefact()){
                        if(artefact.getName().equals(s)){
                            getFlag=true;
                            getArtefact=artefact;
                            break;
                        }
                    }
                }
                if(getFlag){
                    currentPlayer.setArtefact(getArtefact);
                    currentPlayer.getLocation().removeArtefact(getArtefact);
                    this.out.write("You got the "+getArtefact.getName()+".\n");
                    return true;
                } else {
                    this.out.write("There is not your collect artefact in your location.\n");
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean drop(){
        boolean dropFlag=false;
        Artefact dropArtefact = null;
        try {
            if (Arrays.asList(this.command).contains("drop")) {
                for (String s :this.command) {
                    for (Artefact artefact : currentPlayer.getArtefact()) {
                        if (artefact.getName().equals(s)) {
                            dropFlag = true;
                            dropArtefact = artefact;
                            break;
                        }
                    }
                }
                if (dropFlag) {
                    currentPlayer.getLocation().setArtefact(dropArtefact);
                    currentPlayer.removeArtefact(dropArtefact);
                    this.out.write("You drop the "+dropArtefact.getName()+".\n");
                    return true;
                } else {
                    this.out.write("There is not your drop artefact in your backpack.\n");
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean goTo(){
        boolean goToFlag=false;
        Location goToLocation = null;
        try {
            if (Arrays.asList(this.command).contains("goto")) {
                for (String s :this.command) {
                    for (Location location : currentPlayer.getLocation().getPath()) {
                        if (location.getName().equals(s)) {
                            goToFlag = true;
                            goToLocation = location;
                            break;
                        }
                    }
                }
                if(goToFlag){
                    currentPlayer.setLocation(goToLocation);
                    this.out.write("You come to the "+goToLocation.getName()+".\n");
                    return true;
                }else {
                    this.out.write("There is not path to location you want to goto.\n");
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean action(){
        boolean triggersSwitch;
        boolean subjectExactMatch;
        boolean subjectContain;
        ArrayList<Action> possibleAction=new ArrayList<>();
        try {
            for(Action action:world.getAction()){
                triggersSwitch=false;
                subjectExactMatch=true;
                subjectContain=false;
                for(String s:this.command){
                    if (action.getTrigger().contains(s)) {
                        triggersSwitch = true;
                    }
                    if(action.getSubjects().contains(s)){
                        subjectContain=true;
                    }
                    if(subjectContain){
                        for(String subject:action.getSubjects()){
                            subjectExactMatch=subjectExactMatch&(Arrays.asList(this.command).contains(subject));
                        }
                    }
                    if(triggersSwitch&subjectContain&subjectExactMatch){
                        return actionCheck(action);
                    }else if(triggersSwitch&subjectContain&(!subjectExactMatch)){
                        possibleAction.add(action);
                    }else if(triggersSwitch&(!subjectContain)){
                        this.out.write("You can not do this with that entities!\n");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean actionCheck(Action action) {
        boolean artefactFlag;
        boolean funitureFlag;
        boolean characterFlag;
        boolean actionFlag = false;
        for (String subject : action.getSubjects()) {
            artefactFlag = false;
            funitureFlag = false;
            characterFlag = false;
            for (Artefact artefact : this.currentPlayer.getArtefact()) {
                if (artefact.getName().equals(subject)) {
                    artefactFlag = true;
                    break;
                }
            }
            for (Furniture furniture : this.currentPlayer.getLocation().getFurniture()) {
                if (furniture.getName().equals(subject)) {
                    funitureFlag = true;
                    break;
                }
            }
            for (Character character : this.currentPlayer.getLocation().getCharacter()) {
                if (character.getName().equals(subject)) {
                    characterFlag = true;
                    break;
                }
            }
            actionFlag = actionFlag | artefactFlag | funitureFlag | characterFlag;
            try {
                if (!actionFlag) {
                    this.out.write("There are not enough subjects to achieve this action.\n");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        actionExecution(action);
        return true;
    }
}
