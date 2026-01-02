package org.example.model;

public class Agence {
    private int numAgence;
    private String telAgence;
    private String adAgence;

    public Agence(int numAgence, String telAgence, String adAgence) {
        this.numAgence = numAgence;
        this.telAgence = telAgence;
        this.adAgence = adAgence;
    }

    public int getNumAgence() {
        return numAgence;
    }
    public String getTelAgence() {
        return telAgence;
    }
    public String getAdAgence() {
        return adAgence;
    }

    public void setNumAgence(int numAgence) {
        this.numAgence = numAgence;
    }

    public void setTelAgence(String telAgence) {
        this.telAgence = telAgence;
    }

    public void setAdAgence(String adAgence) {
        this.adAgence = adAgence;
    }
}