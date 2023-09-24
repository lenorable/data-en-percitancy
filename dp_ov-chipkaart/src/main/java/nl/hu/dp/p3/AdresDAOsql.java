package nl.hu.dp.p3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOsql implements AdresDAO{
    private Connection conn;
    private ReizigerDAO rDao;

    public AdresDAOsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO adres VALUES (?,?,?,?,?,?)");

        statement.setInt(1, adres.getId());
        statement.setString(2, adres.getPostcode());
        statement.setString(3, adres.getHuisnummer());
        statement.setString(4, adres.getStraat());
        statement.setString(5, adres.getWoonplaats());
        statement.setInt(6, adres.getReiziger_id());

        statement.executeUpdate();
        statement.close();

        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE id = ?");

        statement.setString(1, adres.getPostcode());
        statement.setString(2, adres.getHuisnummer());
        statement.setString(3, adres.getStraat());
        statement.setString(4, adres.getWoonplaats());
        statement.setInt(5, adres.getReiziger_id());
        statement.setInt(6, adres.getId());

        statement.executeUpdate();
        statement.close();

        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM reiziger WHERE id = ?");

        statement.setInt(1, adres.getId());

        statement.executeUpdate();
        statement.close();

        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM adres WHERE reiziger_id = ?");

        statement.setInt(1, reiziger.getId());

        ResultSet awns = statement.executeQuery();

        Adres a1 = new Adres();

        if (awns.next()) {
            a1.setPostcode(awns.getString(2));
            a1.setHuisnummer(awns.getString(3));
            a1.setStraat(awns.getString(4));
            a1.setWoonplaats(awns.getString(5));
            a1.setId(awns.getInt(6));
        }

        statement.close();
        awns.close();

        return a1;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM adres");

        ResultSet awns = statement.executeQuery();

        ArrayList<Adres> adressen = new ArrayList<Adres>();

        while (awns.next()){
            Adres a1 = new Adres();
            a1.setPostcode(awns.getString(2));
            a1.setHuisnummer(awns.getString(3));
            a1.setStraat(awns.getString(4));
            a1.setWoonplaats(awns.getString(5));
            a1.setId(awns.getInt(6));

            adressen.add(a1);
        }

        statement.close();
        awns.close();

        return adressen;
    }
    
}
