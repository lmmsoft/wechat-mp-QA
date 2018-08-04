package com.lmm333.weixin.mp.model;

public class HelloWorldModel {
    private int id;
    private String name;
    private float weight;

    public HelloWorldModel() {
    }

    public HelloWorldModel(int id, String name, float weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
