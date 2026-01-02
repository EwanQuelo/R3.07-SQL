package org.example.model;

public class CompteClient {
    private int numCompte;
    private int numClient;

    public CompteClient(int numCompte, int numClient) {
        this.numCompte = numCompte;
        this.numClient = numClient;
    }

    public int getNumCompte() { return numCompte; }
    public void setNumCompte(int numCompte) { this.numCompte = numCompte; }

    public int getNumClient() { return numClient; }
    public void setNumClient(int numClient) { this.numClient = numClient; }
}