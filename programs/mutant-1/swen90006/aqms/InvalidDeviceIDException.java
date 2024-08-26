package swen90006.aqms;

public class InvalidDeviceIDException extends Exception {
    public InvalidDeviceIDException(String deviceID) {
        super("DeviceID " + deviceID + " does not comply with the requirements\n" +
                "\t- must contains exactly " +
                AQMS.MINIMUM_DEVICE_ID_LENGTH + " characters" +
                " and contain only digits (0-9)");
    }

}
