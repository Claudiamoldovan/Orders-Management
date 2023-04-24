package model;

public class Produs {
    private int id;
    private String nume;
    private int cantitate;
    private float pret;

    public Produs(String nume,int cantitate,float pret){
        this.cantitate=cantitate;
        this.pret=pret;
        this.nume=nume;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
