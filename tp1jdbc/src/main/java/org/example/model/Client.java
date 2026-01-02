package org.example.model;

public class Client {
    private int numClient;
    private String nomClient;
    private String prenomClient;
    private String adClient;
    private java.sql.Date dateNaissClient;
    private int ageClient;
    private int numAgent;

    public Client(int numClient, String nomClient, String prenomClient, String adClient, java.sql.Date dateNaissClient, int numAgent) {
        this.numClient = numClient;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.adClient = adClient;
        this.dateNaissClient = dateNaissClient;
        // this.agentClient = ...
        this.numAgent = numAgent;
    }

    // Getters et setters
    public int getNumClient() { return numClient; }
    public String getNomClient() { return nomClient; }
    public String getPrenomClient() { return prenomClient; }
    public String getAdClient() { return adClient; }
    public java.sql.Date getDateNaissClient() { return dateNaissClient; }
    public int getAgeClient() { return ageClient; }
    public int getNumAgent() { return numAgent; }
    public void setNumAgent(int numAgent) { this.numAgent = numAgent; }
    public void setAgeClient(int ageClient) { this.ageClient = ageClient; }
}