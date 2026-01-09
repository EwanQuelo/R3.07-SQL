package com.votreprojet.mapper;

import com.votreprojet.dto.ClientDTO;
import com.votreprojet.model.Client;

public class ClientMapper {
    public static ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setNumClient(client.getNumClient());
        dto.setNomClient(client.getNomClient());
        dto.setPrenomClient(client.getPrenomClient());
        dto.setAdClient(client.getAdClient());
        dto.setDateNaissClient(client.getDateNaissClient());
        dto.setAgeClient(client.getAgeClient());
        dto.setNumAgent(client.getAgent() != null ? client.getAgent().getNumAgent() : 0);
        return dto;
    }

    public static Client toEntity(ClientDTO dto) {
        Client client = new Client();
        client.setNumClient(dto.getNumClient());
        client.setNomClient(dto.getNomClient());
        client.setPrenomClient(dto.getPrenomClient());
        client.setAdClient(dto.getAdClient());
        client.setDateNaissClient(dto.getDateNaissClient());
        client.setAgeClient(dto.getAgeClient());
        // Note: La relation avec Agent doit etre geree separement
        return client;
    }
}
