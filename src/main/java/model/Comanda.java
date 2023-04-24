package model;

public class Comanda {
    private int idCmd;
    private int idClient;
    private String numeClient;
    private float suma;

    public Comanda(int idClient,String numeClient,float suma){
        this.idClient=idClient;
        this.suma=suma;
        this.numeClient=numeClient;
    }

    public int getIdCmd() {
        return idCmd;
    }

    public void setId(int idCmd) {
        this.idCmd = idCmd;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public float getSuma() {
        return suma;
    }

    public void setSuma(float suma) {
        this.suma = suma;
    }
}
