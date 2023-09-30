package nl.hu.dp.p4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();

        // testReizigerDAO(new ReizigerDAOsql(connection));

        // testAdresDAO(new ReizigerDAOsql(connection), new AdresDAOsql(connection));

        testOVChipDAO(new ReizigerDAOsql(connection), new AdresDAOsql(connection), new OVChipkaartDAOsql(connection));

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

    private static void testAdresDAO(ReizigerDAO rdao, AdresDAO aDao) throws SQLException {
        rdao.setAdresDAO(aDao);
        aDao.setReizigerDAO(rdao);

        List<Reiziger> reizigers = rdao.findAll();

        for (Reiziger reiziger : reizigers) {
            System.out.println(reiziger + " " + aDao.findByReiziger(reiziger));
        }

        Adres aNew = new Adres();
        aNew.setId(6);
        aNew.setPostcode("1234AB");
        aNew.setHuisnummer("101");
        aNew.setStraat("abc straat");
        aNew.setWoonplaats("Groningen");

        Reiziger rNew = new Reiziger(6, "A", "van den", "Berg", LocalDate.parse("2000-02-07"));

        aNew.setReiziger(rNew);
        rNew.setAdres(aNew);

        rdao.save(rNew);

        System.out.println(aDao.findByReiziger(rdao.findById(6)));

        aNew.setPostcode("5678CD");
        rNew.setTussenvoegsel("test");
        rdao.update(rNew);

        System.out.println(aDao.findByReiziger(rdao.findById(6)));

        rdao.delete(rNew);
    }

    private static void testOVChipDAO(ReizigerDAO rdao, AdresDAO aDao, OVChipkaartDAO oDao) throws SQLException {
        rdao.setAdresDAO(aDao);
        aDao.setReizigerDAO(rdao);
        rdao.setOVChipDAO(oDao);

        Adres aNew = new Adres();
        aNew.setId(6);
        aNew.setPostcode("1234AB");
        aNew.setHuisnummer("101");
        aNew.setStraat("abc straat");
        aNew.setWoonplaats("Groningen");

        Reiziger rNew = new Reiziger(6, "A", "van den", "Berg", LocalDate.parse("2000-02-07"));

        OVChipkaart o1 = new OVChipkaart(rNew);
        o1.setKaartNummer(12345);
        o1.setGeldigTot(LocalDate.now().plusYears(3));
        o1.setKlasse(2);
        o1.setSaldo(0.00);
        rNew.addKaart(o1);

        OVChipkaart o2 = new OVChipkaart(rNew);
        o2.setKaartNummer(12346);
        o2.setGeldigTot(LocalDate.now().plusWeeks(13));
        o2.setKlasse(1);
        o2.setSaldo(100.00);
        rNew.addKaart(o2);


        aNew.setReiziger(rNew);
        rNew.setAdres(aNew);

        // voor de tests zelf:
        rdao.save(rNew);

        for (OVChipkaart kaart : oDao.findByReiziger(rNew)) {
            System.out.println(kaart);
        };

        rdao.delete(rNew);
    }

}
