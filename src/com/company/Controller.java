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
        Artefact getArtefact = null;
        try {
            if (Arrays.asList(this.command).contains("get")) {
                for(String s:this.command){
                    getArtefact=currentPlayer.getLocation().getArtefact(s);
                    if(getArtefact!=null)break;
                }
                if(getArtefact!=null){
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
        Artefact dropArtefact = null;
        try {
            if (Arrays.asList(this.command).contains("drop")) {
                for (String s :this.command) {
                    dropArtefact=this.currentPlayer.getArtefact(s);
                    if(dropArtefact!=null) break;
                }
                if (dropArtefact!=null) {
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
        Location goToLocation = null;
        try {
            if (Arrays.asList(this.command).contains("goto")) {
                for (String s :this.command) {
                    goToLocation=this.currentPlayer.getLocation().getPath(s);
                    if (goToLocation!=null) break;
                }
                if(goToLocation!=null){
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
        boolean actionFlag = false;
        for (String subject : action.getSubjects()) {
            actionFlag = actionFlag | this.currentPlayer.artefactContains(subject)
                    | this.currentPlayer.getLocation().funitureContains(subject)
                    | this.currentPlayer.getLocation().characterContains(subject);
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

    private void actionExecution(Action action){

    }
}
