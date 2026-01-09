package com.votreprojet.dao;

import com.votreprojet.model.Client;
import jakarta.persistence.EntityManager;

public class ClientDAO extends AbstractDAO<Client, Integer> {
    public ClientDAO(EntityManager entityManager) {
        super(Client.class, entityManager);
    }

    public List<Client> findByAgent(int numAgent) {
        return entityManager.createQuery(
            "SELECT c FROM Client c WHERE c.agent.numAgent = :numAgent", Client.class)
            .setParameter("numAgent", numAgent)
            .getResultList();
    }
}
