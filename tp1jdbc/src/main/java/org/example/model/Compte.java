package com.votreprojet.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Compte")
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numCompte")
    private int numCompte;

    @Column(name = "solde")
    private double solde;

    @Column(name = "typeCompte", nullable = false)
    private String typeCompte;

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    private List<Operation> operations;

    @ManyToMany(mappedBy = "comptes")
    private List<Client> clients;

    // Constructeurs, getters et setters
}
