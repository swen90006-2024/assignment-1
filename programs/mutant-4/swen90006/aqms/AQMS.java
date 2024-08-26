package swen90006.aqms;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * The `AQMS` class implements a system that manages user registration,
 * login, role assignment, and data retrieval. It supports password and device ID authentication,
 * and enforces specific validation rules for usernames, passwords, and device IDs. The class also
 * handles authentication status and provides role-based access control for user data.
 */
public class AQMS {
    /**
     * The minimum length of a username
     */
    public final static int MINIMUM_USERNAME_LENGTH = 4;

    /**
     * The minimum length of a password
     */
    public final static int MINIMUM_PASSWORD_LENGTH = 8;

    /**
     * The minimum length of a device ID
     */
    public final static int MINIMUM_DEVICE_ID_LENGTH = 4;

    /**
     * The authentication status of a user: not authenticated,
     * or authenticated with a password and device ID.
     */
    private enum AuthenticationStatus {
        AUTHENTICATED,
        NOT_AUTHENTICATED
    }

    /**
     * Enum representing the roles that a user can have in the system.
     */
    public enum Role {
        ADMIN,
        USER
    }

    /**
     * The (passwords, deviceIDs) for all users
     */
    private Map<String, Pair<String, String>> users;

    /**
     * roles stored for users
     */
    private Map<String, Role> roles;

    /**
     * The data stored for each user: a list of lists
     */
    private Map<String, List<List<Integer>>> data;

    /**
     * The authentication status of each user
     */
    private Map<String, AuthenticationStatus> authenticationStatus;

    /**
     * Constructs a new air quality management server with no users
     */
    public AQMS() {
        users = new HashMap<String, Pair<String, String>>();
        data = new HashMap<String, List<List<Integer>>>();
        roles = new HashMap<String, Role>();
        authenticationStatus = new HashMap<String, AuthenticationStatus>();
    }

    /**
     * Registers a new user with an authentication status of NOT_AUTHENTICATED,
     * role as default USER, and an empty default resource (empty data record).
     * The role is not taken as an input when registering.
     *
     * <p>The following requirements must be met:</p>
     *
     * <h3>Requirements:</h3>
     *
     * <ul>
     *   <li><strong>Username:</strong>
     *     <ul>
     *       <li>Must be at least four characters long.</li>
     *       <li>Must contain only lower- and upper-case letters.</li>
     *     </ul>
     *   </li>
     *   <li><strong>Password:</strong>
     *     <ul>
     *       <li>Must be at least eight characters long.</li>
     *       <li>Must contain at least one letter (a-z, A-Z).</li>
     *       <li>Must contain at least one digit (0-9).</li>
     *       <li>Must contain at least one special character (anything other than a-z, A-Z, or 0-9).</li>
     *     </ul>
     *   </li>
     *   <li><strong>Device ID (for two-factor authentication):</strong>
     *     <ul>
     *       <li>Must be exactly four characters long.</li>
     *       <li>Must contain only numeric digits (0-9).</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * <h3>Parameters:</h3>
     * <ul>
     *   <li><strong>username:</strong> The username for the user to be added.</li>
     *   <li><strong>password:</strong> The password for the user.</li>
     *   <li><strong>deviceID:</strong> The physical device ID used for two-factor authentication.</li>
     * </ul>
     *
     * <h3>Exceptions (thrown in order):</h3>
     * <ul>
     *   <li><strong>DuplicateUserException:</strong> Thrown if the username is already registered.</li>
     *   <li><strong>InvalidUsernameException:</strong> Thrown if the username does not meet the specified requirements.</li>
     *   <li><strong>InvalidPasswordException:</strong> Thrown if the password does not meet the specified requirements.</li>
     *   <li><strong>InvalidDeviceIDException:</strong> Thrown if the device ID does not meet the specified requirements.</li>
     * </ul>
     *
     * <p>Exceptions are thrown in the following order:</p>
     * <ul>
     *   <li>DuplicateUserException is thrown first (i.e., existing usernames are all valid).</li>
     *   <li>If the username is invalid, then the password and device ID are not checked.</li>
     *   <li>If the username is valid, then the password is checked.</li>
     *   <li>If both username and password are valid, then the device ID is checked.</li>
     * </ul>
     *
     * <h3>Assumptions:</h3>
     * <ul>
     *   <li>username, password, and deviceID are non-null.</li>
     * </ul>
     *
     * @param username The username for the user to be added.
     * @param password The password for the user.
     * @param deviceID The physical device ID used for two-factor authentication.
     * @throws DuplicateUserException   Thrown if the username is already registered.
     * @throws InvalidUsernameException Thrown if the username does not meet the specified requirements.
     * @throws InvalidPasswordException Thrown if the password does not meet the specified requirements.
     * @throws InvalidDeviceIDException Thrown if the device ID does not meet the specified requirements.
     */
    public void register(String username, String password, String deviceID)
            throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException,
            InvalidDeviceIDException {

        // Check if this user exists
        if (users.containsKey(username)) {
            throw new DuplicateUserException(username);
        } else if (!isValidUsername(username)) {
            throw new InvalidUsernameException(username);
        } else {

            boolean letter = false;
            boolean digit = false;
            boolean special = false;

            if (password.length() < 8) {
                throw new InvalidPasswordException(password);
            } else {
                for (char c : password.toCharArray()) {
                    if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
                        letter = true;
                    } else if ('0' <= c && c <= '9') {
                        digit = true;
                    } else {
                        special = true;
                    }
                }
                if (!(letter && digit && special)) {
                    throw new InvalidPasswordException(password);
                } else if (!isValidDeviceID(deviceID)) {
                    throw new InvalidDeviceIDException(deviceID);
                } else {
                    users.put(username, Pair.of(password, deviceID));
                    data.put(username, new ArrayList<List<Integer>>());
                    authenticationStatus.put(username, AuthenticationStatus.NOT_AUTHENTICATED);
                    roles.put(username, Role.USER);
                }
            }
        }
    }

    /**
     * Logs a user in using their username, password, and device ID, and returns their authentication status.
     * Please note that for login, role is not required as an input.
     *
     * <h3>Parameters:</h3>
     * <ul>
     *   <li><strong>username:</strong> The username.</li>
     *   <li><strong>password:</strong> The password.</li>
     *   <li><strong>deviceID:</strong> The device ID.</li>
     * </ul>
     *
     * <h3>Returns:</h3>
     * <ul>
     *   <li><strong>NOT_AUTHENTICATED</strong> if any of the following conditions are true:
     *     <ul>
     *       <li>The password is incorrect (doesn't match with the registered username).</li>
     *       <li>The device ID is incorrect (doesn't match with the registered username).</li>
     *     </ul>
     *     <p><em>Note:</em> This method does not validate whether the username, password, or device ID
     *     are following the requirements in the register method; it assumes that they are already valid.</p>
     *   </li>
     *   <li><strong>AUTHENTICATED</strong> if and only if all of the following conditions are true:
     *     <ul>
     *       <li>The username exists.</li>
     *       <li>The password is correct.</li>
     *       <li>The device ID is correct.</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * <h3>Assumptions:</h3>
     * <ul>
     *   <li>username, password, and deviceID are non-null.</li>
     *   <li>username, password, and deviceID are all valid.</li>
     * </ul>
     *
     * <h3>Exceptions (thrown in order):</h3>
     * <ul>
     *   <li><strong>NoSuchUserException:</strong> Thrown if the username is not registered.</li>
     *   <li><strong>IncorrectPasswordException:</strong> Thrown if the password doesn't match the username.</li>
     *   <li><strong>IncorrectDeviceIDException:</strong> Thrown if the device ID doesn't match the username.</li>
     * </ul>
     *
     * <p>Exceptions are thrown in the following order:</p>
     * <ul>
     *   <li><strong>NoSuchUserException</strong> is thrown first.</li>
     *   <li>If the username is present but the password is incorrect,
     *       <strong>IncorrectPasswordException</strong> is thrown, and the device ID is not checked.</li>
     *   <li>Lastly, if both the username and password are correct, the device ID is checked,
     *       and <strong>IncorrectDeviceIDException</strong> is thrown if the device ID is incorrect.</li>
     * </ul>
     *
     * @param username The username.
     * @param password The password.
     * @param deviceID The device ID.
     * @return The user's authentication status.
     * @throws NoSuchUserException Thrown if the username is not registered.
     * @throws IncorrectPasswordException Thrown if the password doesn't match the username.
     * @throws IncorrectDeviceIDException Thrown if the device ID doesn't match the username.
     */
    public AuthenticationStatus login(String username, String password, String deviceID)
            throws NoSuchUserException, IncorrectPasswordException, IncorrectDeviceIDException {
        if (checkUsernamePassword(username, password, deviceID)) {
            authenticationStatus.put(username, AuthenticationStatus.AUTHENTICATED);
        }
        return authenticationStatus.get(username);
    }

    /**
     * Assigns the given role to an existing user. Assign the given role {ADMIN, USER} to an existing user. 
     * ADMIN is considered an upgraded role from USER. 
     * Once assigned, our system registers the role for the given username. 
     * 
     * <p>This method is intended for the IT team to assign roles to existing users. It does not require
     * the deviceID or password. If the username does not exist, a NoSuchUserException is thrown.
     * The the new role is a downgrade to the original role, an InvalidAssignedRoleExcetion is thrown. 
     * </p>
     *
     * @param username the username to whom the role is to be assigned
     * @param role     the role to assign (must be ADMIN or USER)
     * @throws NoSuchUserException if the username is not registered
     * @throws InvalidAssignedRoleException if the new role is a downgrade. (e.g., from ADMIN to USER)
     */
    public void assignRole(String username, Role role) throws NoSuchUserException, InvalidAssignedRoleException {
        // Check if the user is registered
        if (!users.containsKey(username)) {
            throw new NoSuchUserException(username);
        }
        // check if we are downgrading the role
        if (roles.get(username) == Role.ADMIN && role == Role.USER) {
            throw new InvalidAssignedRoleException(roles.get(username).toString(), role.toString());
        }
        else{
            // Assign the role to the user
            roles.put(username, role);
        }
    }

    /**
     * Add a new record data to an existing user.
     * The record is added to the end of the list of records.
     * <p>
     * This method is intended for the IT team to add data records to existing users. It does not require
     * the deviceID or password. It also does not check if the role of the user.
     *
     * @param username the username
     * @param record   a list of integers, the record to be added
     *                 <p>
     *                 Assumption: username and record are non-null
     * @throws NoSuchUserException if the user does not have an account
     */
    public void addData(String username, List<Integer> record) throws NoSuchUserException {
        // Check if the user is registered
        if (!users.containsKey(username)) {
            throw new NoSuchUserException(username);
        }

        // Add the record to the user's data
        data.get(username).add(record);
    }

    /**
     * Retrieves a record for a user if the user is authenticated and assigned the role of ADMIN.
     *
     * <p>This method checks the user's registration, authentication status, and assigned role before
     * allowing access to the specified data record. The record at the given index is returned unchanged.
     *
     * @param username the username whose data record is to be retrieved
     * @param index    the index of the data record to be retrieved
     * @return the data record at the specified index
     * @throws NoSuchUserException          if the username is not registered
     * @throws UnauthenticatedUserException if the username is not authenticated
     * @throws AccessRightException         if the username is authenticated but does not have the ADMIN role
     * @throws IndexOutOfBoundsException    if the index is out of bounds for the user's data
     */
    public List<Integer> getData(String username, int index)
            throws NoSuchUserException, UnauthenticatedUserException, AccessRightException,
            IndexOutOfBoundsException {

        // Check if the user is registered
        if (!users.containsKey(username)) {
            throw new NoSuchUserException(username);
        } else if (authenticationStatus.get(username) == AuthenticationStatus.NOT_AUTHENTICATED) {
            throw new UnauthenticatedUserException(username);
        } else if (!(roles.get(username) == Role.ADMIN)) {
            throw new AccessRightException(username);
        } else {
            return data.get(username).get(index);
        }
    }


    /**
     * Validates whether the given username meets the specified criteria.
     *
     * <p>The username must satisfy the following conditions:
     * <ul>
     *   <li>It must be at least four characters long.</li>
     *   <li>It must contain only lower-case and upper-case letters.</li>
     * </ul>
     *
     * @param username the username to be validated
     * @return {@code true} if the username is valid according to the criteria; {@code false} otherwise
     */
    private boolean isValidUsername(String username) {
        // Check if the username is at least 4 characters long
        if (username.length() < 4) {
            return false;
        }

        // Check if the username contains only lower- and upper-case letters
        for (char c : username.toCharArray()) {
            if (!('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z')) {
                return false;
            }
        }

        // If both checks pass, the username is valid
        return true;
    }


    /**
     * Validates whether the given Device ID meets the specified criteria.
     *
     * <p>The Device ID must satisfy the following conditions:
     * <ul>
     *   <li>It must be exactly four characters long.</li>
     *   <li>It must contain only numeric digits (0-9).</li>
     * </ul>
     *
     * @param deviceID the Device ID to be validated
     * @return {@code true} if the Device ID is valid according to the criteria; {@code false} otherwise
     */
    private boolean isValidDeviceID(String deviceID) {
        // Check if the Device ID is exactly 4 characters long
        if (deviceID.length() != 4) {
            return false;
        }

        // Check if the Device ID contains only numeric digits
        for (char c : deviceID.toCharArray()) {
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }

        // If both checks pass, the Device ID is valid
        return true;
    }

    /**
     * Validates the provided username, password, and device ID against stored credentials.
     *
     * <p>This method performs the following checks:
     * <ul>
     *   <li>Ensures the user exists in the system.</li>
     *   <li>Verifies that the provided password matches the stored password for the given username.</li>
     *   <li>Verifies that the provided device ID matches the stored device ID for the given username.</li>
     * </ul>
     *
     * @param username the username to be checked
     * @param password the password to be validated
     * @param deviceID the device ID to be validated
     * @return {@code true} if all the provided credentials are correct
     * @throws NoSuchUserException        if the username does not exist in the system
     * @throws IncorrectPasswordException if the provided password does not match the stored password
     * @throws IncorrectDeviceIDException if the provided device ID does not match the stored device ID
     */
    private boolean checkUsernamePassword(String username, String password, String deviceID)
            throws NoSuchUserException, IncorrectPasswordException, IncorrectDeviceIDException {
        //Check that the user exists
        if (!users.containsKey(username)) {
            throw new NoSuchUserException(username);
        }

        Pair<String, String> storedCredentials = users.get(username);

        if (!storedCredentials.getLeft().equals(password)) {
            throw new IncorrectPasswordException(username, password);
        } else if (!storedCredentials.getRight().equals(deviceID)) {
            throw new IncorrectDeviceIDException(username, deviceID);
        }
        return true;
    }

    /**
     * Checks if the Air Quality Management server contains a user with the given username.
     *
     * @param username the username to check
     * @return true if the server contains the user, false otherwise
     */
    public boolean isUser(String username) {
        return users.containsKey(username);
    }

    /**
     * Checks if the user with the given username is authenticated.
     *
     * @param username the username to check
     * @return true if the user is authenticated, false otherwise
     * @throws NoSuchUserException if the username is not registered
     */
    public boolean isAuthenticated(String username)
        throws NoSuchUserException
    {
        if (!isUser(username)){
            throw new NoSuchUserException(username);
        }
        return authenticationStatus.get(username) == AuthenticationStatus.AUTHENTICATED;
    }

    /**
     * Retrieves the role assigned to the user with the given username.
     *
     * @param username the username to check
     * @return the role assigned to the user
     * @throws NoSuchUserException if the username is not registered
     */
    public Role getRole(String username)
            throws NoSuchUserException{
        if (!isUser(username)){
            throw new NoSuchUserException(username);
        }
        return roles.get(username);
    }
}
