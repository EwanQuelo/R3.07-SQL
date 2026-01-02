package org.example.model;

public class Agent {
    private int numAgent;
    private String nomAgent;
    private String prenomAgent;
    private double salaire;
    private boolean estDirecteur;
    private int numAgence;

    public Agent(int numAgent, String nomAgent, String prenomAgent, double salaire, boolean estDirecteur, int numAgence) {
        this.numAgent = numAgent;
        this.nomAgent = nomAgent;
        this.prenomAgent = prenomAgent;
        this.salaire = salaire;
        this.estDirecteur = estDirecteur;
        this.numAgence = numAgence;
    }

    // Getters et setters
    public int getNumAgent() {
        return numAgent;
    }

    public void setNumAgent(int numAgent) {
        this.numAgent = numAgent;
    }

    public String getNomAgent() {
        return nomAgent;
    }

    public void setNomAgent(String nomAgent) {
        this.nomAgent = nomAgent;
    }

    public String getPrenomAgent() {
        return prenomAgent;
    }

    public void setPrenomAgent(String prenomAgent) {
        this.prenomAgent = prenomAgent;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public boolean isEstDirecteur() {
        return estDirecteur;
    }

    public void setEstDirecteur(boolean estDirecteur) {
        this.estDirecteur = estDirecteur;
    }

    public int getNumAgence() {
        return numAgence;
    }

    public void setNumAgence(int numAgence) {
        this.numAgence = numAgence;
    }
}
