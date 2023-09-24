package nl.hu.dp.p3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException{
        getConnection();

        testReizigerDAO(new ReizigerDAOsql(connection));

        closeConnection();
    }

    private static Connection getConnection() throws SQLException {
        String username = "postgres";
        String password = "k6LfYEIszD1cOP29qTvx";

        if (connection == null) {

            String URL = "jdbc:postgresql://localhost:5432/ovchip";

            connection = DriverManager.getConnection(URL, username, password);
        }

        return connection;
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", LocalDate.parse(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        sietske.setTussenvoegsel("van");
        rdao.update(sietske);

        System.out.println(rdao.findByGbdatum(LocalDate.parse("1981-03-14")) + "\n");

        rdao.delete(sietske);

        System.out.println(rdao.findAll());
        System.out.println("\ngebruiker met id 1: " + rdao.findById(1));

        System.out.println("\nalles lijkt goed te werken :)");
    }

}
