package com.company;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class World {
    private ArrayList<Location> Location=new ArrayList<>();
    private ArrayList<Action> Action=new ArrayList<>();
    private ArrayList<Player> Player=new ArrayList<>();
    private Location start=null;

    public World(String entityFilename, String actionFilename){
        setEntity(entityFilename);
        setAction(actionFilename);
        setStart();
    }

    public void setEntity(String entityFilename){
        try {
            Parser parser=new Parser();
            FileReader reader=new FileReader(entityFilename);
            parser.parse(reader);
            ArrayList<Graph> graphs=parser.getGraphs();
            ArrayList<Graph> subGraphs=graphs.get(0).getSubgraphs();
            for(Graph g:subGraphs){
                ArrayList<Graph> subGraph1=g.getSubgraphs();

                for(Graph g1:subGraph1){
                    ArrayList<Node> nodesloc =g1.getNodes(false);
                    Node nLoc=nodesloc.get(0);
                    Location newLocation=new Location();
                    newLocation.setName(nLoc.getId().getId());
                    ArrayList<Graph> subGraph2=g1.getSubgraphs();
                    for(Graph g2:subGraph2){
                        switch (g2.getId().getId()) {
                            case "artefacts": {
                                ArrayList<Node> nodesEnt = g2.getNodes(false);
                                for (Node nEnt : nodesEnt) {
                                    Artefact newArtefact = new Artefact();
                                    newArtefact.setName(nEnt.getId().getId());
                                    newArtefact.setDescription(nEnt.getAttribute("description"));
                                    newLocation.setArtefact(newArtefact);
                                }
                                break;
                            }
                            case "furniture": {
                                ArrayList<Node> nodesEnt = g2.getNodes(false);
                                for (Node nEnt : nodesEnt) {
                                    Furniture newFurniture = new Furniture();
                                    newFurniture.setName(nEnt.getId().getId());
                                    newFurniture.setDescription(nEnt.getAttribute("description"));
                                    newLocation.setFurniture(newFurniture);
                                }
                                break;
                            }
                            case "characters": {
                                ArrayList<Node> nodesEnt = g2.getNodes(false);
                                for (Node nEnt : nodesEnt) {
                                    Character newCharacter = new Character();
                                    newCharacter.setName(nEnt.getId().getId());
                                    newCharacter.setDescription(nEnt.getAttribute("description"));
                                    newLocation.setCharacter(newCharacter);
                                }
                                break;
                            }
                        }
                    }
                    this.Location.add(newLocation);
                }

                ArrayList<Edge> edges = g.getEdges();
                for(Edge e:edges){
                    for(Location locationFrom : Location){
                        for (Location locationTo : Location) {
                            if (locationFrom.getName().equals(e.getSource().getNode().getId().getId())
                                    & locationTo.getName().equals(e.getTarget().getNode().getId().getId())) {
                                locationFrom.setPath(locationTo);
                            }
                        }
                    }
                }
            }
        }catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void setAction(String actionFilename)  {
        try {
            JSONParser parser=new JSONParser();
            FileReader f=new FileReader(actionFilename);
            JSONObject json= (JSONObject) parser.parse(f);
            JSONArray jsonArray=(JSONArray) json.get("actions");
            for (Object o : jsonArray) {
                Action newAction = new Action();
                JSONObject jsonNewAction = (JSONObject) o;
                JSONArray triggers = (JSONArray) jsonNewAction.get("triggers");
                JSONArray subjects = (JSONArray) jsonNewAction.get("subjects");
                JSONArray consumed = (JSONArray) jsonNewAction.get("consumed");
                JSONArray produced = (JSONArray) jsonNewAction.get("produced");
                String narration = (String) jsonNewAction.get("narration");
                for (Object subO:triggers) { newAction.setTrigger((String)subO); }
                for (Object subO:subjects) { newAction.setSubjects((String)subO); }
                for (Object subO:consumed) { newAction.setConsumed((String)subO); }
                for (Object subO:produced) { newAction.setProduced((String)subO); }
                newAction.setNarration(narration);
                Action.add(newAction);
            }

        } catch (org.json.simple.parser.ParseException | IOException e){
            e.printStackTrace();
        }
    }

    void setStart(){
        this.start = Location.get(0);
    }

    void setPlayer(String name){
        boolean flag=false;
        for(Player player1:Player){
            if (player1.getName().equals(name)) {
                flag = true;
                break;
            }
        }
        if(!flag){
            Player newPlater=new Player(this.start);
            newPlater.setName(name);
            Player.add(newPlater);
        }
    }

    public Player getPlayer(String name) {
        for(Player player:Player){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }

    public ArrayList<Player> getPlayer() {
        return Player;
    }

    public ArrayList<Action> getAction() {
        return Action;
    }

    public ArrayList<Location> getLocation() {
        return Location;
    }

    public Location getStart() {
        return start;
    }
}

