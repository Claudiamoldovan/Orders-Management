package bll.validators;

import model.Produs;

public class numarValid implements Validator<Produs> {

    public void validate(Produs prod) {

        if (prod.getCantitate() < 0) {
            throw new IllegalArgumentException("Cantitate mai mica decat 0");
            //se verifica daca cantitatea este valida
        }

        if (prod.getPret() < 0) {
            throw new IllegalArgumentException("Pret mai mic decat 0");
        }
    }

}


