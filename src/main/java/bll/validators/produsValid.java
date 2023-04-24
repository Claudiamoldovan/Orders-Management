package bll.validators;

import model.Produs;

import java.util.regex.Pattern;

public class produsValid implements Validator<Produs> {
    private static final String EMAIL_PATTERN="^[A-Z ]*";
    public void validate(Produs prod) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN,Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(prod.getNume()).matches()) {
            throw new IllegalArgumentException("Nume produs invalid!");
            //se verifica daca numele produsului este valid
        }
    }
}
