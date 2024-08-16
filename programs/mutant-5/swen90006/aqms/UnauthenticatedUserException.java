package swen90006.aqms;

public class UnauthenticatedUserException extends Exception {
    public UnauthenticatedUserException(String username) {
        super("User " + username + " is not authenticated");
    }
}
