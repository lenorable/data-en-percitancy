package nl.hu.dp.p5;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ReizigerDAO {
    public void setAdresDAO(AdresDAO aDao);
    public void setOVChipDAO(OVChipkaartDAO oDao);
    public boolean save(Reiziger reiziger) throws SQLException;
    public boolean update(Reiziger reiziger) throws SQLException;
    public boolean delete(Reiziger reiziger) throws SQLException;
    public Reiziger findById(int id) throws SQLException;
    public ArrayList<Reiziger> findByGbdatum(LocalDate datum) throws SQLException;
    public ArrayList<Reiziger> findAll() throws SQLException;
}
