package swen90006.aqms;

public class AccessRightException extends Exception {
    public AccessRightException(String username) {
        super("User " + username + " doesn't have access rights.");
    }
}
