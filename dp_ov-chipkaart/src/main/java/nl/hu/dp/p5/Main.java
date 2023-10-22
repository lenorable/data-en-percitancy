package nl.hu.dp.p5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();

        ReizigerDAO rDao = new ReizigerDAOsql(connection);
        AdresDAO aDao = new AdresDAOsql(connection);
        OVChipkaartDAO oDao = new OVChipkaartDAOsql(connection);
        ProductDAO pDao = new ProductDAOPsql(connection);

        testProduct(rDao, aDao, oDao, pDao);

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

    private static void testProduct(ReizigerDAO rDao, AdresDAO aDao, OVChipkaartDAO oDao, ProductDAO pDao)
            throws SQLException {
        rDao.setAdresDAO(aDao);
        rDao.setOVChipDAO(oDao);

        aDao.setReizigerDAO(rDao);

        oDao.setPdao(pDao);

        // informatie ophalen
        Reiziger readReiziger1 = rDao.findById(2);
        System.out.println(readReiziger1);
        System.out.println(readReiziger1.getAdres());

        for (OVChipkaart kaart : readReiziger1.getKaarten()) {

            System.out.println(kaart);

            for (Product product : kaart.getProducten()) {
                System.out.println(product);
            }
        }

        System.out.println("\n");

        // informatie aan maken
        Reiziger nr1 = new Reiziger(22, "Lenor", null, "Hofschreuder", (LocalDate) (LocalDate.now().minusYears(18)));

        Adres na1 = new Adres();
        na1.setId(12);
        na1.setPostcode("3543DN");
        na1.setHuisnummer("105");
        na1.setStraat("StraatNaam");
        na1.setWoonplaats("Utrecht");

        OVChipkaart nk1 = new OVChipkaart(readReiziger1);
        nk1.setGeldigTot(LocalDate.now().plusYears(2));
        nk1.setKaartNummer(404);
        nk1.setKlasse(2);
        nk1.setSaldo(20.00);
        nk1.setReiziger(nr1);

        OVChipkaart nk2 = new OVChipkaart(readReiziger1);
        nk2.setGeldigTot(LocalDate.now().plusYears(1));
        nk2.setKaartNummer(405);
        nk2.setKlasse(1);
        nk2.setSaldo(200.00);
        nk2.setReiziger(nr1);

        Product np1 = new Product();
        np1.setProduct_nummer(101);
        np1.setNaam("2e klas weekvrij student");
        np1.setBeschrijving("studenten gratis ov door de weeks");
        np1.setPrijs(100.0);

        Product np2 = new Product();
        np2.setProduct_nummer(102);
        np2.setNaam("2e klas weekvrij student");
        np2.setBeschrijving("studenten gratis ov door de weeks");
        np2.setPrijs(100.0);

        Product np3 = new Product();
        np3.setProduct_nummer(103);
        np3.setNaam("2e klas weekendvrij student");
        np3.setBeschrijving("studenten gratis ov in de weekenden");
        np3.setPrijs(100.0);

        nk1.addProduct(np1);
        np1.addOVChipkaart(nk1);
        nk1.addProduct(np2);
        np2.addOVChipkaart(nk1);

        nk2.addProduct(np3);
        np3.addOVChipkaart(nk2);

        na1.setReiziger(nr1);

        nr1.setAdres(na1);

        nr1.addKaart(nk1);
        nr1.addKaart(nk2);

        rDao.save(nr1);

        // informatie weer ophalen
        Reiziger readReiziger2 = rDao.findById(22);
        System.out.println(readReiziger2);
        System.out.println(readReiziger2.getAdres());

        for (OVChipkaart kaart : readReiziger2.getKaarten()) {

            System.out.println(kaart);

            for (Product product : kaart.getProducten()) {
                System.out.println(product);
            }
        }

        System.out.println("\n");

        // delete weer uit de database
        rDao.delete(nr1);

        // informatie weer weer ophalen
        for (Reiziger reiziger : rDao.findAll()) {
            System.out.println(reiziger);
            System.out.println(reiziger.getAdres());

            for (OVChipkaart kaart : reiziger.getKaarten()) {

                System.out.println(kaart);

                for (Product product : kaart.getProducten()) {
                    System.out.println(product);
                }
            }
        }

        System.out.println("\n");
    }

}
