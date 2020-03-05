package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private World world;

    private Player currentPlayer;
    private String[] command;
    BufferedWriter out;

    public Controller(World world) {
        this.world = world;
    }

    void readCommand(String in, BufferedWriter out) {

        String[] temp = in.split(":\\s|\\s");
        this.world.setPlayer(temp[0]);
        this.currentPlayer = world.getPlayer(temp[0]);
        setCommand(temp);
        this.out = out;

        try {
            if (this.currentPlayer.getFirstLogIn()) {
                out.write("Welcome to the world of Stag.\n");
                out.newLine();
                this.currentPlayer.setFirstLogIn(false);
            }
            if(this.currentPlayer.isDead()){
                if(this.currentPlayer.getKiller().size()!=0){
                    this.currentPlayer.revenge(this.out);
                    this.out.newLine();
                }
                this.currentPlayer.dead(this.world.getStart(),this.out);
            }else if(this.currentPlayer.getKiller().size()!=0){
                this.currentPlayer.revenge(this.out);
                this.currentPlayer.getHealth(out);
                this.out.newLine();
            }
            if (!(inventory() | get() | look() | drop() | goTo() |health()||PVP()| action())) {
                out.write("I can not understand what is your mean.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setCommand(String[] temp) {
        command = new String[temp.length - 1];
        System.arraycopy(temp, 1, this.command, 0, temp.length - 1);
    }

    boolean inventory() {
        if (Arrays.asList(this.command).contains("inventory") || Arrays.asList(this.command).contains("inv")) {
            currentPlayer.artefactDescribe(this.out);
            return true;
        }
        return false;
    }

    boolean look() {
        if (Arrays.asList(this.command).contains("look")) {
            currentPlayer.getLocation().loctionDescribe(this.currentPlayer,this.out);
            return true;
        }
        return false;
    }

    boolean get() {
        Artefact getArtefact = null;
        try {
            if (Arrays.asList(this.command).contains("get")) {
                for (String s : this.command) {
                    getArtefact = currentPlayer.getLocation().getArtefact(s);
                    if (getArtefact != null) break;
                }
                if (getArtefact != null) {
                    currentPlayer.setArtefact(getArtefact);
                    currentPlayer.getLocation().removeArtefact(getArtefact);
                    this.out.write("You got the " + getArtefact.getName() + ".\n");
                    return true;
                } else {
                    this.out.write("There is not your collect artefact in your location.\n");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean drop() {
        Artefact dropArtefact = null;
        try {
            if (Arrays.asList(this.command).contains("drop")) {
                for (String s : this.command) {
                    dropArtefact = this.currentPlayer.getArtefact(s);
                    if (dropArtefact != null) break;
                }
                if (dropArtefact != null) {
                    currentPlayer.getLocation().setArtefact(dropArtefact);
                    currentPlayer.removeArtefact(dropArtefact);
                    this.out.write("You drop the " + dropArtefact.getName() + ".\n");
                    return true;
                } else {
                    this.out.write("There is not your drop artefact in your backpack.\n");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean goTo() {
        Location goToLocation = null;
        try {
            if (Arrays.asList(this.command).contains("goto")) {
                for (String s : this.command) {
                    goToLocation = this.currentPlayer.getLocation().getPath(s);
                    if (goToLocation != null) break;
                }
                if (goToLocation != null) {
                    currentPlayer.getLocation().removePlayer(currentPlayer);
                    currentPlayer.setLocation(goToLocation);
                    currentPlayer.getLocation().setPlayer(currentPlayer);
                    this.out.write("You come to the " + goToLocation.getName() + ".\n");
                } else {
                    this.out.write("There is not path to location you want to goto.\n");
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean health(){
        if (Arrays.asList(this.command).contains("health")) {
            this.currentPlayer.getHealth(this.out);
            return true;
        }
        return false;
    }

    boolean PVP(){
        Player targetPlayer=null;
        Boolean PVE=false;
        String[] PVPWord= new String[]{"fight", "hit", "punch", "kick", "slap", "attack", "kill"};
        for(String literal:PVPWord){
            if (Arrays.asList(this.command).contains(literal)) {
                for(String s:command){
                    targetPlayer=this.currentPlayer.getLocation().getPlayer(s);
                    PVE=this.currentPlayer.getLocation().characterContains(s);
                    if((targetPlayer!=null)||PVE) break;
                }
                try {
                    if(targetPlayer!=null){
                        targetPlayer.reduceHealth();
                        this.out.write("You hit "+targetPlayer.getName()+", he/she bleeds blood, and gets angry.");
                        targetPlayer.setKiller(currentPlayer);
                        return true;
                    }else {
                        if(!PVE) {
                            this.out.write("Without finding that player, you punched it in the air.");
                            return true;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    boolean action() {
        boolean triggersSwitch;
        boolean subjectExactMatch;
        boolean subjectContain;
        ArrayList<Action> possibleAction = new ArrayList<>();
        try {
            for (Action action : world.getAction()) {
                triggersSwitch = false;
                subjectExactMatch = true;
                subjectContain = false;
                for (String s : this.command) {
                    if (action.getTrigger().contains(s)) {
                        triggersSwitch = true;
                    }
                    if (action.getSubjects().contains(s)) {
                        subjectContain = true;
                    }
                    if (subjectContain) {
                        for (String subject : action.getSubjects()) {
                            subjectExactMatch = subjectExactMatch & (Arrays.asList(this.command).contains(subject));
                        }
                    }
                }
                if (triggersSwitch & subjectContain & subjectExactMatch) {
                    return actionCheck(action);
                } else if (triggersSwitch & (!subjectExactMatch)) {
                    possibleAction.add(action);
                }
            }

            if(possibleAction.size()!=0){
                this.out.write("You can not do this with that entities!\n");
                possibleActionTips(possibleAction);
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean actionCheck(Action action) {
        boolean actionFlag = true;
        for (String subject : action.getSubjects()) {
            actionFlag = actionFlag &( this.currentPlayer.artefactContains(subject)
                    | this.currentPlayer.getLocation().funitureContains(subject)
                    | this.currentPlayer.getLocation().characterContains(subject));
            try {
                if (!actionFlag) {
                    this.out.write("There are not enough subjects to achieve this action.\n");
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        actionExecution(action);
        return true;
    }

    private void actionExecution(Action action) {
        try {
            for(String subject:action.getSubjects()){
                if(this.currentPlayer.getLocation().characterContains(subject)){
                    this.currentPlayer.getLocation().getCharacter(subject).characterDescribe(out);
                }
            }

            for(String narration:action.getNarration()){
                out.write(""+narration+".\n");
            }

            for (String consumed : action.getConsumed()) {
                if (consumed.equals("health")) {
                    this.currentPlayer.reduceHealth();
                    out.write("You are losing your health. Now, your health is "+this.currentPlayer.getHealth()+".\n");
                    if(this.currentPlayer.isDead()){
                        this.currentPlayer.dead(this.world.getStart(),out);
                    }
                } else if (this.currentPlayer.artefactContains(consumed)) {
                    out.write("You consume your "+consumed+".\n");
                    this.currentPlayer.removeArtefact(consumed);
                } else if (this.currentPlayer.getLocation().funitureContains(consumed)) {
                    out.write("You consume this location's "+consumed+".\n");
                    this.currentPlayer.getLocation().removeFuniture(consumed);
                }
            }

            for (String produces : action.getProduced()) {
                if (produces.equals("health")) {
                    this.currentPlayer.increaseHealth();
                    out.write("You are increasing your health. Now, your health is "+this.currentPlayer.getHealth()+".\n");
                } else if (this.world.locationContains(produces)) {
                    if(this.currentPlayer.getLocation().pathContains(produces)){
                        out.write("The path to "+produces+" has already existed.\n");
                    }else {
                        this.currentPlayer.getLocation().setPath(this.world.getLocation(produces));
                        out.write("A path to "+produces+" appeared.\n");
                    }
                }else if(this.world.locationContains("unplaced")){
                    if(this.world.getLocation("unplaced").artefactContains(produces)){
                        this.currentPlayer.getLocation().setArtefact(this.world.getLocation("unplaced").getArtefact(produces));
                        this.world.getLocation("unplaced").removeArtefact(produces);
                    }else if(this.world.getLocation("unplaced").funitureContains(produces)){
                        this.currentPlayer.getLocation().setFurniture(this.world.getLocation("unplaced").getFurniture(produces));
                        this.world.getLocation("unplaced").removeFuniture(produces);
                    }

                    out.write("A "+produces+" drop on the ground.\n");
                }else {
                    Artefact newArtefact=new Artefact();
                    newArtefact.setName(produces);
                    newArtefact.setDescription("A mysterious item.");
                    this.currentPlayer.getLocation().setArtefact(newArtefact);
                    out.write("A "+produces+" drop on the ground.\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void possibleActionTips(ArrayList<Action> possibleAction){
        try {
            for(Action action:possibleAction){
                for(String s:action.getSubjects()){
                    if(!Arrays.asList(command).contains(s)){
                        out.write("It seems need "+s+"?\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}