package nl.hu.dp.p5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOPsql implements ProductDAO {
    private Connection conn;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO product VALUES (?,?,?,?)");

        statement.setInt(1, product.getProduct_nummer());
        statement.setString(2, product.getNaam());
        statement.setString(3, product.getBeschrijving());
        statement.setDouble(4, product.getPrijs());

        statement.executeUpdate();
        statement.close();

        for (OVChipkaart kaart : product.getOVChipkaarten()) {
            statement = conn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES (?,?, null, null)");

            statement.setInt(1, kaart.getKaartNummer());
            statement.setInt(2, product.getProduct_nummer());
            // statement.setString(3, product.getBeschrijving());
            // statement.setDouble(4, product.getPrijs());

            statement.executeUpdate();
            statement.close();
        }

        return true;
    }

    @Override
    public boolean update(Product product) throws SQLException {

        delete(product);
        save(product);

        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");

        statement.setInt(1, product.getProduct_nummer());

        statement.executeUpdate();
        statement.close();

        statement = conn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");

        statement.setInt(1, product.getProduct_nummer());

        statement.executeUpdate();
        statement.close();

        return true;
    }

    @Override
    public ArrayList<Product> findByOVChipkaart(OVChipkaart OVChipkaart) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "select * from product join ov_chipkaart_product ON ov_chipkaart_product.product_nummer = product.product_nummer where ov_chipkaart_product.kaart_nummer = ?");

        statement.setInt(1, OVChipkaart.getKaartNummer());

        ResultSet awns = statement.executeQuery();

        ArrayList<Product> producten = new ArrayList<Product>();

        while (awns.next()) {
            Product product = new Product();

            product.setProduct_nummer(awns.getInt(1));
            product.setNaam(awns.getString(2));
            product.setBeschrijving(awns.getString(3));
            product.setPrijs(awns.getDouble(4));

            int pos = awns.getInt(6);

            producten.add(product);
        }

        // statement.executeUpdate();
        statement.close();

        return producten;
    }

    @Override
    public ArrayList<Product> findAll() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM product;");

        ResultSet awns = statement.executeQuery();

        ArrayList<Product> producten = new ArrayList<Product>();

        while (awns.next()) {
            Product product = new Product();

            product.setProduct_nummer(awns.getInt(1));
            product.setNaam(awns.getString(2));
            product.setBeschrijving(awns.getString(3));
            product.setPrijs(awns.getDouble(4));

            int pos = awns.getInt(6);

            producten.add(pos, product);
        }

        statement.close();
        awns.close();

        return producten;
    }
}
