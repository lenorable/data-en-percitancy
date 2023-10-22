package nl.hu.dp.p5;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;
    public boolean update(Product product) throws SQLException;
    public boolean delete(Product product) throws SQLException;

    public ArrayList<Product> findByOVChipkaart(OVChipkaart OVChipkaart) throws SQLException;
    public ArrayList<Product> findAll() throws SQLException;
}
