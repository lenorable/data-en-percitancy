package nl.hu.dp.p4;

import java.time.LocalDate;

public class OVChipkaart {
    private int kaartNummer;
    private LocalDate geldigTot;
    private int klasse;
    private double saldo;

    private Reiziger reiziger;

    public OVChipkaart(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public LocalDate getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(LocalDate geldigTot) {
        this.geldigTot = geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public String toString(){
        return String.format("De ovchipkaart met nummer %d, die eindigt op: %s is van %s", this.kaartNummer, this.geldigTot.toString(), this.reiziger);
    }
}
