import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

class Location extends Entity {
    private ArrayList<Artefact> Artefact=new ArrayList<>();
    private ArrayList<Furniture> Furniture=new ArrayList<>();
    private ArrayList<Location> Path= new ArrayList<>();
    private ArrayList<Character> Character=new ArrayList<>();
    private ArrayList<Player> Player=new ArrayList<>();
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

    public void setPlayer(Player player){
        this.Player.add(player);
    }

    public void removePlayer(Player currentPlayer){
        Player.remove(currentPlayer);
    }

    public Player getPlayer(String s){
        if(s==null||s.length()==0){
            return null;
        }
        for(Player player:this.Player){
            if(player.getName().equals(s)){
                return player;
            }
        }
        return null;
    }

    public ArrayList<Location> getPath() {
        return Path;
    }

    public Location getPath(String s){
        if(s==null||s.length()==0){
            return null;
        }
        for(Location location:this.Path){
            if(location.getName().equals(s)){
                return location;
            }
        }
        return null;
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

    public ArrayList<Furniture> getFurniture() {
        return Furniture;
    }

    public Furniture getFurniture(String s){
        if(s==null||s.length()==0){
            return null;
        }
        for(Furniture furniture:this.Furniture){
            if(furniture.getName().equals(s)){
                return furniture;
            }
        }
        return null;
    }

    public ArrayList<Character> getCharacter() {
        return Character;
    }

    public Character getCharacter(String s){
        if(s==null||s.length()==0){
            return null;
        }
        for(Character character:Character){
            if(character.getName().equals(s)){
                return character;
            }
        }
        return null;
    }

    public void loctionDescribe(Player currentPlayer,BufferedWriter out) throws IOException {
        out.write("You are at the "+this.getName()+". It is "+this.getDescription()+".\n");
        for(Artefact artefact:Artefact){
            out.write("    There is "+(artefact.nameIsVowel()?"an ":"a ")+artefact.getName()+ " which you could collect. " +
                    "It is "+artefact.getDescription()+".\n");
        }
        for(Furniture furniture:Furniture){
            out.write("    There is "+(furniture.nameIsVowel()?"an ":"a ")+furniture.getName()+
                    ". It is "+furniture.getDescription()+".\n");
        }
        for(Character character:Character){
            out.write("    There is "+(character.nameIsVowel()?"an ":"a ")+character.getName()+
                    ". It is "+character.getDescription()+".\n");
        }
        for(Location path:Path){
            out.write("    There is a way to "+path.getName()+". It is "+path.getDescription()+".\n");
        }
        for(Player player:Player){
            if(!player.getName().equals(currentPlayer.getName())){
                out.write("    There is a player named "+player.getName()+" looking at you.\n");
            }
        }
        out.newLine();
    }

    public void removeArtefact(Artefact artefact){
        this.Artefact.remove(artefact);
    }

    public void removeArtefact(String s){
        this.Artefact.removeIf(artefact -> artefact.getName().equals(s));
    }

    public void removeFuniture(String s){
        this.Furniture.removeIf(funiture -> funiture.getName().equals(s));
    }

    public boolean artefactContains(String s){
        for(Artefact artefact:this.Artefact){
            if(artefact.getName().equals(s)){
                return true;
            }
        }
        return false;
    }

    public boolean funitureContains(String s){
        for(Furniture furniture:this.Furniture){
            if(furniture.getName().equals(s)){
                return true;
            }
        }
        return false;
    }

    public boolean characterContains(String s){
        for(Character character:this.Character){
            if(character.getName().equals(s)){
                return true;
            }
        }
        return false;
    }

    public void removeCharacter(String s){
        this.Character.removeIf(character -> character.getName().equals(s));
    }

    public boolean pathContains(String s){
        for(Location location:this.Path){
            if(location.getName().equals(s)){
                return true;
            }
        }
        return false;
    }
    public void removePath(String s){
        this.Path.removeIf(path -> path.getName().equals(s));
    }
}
