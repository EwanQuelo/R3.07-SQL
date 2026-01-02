package org.example.model;

import java.sql.Date;

public class Operation {
    private int numOperation;
    private Date dateOperation;
    private String typeOperation;
    private double montant;
    private int numCompte;

    public Operation(int numOperation, Date dateOperation, double montant, String typeOperation, int numCompte) {
        this.numOperation = numOperation;
        this.dateOperation = dateOperation;
        this.montant = montant;
        this.typeOperation = typeOperation;
        this.numCompte = numCompte;
    }

    // Getters
    public int getNumOperation() { return numOperation; }
    public Date getDateOperation() { return dateOperation; }
    public String getTypeOperation() { return typeOperation; }
    public double getMontant() { return montant; }
    public int getNumCompte() { return numCompte; }

    // Setters
    public void setNumOperation(int numOperation) {
        this.numOperation = numOperation;
    }
    public void setDateOperation(Date dateOperation) { this.dateOperation = dateOperation; }
    public void setTypeOperation(String typeOperation) { this.typeOperation = typeOperation; }
    public void setMontant(double montant) { this.montant = montant; }
    public void setNumCompte(int numCompte) { this.numCompte = numCompte; }
}