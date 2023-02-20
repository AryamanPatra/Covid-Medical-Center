package com.example.common;

public class MedCentre {
    private int slots;
    private String centreName;

    public MedCentre(String centreName,int slots){
        this.centreName = centreName;
        this.slots = slots;
    }

    public int getSlots(){
        return slots;
    }

    public String getCentreName() {
        return centreName;
    }
}