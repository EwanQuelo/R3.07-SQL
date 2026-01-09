package com.votreprojet.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numClient")
    private int numClient;

    @Column(name = "nomClient", nullable = false)
    private String nomClient;

    @Column(name = "prenomClient", nullable = false)
    private String prenomClient;

    @Column(name = "adClient")
    private String adClient;

    @Column(name = "dateNaissClient")
    @Temporal(TemporalType.DATE)
    private Date dateNaissClient;

    @Column(name = "ageClient")
    private int ageClient;

    @ManyToOne
    @JoinColumn(name = "numAgent", referencedColumnName = "numAgent")
    private Agent agent;

    @ManyToMany
    @JoinTable(
        name = "Compte_Client",
        joinColumns = @JoinColumn(name = "numClient"),
        inverseJoinColumns = @JoinColumn(name = "numCompte")
    )
    private List<Compte> comptes;

    // Constructeurs, getters et setters
}
