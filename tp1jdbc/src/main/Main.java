package org.example;

import org.example.model.Client;
import org.example.dao.BanqueDAO;
// CORRECTION 1 : Import manquant ajouté
import java.util.List;
import java.sql.*;

public class Main
{
    // Méthode utilitaire (inchangée)
    public boolean printUserEmail() throws SQLException {
        boolean connectionValid = false;
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");
        System.out.println("DB_URL: " + dbUrl);

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            connectionValid = connection.isValid(2);
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[] {"TABLE"});
            System.out.println("Tables in the database:");
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_NAME"));
            }
        }
        return connectionValid;
    }

    public static void main( String[] args )
    {
        System.out.println( "Démarrage de l'application..." );

        // Début du bloc TRY. 'connection' existe à partir d'ici.
        try (Connection connection = BanqueDAO.getConnection()) {

            BanqueDAO dao = new BanqueDAO(connection); // 'dao' existe à partir d'ici.
            System.out.println("Connexion réussie !");

            // --- PARTIE 1 : CLIENT ---
            Client nouveauClient = new Client(
                    101,
                    "TestNom",
                    "TestPrenom",
                    "10 Rue du Test",
                    Date.valueOf("2000-01-01"),
                    1
            );
            nouveauClient.setAgeClient(25);

            System.out.println("Tentative d'insertion du client...");

            // On gère le cas où le client existe déjà pour éviter que le programme plante au 2ème lancement
            try {
                dao.ajouterClient(nouveauClient);
                System.out.println("Client inséré avec succès");
            } catch (SQLException e) {
                System.out.println("Info : Le client existe déjà ou erreur : " + e.getMessage());
            }

            // --- PARTIE 2 : COMPTE ET LIEN (CORRECTION : TOUT EST MAINTENANT DANS LE TRY) ---

            // 1. Insérer un Compte (via SQL brut pour l'exemple)
            try (java.sql.Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("INSERT INTO Compte (numCompte, solde, typeCompte) VALUES (500, 1000.0, 'Epargne') ON CONFLICT DO NOTHING");
            }

            System.out.println("Ajout du lien Compte-Client...");

            // 2. Lier le Client (101) au Compte (500)
            // On protège aussi cet appel au cas où le lien existe déjà
            try {
                dao.ajouterCompteClient(500, 101);
                System.out.println("Lien créé !");
            } catch (SQLException e) {
                System.out.println("Info : Lien déjà existant ou erreur : " + e.getMessage());
            }

            // 3. Vérifier en récupérant les comptes du client
            System.out.println("Recherche des comptes du client 101...");
            List<org.example.model.Compte> comptes = dao.obtenirComptesParClient(101);

            for (org.example.model.Compte c : comptes) {
                System.out.println(" -> Compte trouvé : " + c.getTypeCompte() + " (Solde: " + c.getSolde() + ")");
            }

        } catch (SQLException e) {
            // Fin du scope : ici 'connection' et 'dao' n'existent plus
            System.err.println("Erreur SQL critique : " + e.getMessage());
            e.printStackTrace();
        }
    }
}