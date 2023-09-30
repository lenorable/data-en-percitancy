package nl.hu.dp.p4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOsql implements OVChipkaartDAO {
    private Connection conn;
    private ReizigerDAO rdao;

    public OVChipkaartDAOsql(Connection conn) {
        this.conn = conn;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    @Override
    public boolean save(ArrayList<OVChipkaart> kaarten) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO ov_chipkaart VALUES (?,?,?,?,?)");

        for (OVChipkaart kaart : kaarten) {
            Date sqlDate = Date.valueOf(kaart.getGeldigTot());

            statement.setInt(1, kaart.getKaartNummer());
            statement.setDate(2, sqlDate);
            statement.setInt(3, kaart.getKlasse());
            statement.setDouble(4, kaart.getSaldo());
            statement.setInt(5, kaart.getReiziger().getId());

            statement.executeUpdate();
        }

        statement.close();

        return true;
    }

    @Override
    public boolean update(ArrayList<OVChipkaart> kaarten) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?");

        for (OVChipkaart kaart : kaarten) {
            Date sqlDate = Date.valueOf(kaart.getGeldigTot());

            statement.setDate(1, sqlDate);
            statement.setInt(2, kaart.getKlasse());
            statement.setDouble(3, kaart.getSaldo());
            statement.setInt(4, kaart.getReiziger().getId());
            statement.setInt(5, kaart.getKaartNummer());

            statement.executeUpdate();
        }

        statement.close();

        return true;
    }

    @Override
    public boolean delete(ArrayList<OVChipkaart> kaarten) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");

        for (OVChipkaart kaart : kaarten) {
            statement.setInt(1, kaart.getKaartNummer());

            statement.executeUpdate();
        }

        statement.close();

        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?");

        statement.setInt(1, reiziger.getId());

        ResultSet awns = statement.executeQuery();

        ArrayList<OVChipkaart> kaarten = new ArrayList<OVChipkaart>();

        while (awns.next()) {
            OVChipkaart k1 = new OVChipkaart(reiziger);

            k1.setKaartNummer(awns.getInt(1));
            k1.setGeldigTot(LocalDate.parse(awns.getDate(2).toString()));
            k1.setKlasse(awns.getInt(3));
            k1.setSaldo(awns.getDouble(4));

            kaarten.add(k1);
        }

        statement.close();
        awns.close();

        return kaarten;
    }

}
