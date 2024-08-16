package swen90006.aqms;

public class DuplicateUserException extends Exception {
    public DuplicateUserException(String username) {
        super("Username already exists: " + username);
    }
}
