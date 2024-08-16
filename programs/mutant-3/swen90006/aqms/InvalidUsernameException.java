package swen90006.aqms;

public class InvalidUsernameException extends Exception {
    public InvalidUsernameException(String username) {
        super("Username " + username + " does not comply with the requirements\n" +
                "\t- must contains at least " +
                AQMS.MINIMUM_USERNAME_LENGTH + " characters" +
                " and contain only letters (a-z, A-Z)");
    }
}
