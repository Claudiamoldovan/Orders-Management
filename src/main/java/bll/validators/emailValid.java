package bll.validators;

import model.Client;

import java.util.regex.Pattern;

public class emailValid implements Validator<Client>{
    private static final String EMAIL_PATTERN="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    //se verifica daca adresa de mail este valida
    public void validate(Client client) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN,Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(client.getMail()).matches()) {
            throw new IllegalArgumentException("Email invalid!");
        }
    }


}
