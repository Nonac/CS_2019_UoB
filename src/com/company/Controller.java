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

    void readCommand(String in, BufferedWriter out) throws IOException,NoSuchMethodError {

        String[] temp = in.split(":\\s|\\s");
        this.world.setPlayer(temp[0]);
        this.currentPlayer = world.getPlayer(temp[0]);
        setCommand(temp);
        this.out = out;

        if (this.currentPlayer.getFirstLogIn()) {
            out.write("Welcome to Stag.\n");
            out.newLine();
            this.currentPlayer.setFirstLogIn(false);
        }
        if(this.currentPlayer.isDead()){
            if(this.currentPlayer.getKiller().size()!=0){
                this.currentPlayer.revenge(this.out);
                this.out.newLine();
            }
            this.world.getStart().setPlayer(this.currentPlayer);
            this.currentPlayer.getLocation().removePlayer(this.currentPlayer);
            this.currentPlayer.dead(this.world.getStart(),out);
        }else if(this.currentPlayer.getKiller().size()!=0){
            this.currentPlayer.revenge(this.out);
            this.currentPlayer.getHealth(out);
            this.out.newLine();
        }
        if (!(inventory() | get() | look() | drop() | goTo() |health()||PVP()| actionTriggersRecognition())) {
            out.write("I do not understand what you are saying.");
        }
    }

    void setCommand(String[] temp) {
        command = new String[temp.length - 1];
        System.arraycopy(temp, 1, this.command, 0, temp.length - 1);
    }

    boolean inventory() throws IOException {
        if (Arrays.asList(this.command).contains("inventory") || Arrays.asList(this.command).contains("inv")) {
            currentPlayer.artefactDescribe(this.out);
            return true;
        }
        return false;
    }

    boolean look() throws IOException {
        if (Arrays.asList(this.command).contains("look")) {
            currentPlayer.getLocation().loctionDescribe(this.currentPlayer,this.out);
            return true;
        }
        return false;
    }

    boolean get() throws IOException {
        Artefact getArtefact = null;

        if (Arrays.asList(this.command).contains("get")) {
            for (String s : this.command) {
                getArtefact = currentPlayer.getLocation().getArtefact(s);
                if (getArtefact != null) break;
            }
            if (getArtefact != null) {
                currentPlayer.setArtefact(getArtefact);
                currentPlayer.getLocation().removeArtefact(getArtefact);
                this.out.write("You get the " + getArtefact.getName() + ".\n");
                return true;
            } else {
                this.out.write("There is not the artefact yor wanna get here.\n");
                return true;
            }
        }
        return false;
    }

    boolean drop() throws IOException {
        Artefact dropArtefact = null;
        if (Arrays.asList(this.command).contains("drop")) {
            for (String s : this.command) {
                dropArtefact = this.currentPlayer.getArtefact(s);
                if (dropArtefact != null) break;
            }
            if (dropArtefact != null) {
                currentPlayer.getLocation().setArtefact(dropArtefact);
                currentPlayer.removeArtefact(dropArtefact);
                this.out.write("You drop the " + dropArtefact.getName() + " on the ground.\n");
            } else {
                this.out.write("There is not your drop artefact in your backpack.\n");
            }
            return true;
        }
        return false;
    }

    boolean goTo() throws IOException {
        Location goToLocation = null;

        if (Arrays.asList(this.command).contains("goto")) {
            for (String s : this.command) {
                goToLocation = this.currentPlayer.getLocation().getPath(s);
                if (goToLocation != null) break;
            }
            if (goToLocation != null) {
                currentPlayer.getLocation().removePlayer(currentPlayer);
                currentPlayer.setLocation(goToLocation);
                currentPlayer.getLocation().setPlayer(currentPlayer);
                this.out.write("You come to the " + goToLocation.getName() + ". It is "
                        +goToLocation.getDescription()+".\n");
            } else {
                this.out.write("There is not path to location you want to go.\n");
            }
            return true;
        }
        return false;
    }

    boolean health() throws IOException {
        if (Arrays.asList(this.command).contains("health")) {
            this.currentPlayer.getHealth(this.out);
            return true;
        }
        return false;
    }

    boolean PVP() throws IOException {
        Player targetPlayer=null;
        boolean PVE=false;
        String[] PVPWord= new String[]{"fight", "hit", "punch", "kick", "slap", "attack", "kill"};
        for(String literal:PVPWord){
            if (Arrays.asList(this.command).contains(literal)) {
                for(String s:command){
                    targetPlayer=this.currentPlayer.getLocation().getPlayer(s);
                    PVE=this.currentPlayer.getLocation().characterContains(s);
                    if((targetPlayer!=null)||PVE) break;
                }

                if(targetPlayer!=null){
                    if(targetPlayer!=this.currentPlayer){
                        targetPlayer.reduceHealth();
                        this.out.write("You hit "+targetPlayer.getName()+", he/she bleeds blood, and gets angry.");
                        targetPlayer.setKiller(currentPlayer);
                    }else {
                        this.out.write("You can not punch yourself!\n");
                    }
                    return true;
                }else {
                    if(!PVE) {
                        this.out.write("Without finding that player, you punched it in the air.");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean actionTriggersRecognition() throws IOException {
        boolean triggersSwitch;
        boolean subjectExactMatch;
        boolean subjectContain;
        ArrayList<Action> possibleAction = new ArrayList<>();
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
                    return actionSubjectCheck(action);
                } else if (triggersSwitch & (!subjectExactMatch)) {
                    possibleAction.add(action);
                }
            }

            if(possibleAction.size()!=0){
                this.out.write("You can not do this with that entity!\n");
                possibleActionTips(possibleAction);
                return true;
            }

        return false;
    }

    public boolean actionSubjectCheck(Action action) throws IOException {
        boolean actionFlag;
        for (String subject : action.getSubjects()) {
            actionFlag=( this.currentPlayer.artefactContains(subject)
                    | this.currentPlayer.getLocation().funitureContains(subject)
                    | this.currentPlayer.getLocation().characterContains(subject));

            if (!actionFlag) {
                this.out.write("There are not enough subjects to achieve this action.\n");
                return true;
            }
        }
        actionExecution(action);
        return true;
    }

    private void actionExecution(Action action) throws IOException {
        for(String subject:action.getSubjects()){
            if(this.currentPlayer.getLocation().characterContains(subject)){
                this.currentPlayer.getLocation().getCharacter(subject).characterDescribe(out);
            }
        }
        actionExecutionConsumed(action);
        actionExecutionProduces(action);
        for(String narration:action.getNarration()){
            out.write(""+narration+".\n");
        }
    }

    void actionExecutionConsumed(Action action) throws IOException {
        for (String consumed : action.getConsumed()) {
            if (consumed.equals("health")) {
                this.currentPlayer.reduceHealth();
                out.write("You are losing your health. Now, your health is "+this.currentPlayer.getHealth()+".\n");
                if(this.currentPlayer.isDead()){
                    this.world.getStart().setPlayer(this.currentPlayer);
                    this.currentPlayer.getLocation().removePlayer(this.currentPlayer);
                    this.currentPlayer.dead(this.world.getStart(),out);
                }
            } else if (this.currentPlayer.artefactContains(consumed)) {
                out.write("You consume your "+consumed+".\n");
                this.currentPlayer.removeArtefact(consumed);
            } else if (this.currentPlayer.getLocation().funitureContains(consumed)) {
                out.write("You consume this location's "+consumed+".\n");
                this.currentPlayer.getLocation().removeFuniture(consumed);
            } else if(this.currentPlayer.getLocation().characterContains(consumed)){
                out.write(""+consumed+" is dead.\n");
                this.currentPlayer.getLocation().removeCharacter(consumed);
            }else if(this.currentPlayer.getLocation().pathContains(consumed)){
                out.write("The path to"+consumed+" is closed.\n");
                this.currentPlayer.getLocation().removePath(consumed);
            }
        }
    }

    void actionExecutionProduces(Action action) throws IOException{
        boolean newArtefactFlag=true;
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
                }else if(this.world.getLocation("unplaced").characterContains(produces)){
                    this.currentPlayer.getLocation().setCharacter(this.world.getLocation("unplaced").getCharacter(produces));
                    this.world.getLocation("unplaced").removeCharacter(produces);
                }
                out.write("A "+produces+" drop on the ground.\n");
            }else {
                for(Location location:this.world.getLocation()){
                    if((location.getArtefact(produces)!=null)||
                            (location.getCharacter(produces)!=null)||
                            (location.getFurniture(produces)!=null)){
                        newArtefactFlag=false;
                        break;
                    }
                }
                if(newArtefactFlag){
                    Artefact newArtefact=new Artefact();
                    newArtefact.setName(produces);
                    newArtefact.setDescription("A mysterious item.");
                    this.currentPlayer.getLocation().setArtefact(newArtefact);
                    out.write("A "+produces+" drop on the ground.\n");
                }else {
                    out.write("The creator seems to have made a mistake, " +
                            "and the resulting items cannot have taboo names.");
                }
            }
        }
    }

    void possibleActionTips(ArrayList<Action> possibleAction) throws IOException {
        for(Action action:possibleAction){
            for(String s:action.getSubjects()){
                if(!Arrays.asList(command).contains(s)){
                    out.write("It seems need "+s+"?\n");
                }
            }
        }
    }
}