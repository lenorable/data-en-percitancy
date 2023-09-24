package nl.hu.dp.p3;

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

    public ReizigerDAOsql(Connection conn) {
        this.conn = conn;
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

        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
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

        while (awns.next()){
            Reiziger r1 = new Reiziger();
            r1.setId(awns.getInt(1));
            r1.setVoorletters(awns.getString(2));
            r1.setTussenvoegsel(awns.getString(3));
            r1.setAchternaam(awns.getString(4));
            r1.setGeboortedaum(LocalDate.parse(awns.getDate(5).toString()));

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

        while (awns.next()){
            Reiziger r1 = new Reiziger();
            r1.setId(awns.getInt(1));
            r1.setVoorletters(awns.getString(2));
            r1.setTussenvoegsel(awns.getString(3));
            r1.setAchternaam(awns.getString(4));
            r1.setGeboortedaum(LocalDate.parse(awns.getDate(5).toString()));

            reizigers.add(r1);
        }

        statement.close();
        awns.close();

        return reizigers;
    }

}
