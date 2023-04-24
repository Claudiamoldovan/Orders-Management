package model;

public class Client {
    private int id;
    private String nume;
    private String adresa;
    private String mail;

    public Client(String nume, String adresa,String mail){
        this.adresa=adresa;
        this.mail=mail;
        this.nume=nume;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
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

    public String toString(){
        return "Client  "+this.nume+" adresa "+this.adresa+" mail "+this.mail;
    }
}
