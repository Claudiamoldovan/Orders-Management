package bll.validators;

import model.ComandaProdus;

public class cantitateValida implements Validator<ComandaProdus> {

    public void validate(ComandaProdus cmd) {
        //verifica daca cantitatea este valida
        if (cmd.getCantitate() <= 0) {
            throw new IllegalArgumentException("Cantitate mai mica sau egal cu 0");
        }
    }
}
