package nl.hu.dp.p4;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart kaart) throws SQLException;
    public boolean update(OVChipkaart kaart) throws SQLException;
    public boolean delete(OVChipkaart kaart) throws SQLException;

    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
}
