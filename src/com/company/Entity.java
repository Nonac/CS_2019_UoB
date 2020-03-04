package com.company;

class Entity {
    private String property;
    private String name;
    private String description;
    public Entity(){
        this.name=null;
        this.description=null;
        this.property=null;
    }
    public void setName(String name){
        this.name=name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getProperty() {
        return property;
    }
    public void setProperty(String property) {
        this.property = property;
    }
    public boolean nameIsVowel(){
        switch (this.name.charAt(0)){
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:return false;
        }
    }
    public boolean descriptionIsVowel(){
        switch (this.description.charAt(0)){
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:return false;
        }
    }
}

