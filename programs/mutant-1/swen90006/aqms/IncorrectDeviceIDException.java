package swen90006.aqms;

public class IncorrectDeviceIDException extends Exception {
    public IncorrectDeviceIDException(String username, String deviceID) {
        super("Incorrect device ID: " + deviceID + " for user " + username);
    }
}
