package com.votreprojet.service;

import com.votreprojet.dao.ClientDAO;
import com.votreprojet.dto.ClientDTO;
import com.votreprojet.mapper.ClientMapper;
import com.votreprojet.model.Client;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class ClientService {
    private final ClientDAO clientDAO;

    public ClientService(EntityManager entityManager) {
        this.clientDAO = new ClientDAO(entityManager);
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = ClientMapper.toEntity(clientDTO);
        clientDAO.save(client);
        return ClientMapper.toDTO(client);
    }

    public ClientDTO getClient(int id) {
        return clientDAO.find(id)
                .map(ClientMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public List<ClientDTO> getAllClients() {
        return clientDAO.findAll().stream()
                .map(ClientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ClientDTO> getClientsByAgent(int numAgent) {
        return clientDAO.findByAgent(numAgent).stream()
                .map(ClientMapper::toDTO)
                .collect(Collectors.toList());
    }
}
