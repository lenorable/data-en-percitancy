package nl.hu.dp.p3;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ReizigerDAO {
    public void setAdresDAO(AdresDAO aDao);
    public boolean save(Reiziger reiziger) throws SQLException;
    public boolean update(Reiziger reiziger) throws SQLException;
    public boolean delete(Reiziger reiziger) throws SQLException;
    public Reiziger findById(int id) throws SQLException;
    public List<Reiziger> findByGbdatum(LocalDate datum) throws SQLException;
    public List<Reiziger> findAll() throws SQLException;
}
