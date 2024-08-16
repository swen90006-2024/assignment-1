package swen90006.aqms;

public class NoSuchUserException extends Exception {
    public NoSuchUserException(String username) {
        super("Username does not exist: " + username);
    }
}
