package nl.hu.dp.p5;

import java.util.ArrayList;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    private ArrayList<OVChipkaart> OVChipkaarten;

    public Product(){
        this.OVChipkaarten = new ArrayList<OVChipkaart>();
    }

    public int getProduct_nummer() {
        return product_nummer;
    }
    
    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }
    
    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public ArrayList<OVChipkaart> getOVChipkaarten() {
        return OVChipkaarten;
    }

    public void setOVChipkaarten(ArrayList<OVChipkaart> oVChipkaarten) {
        for (OVChipkaart kaart : oVChipkaarten) {
            this.OVChipkaarten.add(kaart);
        }
    }

    public void addOVChipkaart(OVChipkaart oVChipkaart) {
        this.OVChipkaarten.add(oVChipkaart);
    }

    public String toString(){
        return "Product nummer " + this.product_nummer + " heeft naam: " + this.naam;
    }
}
