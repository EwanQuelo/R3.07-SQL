package org.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.model.Agence;
import org.example.model.Agent;
import org.example.model.Client;
import org.example.model.Operation;

// Import de Log4j2
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BanqueDAO {
    // Initialisation du Logger
    private static final Logger logger = LogManager.getLogger(BanqueDAO.class);

    private Connection connection;

    public BanqueDAO(Connection connection) {
        this.connection = connection;
        logger.info("BanqueDAO initialisé avec une connexion active.");
    }

    public static Connection getConnection() throws SQLException {
        // On ne loggue pas le mot de passe pour la sécurité !
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        logger.debug("Tentative de connexion à la base de données : {}", url);
        return DriverManager.getConnection(url, user, password);
    }

    public void ajouterAgence(Agence agence) throws SQLException {
        logger.debug("Ajout de l'agence : {}", agence.getNumAgence());
        String insertSql = "INSERT INTO agence (numAgence, telAgence, adAgence) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setInt(1, agence.getNumAgence());
            ps.setString(2, agence.getTelAgence());
            ps.setString(3, agence.getAdAgence());
            ps.executeUpdate();
            logger.info("Agence {} ajoutée avec succès.", agence.getNumAgence());
        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout de l'agence : {}", e.getMessage());
            throw e;
        }
    }

    public void ajouterAgent(Agent agent) throws SQLException {
        logger.debug("Ajout de l'agent : {} {}", agent.getNomAgent(), agent.getPrenomAgent());
        String insertSql = "INSERT INTO agent (numAgent, nomAgent, prenomAgent, numAgence) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setInt(1, agent.getNumAgent());
            ps.setString(2, agent.getNomAgent());
            ps.setString(3, agent.getPrenomAgent());
            ps.setInt(4, agent.getNumAgence());
            ps.executeUpdate();
            logger.info("Agent {} ajouté avec succès.", agent.getNumAgent());
        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout de l'agent : {}", e.getMessage());
            throw e;
        }
    }

    public Client obtenirClientParNum(int numClient) throws SQLException {
        logger.debug("Recherche du client numéro : {}", numClient);
        String sql = "SELECT numClient, nomClient, prenomClient, adClient, dateNaissClient, numAgent FROM client WHERE numClient = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, numClient);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.debug("Client {} trouvé.", numClient);
                    return new Client(
                            rs.getInt("numClient"),
                            rs.getString("nomClient"),
                            rs.getString("prenomClient"),
                            rs.getString("adClient"),
                            rs.getDate("dateNaissClient"),
                            rs.getInt("numAgent")
                    );
                }
            }
        }
        logger.warn("Aucun client trouvé avec le numéro : {}", numClient);
        return null;
    }

    public void ajouterOperation(Operation operation) throws SQLException {
        logger.debug("Ajout d'une opération sur le compte {}", operation.getNumCompte());
        String insertSql = "INSERT INTO operation (numOperation, dateOperation, montant, typeOperation, numCompte) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setInt(1, operation.getNumOperation());
            ps.setDate(2, operation.getDateOperation());
            ps.setDouble(3, operation.getMontant());
            ps.setString(4, operation.getTypeOperation());
            ps.setInt(5, operation.getNumCompte());
            ps.executeUpdate();
            logger.info("Opération {} ajoutée.", operation.getNumOperation());
        }
    }

    public double obtenirSoldeCompte(int numCompte) throws SQLException {
        logger.debug("Calcul du solde pour le compte {}", numCompte);
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

    public List<Client> obtenirClientsParAgent(int numAgent) throws SQLException {
        String sql = "SELECT numClient, nomClient, prenomClient, adClient, dateNaissClient, numAgent FROM client WHERE numAgent = ?";
        List<Client> clients = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, numAgent);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Client c = new Client(
                            rs.getInt("numClient"),
                            rs.getString("nomClient"),
                            rs.getString("prenomClient"),
                            rs.getString("adClient"),
                            rs.getDate("dateNaissClient"),
                            rs.getInt("numAgent")
                    );
                    clients.add(c);
                }
            }
        }
        return clients;
    }

    /**
     * Méthode utilitaire pour vérifier l'existence (privée)
     */
    private boolean clientExisteDeja(int numClient) throws SQLException {
        String sql = "SELECT 1 FROM Client WHERE numClient = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numClient);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Ajout d'un client avec gestion de TRANSACTION
     */
    public void ajouterClient(Client client) throws SQLException {
        logger.debug("Tentative d'ajout du client : {} {}", client.getNomClient(), client.getPrenomClient());

        // 1. Vérification manuelle
        if (clientExisteDeja(client.getNumClient())) {
            logger.warn("Tentative d'ajout échouée : Le client {} existe déjà.", client.getNumClient());
            throw new SQLException("Client deja existant !");
        }

        String sql = "INSERT INTO Client (numClient, nomClient, prenomClient, adClient, dateNaissClient, ageClient, numAgent) VALUES (?, ?, ?, ?, ?, ?, ?)";

        boolean autoCommitOriginal = connection.getAutoCommit();

        try {
            // 2. Début de la transaction
            logger.debug("Début de la transaction pour l'ajout du client.");
            connection.setAutoCommit(false);

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, client.getNumClient());
                pstmt.setString(2, client.getNomClient());
                pstmt.setString(3, client.getPrenomClient());
                pstmt.setString(4, client.getAdClient());
                pstmt.setDate(5, client.getDateNaissClient());
                pstmt.setInt(6, client.getAgeClient());
                pstmt.setInt(7, client.getNumAgent());

                pstmt.executeUpdate();
            }

            // 3. Validation de la transaction
            connection.commit();
            logger.info("Client {} ajouté avec succès (Transaction validée).", client.getNumClient());

        } catch (SQLException e) {
            // 4. Annulation en cas d'erreur
            connection.rollback();
            logger.error("Erreur critique lors de l'ajout du client. Rollback effectué. Raison : {}", e.getMessage());
            throw e;
        } finally {
            connection.setAutoCommit(autoCommitOriginal);
        }
    }

    /**
     * Lie un compte existant à un client existant.
     */
    public void ajouterCompteClient(int numCompte, int numClient) throws SQLException {
        logger.debug("Liaison du compte {} au client {}", numCompte, numClient);
        String sql = "INSERT INTO Compte_Client (numCompte, numClient) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numCompte);
            pstmt.setInt(2, numClient);
            pstmt.executeUpdate();
            logger.info("Lien Compte {} <-> Client {} créé avec succès.", numCompte, numClient);
        } catch (SQLException e) {
            logger.error("Erreur lors de la liaison Compte-Client : {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Récupère la liste des comptes appartenant à un client.
     */
    public List<org.example.model.Compte> obtenirComptesParClient(int numClient) throws SQLException {
        logger.debug("Récupération des comptes pour le client {}", numClient);
        List<org.example.model.Compte> comptes = new ArrayList<>();

        String sql = "SELECT c.numCompte, c.solde, c.typeCompte " +
                "FROM Compte c " +
                "JOIN Compte_Client cc ON c.numCompte = cc.numCompte " +
                "WHERE cc.numClient = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numClient);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    org.example.model.Compte compte = new org.example.model.Compte(
                            rs.getInt("numCompte"),
                            rs.getDouble("solde"),
                            rs.getString("typeCompte")
                    );
                    comptes.add(compte);
                }
            }
        }
        logger.info("{} comptes trouvés pour le client {}.", comptes.size(), numClient);
        return comptes;
    }
}