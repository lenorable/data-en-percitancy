package nl.hu.dp.p3;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int reiziger_id;

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

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String toString() {
        return String.format("Reiziger met id %o woont op postcode %c met huisnummer %c. dit is in de straat %c in %c. dit adres is adres %c", reiziger_id, postcode, huisnummer, straat, woonplaats, id);
    }

}
