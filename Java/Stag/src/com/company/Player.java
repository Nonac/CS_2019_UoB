import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Character {
    private Location location;
    private int rebirth;
    private boolean firstLogIn;
    private ArrayList<Player> killer=new ArrayList<>();
    private ArrayList<Artefact> Artefact=new ArrayList<>();

    public Player(Location location){
        this.location=location;
        this.setProperty("Player");
        this.setRebirth(0);
        this.setFirstLogIn(true);
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

    public void setKiller(Player player){
        if(!this.killerContains(player)){
            this.killer.add(player);
        }
    }

    public ArrayList<Player> getKiller() {
        return killer;
    }

    public boolean killerContains(Player player){
        for(Player killer:this.killer){
            if(killer.equals(player)){
                return true;
            }
        }
        return false;
    }

    public void revenge(BufferedWriter out) throws IOException {
        if(this.isDead()){
            out.write("You are killed by ");
        }else {
            out.write("You are punched by ");
        }

        for(Player player:this.killer){
            out.write(player.getName()+" ");
        }
        out.write(".\n");

        killer=new ArrayList<>();
    }

    public void artefactDescribe(BufferedWriter out) throws IOException {
        if(Artefact.size()==0){
            out.write("There is nothing artefact currently carried by "+this.getName()+".\n");
        }else {
            out.write("There are artefacts currently carried by "+this.getName()+":\n");
            for(Artefact artefact:Artefact){
                out.write("    There is "+(artefact.nameIsVowel()?"an ":"a ")+artefact.getName()+ ". " +
                        "It is "+(artefact.descriptionIsVowel()?"an ":"a ")+artefact.getDescription()+".\n");
            }
        }
    }

    public void getHealth(BufferedWriter out) throws IOException {
        out.write("Your health is "+this.getHealth()+".\n");
    }

    public void removeArtefact(Artefact artefact)throws NullPointerException{
        this.Artefact.remove(artefact);
    }

    private void removeArtefact(ArrayList<Artefact> artefact)throws NullPointerException{
        if (artefact.size() > 0) {
            artefact.subList(0, artefact.size()).clear();
        }
    }

    public void removeArtefact(String s)throws NullPointerException{
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

    public void dead(Location location,BufferedWriter out) throws NullPointerException, IOException {
        out.write("You are dead.\n");
        for(Artefact artefact:this.Artefact){
            this.getLocation().setArtefact(artefact);
            out.write("Your "+artefact.getName()+" drop on the ground.\n");
        }
        this.removeArtefact(this.Artefact);
        this.location=location;

        setHealth(3);
        this.rebirth++;
        out.write("You are reborn where you started.\n");
        out.newLine();
    }

    private void setRebirth(int n){
        this.rebirth=n;
    }

    public int getRebirth() {
        return rebirth;
    }

    public boolean getFirstLogIn(){
        return this.firstLogIn;
    }

    public void setFirstLogIn(boolean firstLogIn) {
        this.firstLogIn = firstLogIn;
    }
}
