package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.model.Agence;
import org.example.model.Agent;
import org.example.model.Client;
import org.example.model.Operation;

public class BanqueDAO {
    private Connection connection;


    public BanqueDAO(Connection connection) {
        this.connection = connection;
    }


    public static Connection getConnection() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        return DriverManager.getConnection(url, user, password);
    }

    public void ajouterAgence(Agence agence) throws SQLException {
        String insertSql = "INSERT INTO agence (numAgence, telAgence, adAgence) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setInt(1, agence.getNumAgence());
            ps.setString(2, agence.getTelAgence());
            ps.setString(3, agence.getAdAgence());
            ps.executeUpdate();
        }
    }

    public void ajouterAgent(Agent agent) throws SQLException {
        String insertSql = "INSERT INTO agent (numAgent, nomAgent, prenomAgent, telAgent, numAgence) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setInt(1, agent.getNumAgent());
            ps.setString(2, agent.getNomAgent());
            ps.setString(3, agent.getPrenomAgent());
            ps.setString(4, agent.getTelAgent());
            ps.setInt(5, agent.getNumAgence());
            ps.executeUpdate();
        }
    }   

    public Client obtenirClientParNum(int numClient) throws SQLException {
        String sql = "SELECT numClient, nomClient, prenomClient, adClient, numAgent FROM client WHERE numClient = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, numClient);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Assumes Client has a constructor: Client(int numClient, String nom, String prenom, String adresse, int numAgent)
                    return new Client(
                        rs.getInt("numClient"),
                        rs.getString("nomClient"),
                        rs.getString("prenomClient"),
                        rs.getString("adClient"),
                        rs.getInt("numAgent")
                    );
                }
            }
        }
        return null;
    }

    public void ajouterOperation(Operation operation) throws SQLException {
        String insertSql = "INSERT INTO operation (numOperation, dateOperation, montant, typeOperation, numCompte) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setInt(1, operation.getNumOperation());
            // Assumes Operation#getDateOperation returns java.sql.Date
            ps.setDate(2, operation.getDateOperation());
            ps.setDouble(3, operation.getMontant());
            ps.setString(4, operation.getTypeOperation());
            ps.setInt(5, operation.getNumCompte());
            ps.executeUpdate();
        }
    }

    public double obtenirSoldeCompte(int numCompte) throws SQLException {
        // Assumes typeOperation uses 'CREDIT' / 'DEBIT' (credits add, debits subtract).
        String sql = "SELECT COALESCE(SUM(CASE WHEN typeOperation = 'DEBIT' THEN -montant WHEN typeOperation = 'CREDIT' THEN montant ELSE montant END),0) AS solde FROM operation WHERE numCompte = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, numCompte);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("solde");
                }
            }
        }
        return 0.0;
    }

    public List<Operation> obtenirOperationsParCompte(int numCompte) throws SQLException {
        String sql = "SELECT numOperation, dateOperation, montant, typeOperation, numCompte FROM operation WHERE numCompte = ? ORDER BY dateOperation DESC";
        List<Operation> ops = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, numCompte);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Assumes Operation has a constructor: Operation(int numOp, java.sql.Date date, double montant, String type, int numCompte)
                    Operation op = new Operation(
                        rs.getInt("numOperation"),
                        rs.getDate("dateOperation"),
                        rs.getDouble("montant"),
                        rs.getString("typeOperation"),
                        rs.getInt("numCompte")
                    );
                    ops.add(op);
                }
            }
        }
        return ops;
    }

    public List<Client>  obtenirClientsParAgent(int numAgent) throws SQLException {
        String sql = "SELECT numClient, nomClient, prenomClient, adClient, numAgent FROM client WHERE numAgent = ?";
        List<Client> clients = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, numAgent);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Assumes Client has a constructor: Client(int numClient, String nom, String prenom, String adresse, int numAgent)
                    Client c = new Client(
                        rs.getInt("numClient"),
                        rs.getString("nomClient"),
                        rs.getString("prenomClient"),
                        rs.getString("adClient"),
                        rs.getInt("numAgent")
                    );
                    clients.add(c);
                }
            }
        }
        return clients;
    }
}