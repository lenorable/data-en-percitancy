package nl.hu.dp.p4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOsql implements ReizigerDAO {
    private Connection conn;
    private AdresDAO aDao;
    private OVChipkaartDAO oDao;

    public ReizigerDAOsql(Connection conn) {
        this.conn = conn;
    }

    public void setAdresDAO(AdresDAO aDao) {
        this.aDao = aDao;
    }

    public void setOVChipDAO(OVChipkaartDAO oDao) {
        this.oDao = oDao;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO reiziger VALUES (?,?,?,?,?)");

        Date sqlDate = Date.valueOf(reiziger.getGeboortedaum());

        statement.setInt(1, reiziger.getId());
        statement.setString(2, reiziger.getVoorletters());
        statement.setString(3, reiziger.getTussenvoegsel());
        statement.setString(4, reiziger.getAchternaam());
        statement.setDate(5, sqlDate);

        statement.executeUpdate();
        statement.close();

        if (this.aDao != null) {
            this.aDao.save(reiziger.getAdres());
        }

        if (this.oDao != null) {
            for (OVChipkaart kaart : reiziger.getKaarten()) {
                this.oDao.save(kaart);
            }
        }

        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "UPDATE reiziger SET reiziger_id = ?, voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");

        Date sqlDate = Date.valueOf(reiziger.getGeboortedaum());

        statement.setInt(1, reiziger.getId());
        statement.setString(2, reiziger.getVoorletters());
        statement.setString(3, reiziger.getTussenvoegsel());
        statement.setString(4, reiziger.getAchternaam());
        statement.setDate(5, sqlDate);
        statement.setInt(6, reiziger.getId());

        statement.executeUpdate();
        statement.close();

        if (this.aDao != null) {
            this.aDao.update(reiziger.getAdres());
        }

        if (this.oDao != null) {
            for (OVChipkaart kaart : reiziger.getKaarten()) {
                this.oDao.update(kaart);
            }
        }

        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        if (this.aDao != null) {
            this.aDao.delete(reiziger.getAdres());
        }

        if (this.oDao != null) {
            for (OVChipkaart kaart : reiziger.getKaarten()) {
                this.oDao.delete(kaart);
            }
        }

        PreparedStatement statement = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");

        statement.setInt(1, reiziger.getId());

        statement.executeUpdate();
        statement.close();

        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?");

        statement.setInt(1, id);

        ResultSet awns = statement.executeQuery();

        Reiziger r1 = new Reiziger();

        if (awns.next()) {
            r1.setId(awns.getInt(1));
            r1.setVoorletters(awns.getString(2));
            r1.setTussenvoegsel(awns.getString(3));
            r1.setAchternaam(awns.getString(4));
            r1.setGeboortedaum(LocalDate.parse(awns.getDate(5).toString()));
        }

        // ik denk dat dit in de loop hier boven moet en dat het uberhaupt nu nog niks
        // terug geeft? dus ja, check dat en voeg dan het zelfde idee toe aan de p5 :)
        if (this.aDao != null) {
            Adres reizigerAdres = this.aDao.findByReiziger(r1);
            r1.setAdres(reizigerAdres);
        }

        if (this.oDao != null) {
            ArrayList<OVChipkaart> kaarten = oDao.findByReiziger(r1);
            for (OVChipkaart kaart : kaarten) {
                r1.addKaart(kaart);
            }
        }

        statement.close();
        awns.close();

        return r1;
    }

    @Override
    public List<Reiziger> findByGbdatum(LocalDate datum) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");

        statement.setDate(1, Date.valueOf(datum));

        ResultSet awns = statement.executeQuery();

        ArrayList<Reiziger> reizigers = new ArrayList<Reiziger>();

        while (awns.next()) {
            Reiziger r1 = new Reiziger();
            r1.setId(awns.getInt(1));
            r1.setVoorletters(awns.getString(2));
            r1.setTussenvoegsel(awns.getString(3));
            r1.setAchternaam(awns.getString(4));
            r1.setGeboortedaum(LocalDate.parse(awns.getDate(5).toString()));

            if (this.aDao != null) {
                Adres reizigerAdres = this.aDao.findByReiziger(r1);
                r1.setAdres(reizigerAdres);
            }

            if (this.oDao != null) {
                ArrayList<OVChipkaart> kaarten = oDao.findByReiziger(r1);
                for (OVChipkaart kaart : kaarten) {
                    r1.addKaart(kaart);
                }
            }

            reizigers.add(r1);
        }

        statement.close();
        awns.close();

        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM reiziger");

        ResultSet awns = statement.executeQuery();

        ArrayList<Reiziger> reizigers = new ArrayList<Reiziger>();

        while (awns.next()) {
            Reiziger r1 = new Reiziger();
            r1.setId(awns.getInt(1));
            r1.setVoorletters(awns.getString(2));
            r1.setTussenvoegsel(awns.getString(3));
            r1.setAchternaam(awns.getString(4));
            r1.setGeboortedaum(LocalDate.parse(awns.getDate(5).toString()));

            if (this.aDao != null) {
                Adres reizigerAdres = this.aDao.findByReiziger(r1);
                r1.setAdres(reizigerAdres);
            }

            if (this.oDao != null) {
                ArrayList<OVChipkaart> kaarten = oDao.findByReiziger(r1);
                for (OVChipkaart kaart : kaarten) {
                    r1.addKaart(kaart);
                }
            }

            reizigers.add(r1);
        }

        statement.close();
        awns.close();

        return reizigers;
    }

}
