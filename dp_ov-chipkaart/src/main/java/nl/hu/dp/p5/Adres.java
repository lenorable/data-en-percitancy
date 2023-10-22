package nl.hu.dp.p5;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;

    private Reiziger reiziger;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    // voor het ophalen van id? kan anders...
    public int getReiziger_id() {
        return this.reiziger.getId();
    }

    // voor de relatie tussen Adres en Reiziger
    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public String toString() {
        return String.format("Reiziger (met id %o )woont op postcode %s met huisnummer %s. dit is in de straat %s in %s. dit adres is adres %s", this.getReiziger_id(), postcode, huisnummer, straat, woonplaats, id);
    }

}
