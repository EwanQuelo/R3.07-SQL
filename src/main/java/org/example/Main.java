package org.example;

import java.sql.*;


/**
 * Hello world!
 *
 */
public class Main 
{
    public boolean printUserEmail() throws SQLException {
        boolean connectionValid = false;

        // get DB_URL, DB_USER, DB_PASSWORD from environment variables
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");
        System.out.println("DB_URL: " + dbUrl);
        System.out.println("DB_USER: " + dbUser);
        System.out.println("DB_PASSWORD: " + dbPassword);
        

        try (Connection connection = DriverManager.getConnection(
                dbUrl, dbUser, dbPassword)) {
            
            // Vérifie si la connexion est valide (timeout 2s)
            connectionValid = connection.isValid(2);

            // Prépare la requête pour récupérer l'utilisateur avec user_id = 1
            // String sql = "SELECT * FROM Agence WHERE numAgence = ?;";
            // try (PreparedStatement statement = connection.prepareStatement(sql)) {
            //     statement.setInt(1, 1);

            //     // Exécution de la requête et affichage de l'email
            //     try (ResultSet resultSet = statement.executeQuery()) {
            //         while (resultSet.next()) {
            //             String email = resultSet.getString("email");
            //             System.out.println("Email de l'utilisateur : " + email);
            //         }
            //     }
            // }

            // print all tables in the database
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[] {"TABLE"});
            System.out.println("Tables in the database:");
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println(tableName);
            }
        }

        return connectionValid;
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Main app = new Main();
        try {
            boolean isConnected = app.printUserEmail();
            System.out.println("Connexion valide : " + isConnected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
