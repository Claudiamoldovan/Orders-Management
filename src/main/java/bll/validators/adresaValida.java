package bll.validators;

import model.Client;

import java.util.regex.Pattern;

public class adresaValida implements Validator<Client>{
    //se verifica daca adresa este valida
    private static final String EMAIL_PATTERN="^[A-Z ]*";
    public void validate(Client client) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN,Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(client.getAdresa()).matches()) {
            throw new IllegalArgumentException("Adresa invalida!");
        }
        if(!pattern.matcher(client.getNume()).matches()){
            throw new IllegalArgumentException("Nume invalid!");
        }
    }
}
