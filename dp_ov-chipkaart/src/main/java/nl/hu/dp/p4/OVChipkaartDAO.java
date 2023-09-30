package nl.hu.dp.p4;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(ArrayList<OVChipkaart> kaarten) throws SQLException;
    public boolean update(ArrayList<OVChipkaart> kaarten) throws SQLException;
    public boolean delete(ArrayList<OVChipkaart> kaarten) throws SQLException;

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
}
