package swen90006.aqms;

public class InvalidAssignedRoleException extends Exception {
    public InvalidAssignedRoleException(String exisitingRole, String newRole) {
        super("Can not change role from: " + exisitingRole + " to " + newRole);
    }
}
