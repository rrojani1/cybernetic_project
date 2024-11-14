package com.cybernetic;
//import lombok.Data;

class Organ {
    private String id;  // New property
    private String name;
    private String bloodType;
    private int weight; // grams
    private String hlaType; // Human Leukocyte Antigen

    public Organ(String id,String name, String bloodType, int weight, String hlaType) {
        this.id = id;
        this.name = name;
        this.bloodType = bloodType;
        this.weight = weight;
        this.hlaType = hlaType;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getBloodType() { return bloodType; }
    public int getWeight() { return weight; }
    public String getHlaType() { return hlaType; }
}