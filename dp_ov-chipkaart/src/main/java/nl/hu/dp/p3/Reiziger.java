package nl.hu.dp.p3;

import java.time.LocalDate;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private LocalDate geboortedaum;

    // private 

    public Reiziger(){
        
    };

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedaum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedaum = geboortedaum;
    } 

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public LocalDate getGeboortedaum() {
        return geboortedaum;
    }

    public void setGeboortedaum(LocalDate geboortedaum) {
        this.geboortedaum = geboortedaum;
    }

    public String getNaam(){
        return voorletters + ". " + tussenvoegsel + " " + achternaam;
    }

    public String toString(){
        return "Klant " + getNaam() + " met id: " + id + " , is geboren op: " + geboortedaum.toString();
    }
}
