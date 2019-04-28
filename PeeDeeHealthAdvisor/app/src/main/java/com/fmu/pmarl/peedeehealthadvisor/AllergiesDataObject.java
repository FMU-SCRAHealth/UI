package com.example.pmarl.peedeehealthadvisor;

public class AllergiesDataObject {



    private String allergyName;
    private String allergyDescription;



    AllergiesDataObject(String name, String description) {

        allergyName = name;
        allergyDescription = description;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }

    public String getAllergyDescription() {
        return allergyDescription;
    }

    public void setAllergyDescription(String allergyDescription) {
        this.allergyDescription = allergyDescription;
    }

}