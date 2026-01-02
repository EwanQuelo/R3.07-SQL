package org.example.model;

public class Compte {
    private int numCompte;
    private double solde;
    private String typeCompte;

    public Compte(int numCompte, double solde, String typeCompte) {
        this.numCompte = numCompte;
        this.solde = solde;
        this.typeCompte = typeCompte;
    }

    // Getters et setters
    public int getNumCompte() { return numCompte; }
    public double getSolde() { return solde; }
    public String getTypeCompte() { return typeCompte; }
}