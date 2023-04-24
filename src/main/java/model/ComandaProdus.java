package model;

public class ComandaProdus {

    private int idCmd;
    private int idProdus;
    private int cantitate;

    public ComandaProdus(int idCmd,int idProdus,int cantitate){
        this.idCmd=idCmd;
        this.idProdus=idProdus;
        this.cantitate=cantitate;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getidCmd() {
        return idCmd;
    }

    public void setidCmd(int idCmd) {
        this.idCmd = idCmd;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public String toString(){
        return "idCmd " +this.idCmd+" idProdus "+idProdus+" cantitate "+this.cantitate;
    }
}
