package org.example.model;

public class Operation {
    private int numOperation;
    private java.sql.Date dateOperation;
    private String typeOperation;
    private double montant;
    private int numCompte;

    public Operation(int numOperation, java.sql.Date dateOperation, String typeOperation, double montant) {
        this.numOperation = numOperation;
        this.dateOperation = dateOperation;
        this.typeOperation = typeOperation;
        this.montant = montant;
    }

    // Getters et setters
    public int getNumOperation() { return numOperation; }
    public java.sql.Date getDateOperation() { return dateOperation; }
    public String getTypeOperation() { return typeOperation; }
    public double getMontant() { return montant; }
    public int getNumCompte() { return numCompte; }

    public void setNumOperation(int numOperation) { this.numOperation = numOperon; }
    public void setDateOperation(java.sql.Date dateOperation) { this.dateOperation = dateOperation; }
    public void setTypeOperation(String typeOperation) { this.typeOperation = typeOperation; }
    public void setMontant(double montant) { this.montant = montant; }
    public void setNumCompte(int numCompte) { this.numCompte = numCompte; }

}
