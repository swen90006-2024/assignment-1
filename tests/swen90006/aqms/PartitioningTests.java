package swen90006.aqms;

import org.junit.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class is a template for your assignment, designed to help you get started with writing JUnit tests.
 * <p>
 * It sets up the testing environment by creating an instance of the AQMS (Air Quality Management System) class
 * and includes a few example tests to demonstrate how to write and structure your JUnit tests.
 * <p>
 * Please add your own tests starting from the section marked "ADD YOUR TESTS HERE".
 */
public class PartitioningTests {
    // The AQMS instance variable aqms is shared across all test methods in this class
    protected AQMS aqms;

    /**
     * The setup method annotated with "@Before" runs before each test.
     * It initializes the AQMS instance and creates a dummy user.
     * Use this method to set up any common test data or state.
     */
    @Before
    public void setUp()
            throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException, InvalidDeviceIDException, InvalidAssignedRoleException, NoSuchUserException {
        // Initialize the AQMS instance and create a dummy user. This setup runs before each test
        aqms = new AQMS();
        aqms.register("UserNameA", "Password1!", "1234");
        aqms.register("UserNameC","Password1!","1234");
        aqms.register("UserM","Password1!","1234");
        aqms.register("UserN","Password1!","5678");
        aqms.assignRole("UserM",AQMS.Role.ADMIN);

        aqms.addData("UserM", Arrays.asList(10,20,30));
        aqms.addData("UserM", Arrays.asList(40,50,60));

    }

    /**
     * The teardown method annotated with "@After" runs after each test.
     * It's useful for cleaning up resources or resetting states.
     * Currently, this method doesn't perform any actions, but you can customize it as needed.
     */
    @After
    public void tearDown() {
        // No resources to clean up in this example, but this is where you would do so if needed
    }

    /**
     * This is a basic example test annotated with "@Test" to demonstrate how to use assertions in JUnit.
     * The assertEquals method checks if the expected value matches the actual value.
     */
    @Test
    public void aTest() {
        final int expected = 2;
        final int actual = 1 + 1;
        // Use of assertEquals to verify that the expected value matches the actual value
        assertEquals(expected, actual);
    }

    /**
     * This example test shows how to check if a user exists after registration.
     * The point is to let you know how to use assertTrue and assertFalse to verify conditions.
     */
    @Test
    public void anotherTest()
            throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException, InvalidDeviceIDException {
        aqms.register("UserNameB", "Password2!", "1234");

        // Verify that the newly registered user exists and that a non-registered user does not exist
        assertTrue(aqms.isUser("UserNameB"));
        assertFalse(aqms.isUser("NonUser"));
    }

    /**
     * This test checks if the InvalidDeviceIDException is correctly thrown when registering with an invalid device ID.
     * The expected exception is specified in the @Test annotation.
     */
    @Test(expected = InvalidDeviceIDException.class)
    public void anExceptionTest()
            throws Throwable {
        // Test registration with a valid username and password, but an invalid device ID
        // to test whether the appropriate exception is thrown.
        aqms.register("UserNameB", "Password2!", "12345");
    }

    /**
     * This is an example of a test that is designed to fail.
     * It shows how to include an error message to provide feedback when a test doesn't pass.
     */
    @Test
    public void aFailedTest() {
        // This test currently fails to demonstrate how JUnit reports errors
        final int expected = 2;
        final int actual = 1 + 2;
        // Uncomment the following line to observe a test failure.
        // assertEquals("Some failure message", expected, actual);
    }

    // ADD YOUR TESTS HERE
    // This is the section where you will add your own tests.
    // Follow the examples above to create your tests.

    // Testcase for Register

    @Test(expected = DuplicateUserException.class)
    public void testRegisterDuplicateUser()
            throws Throwable{
        // The Username is existed.
        aqms.register("UserNameA", "Password1!", "1234");
    }

    @Test(expected = InvalidUsernameException.class)
    public void testRegisterShortUsername()
            throws Throwable{
        // The Username is short.
        aqms.register("Us","Password1!","1234");
    }

    @Test(expected = InvalidUsernameException.class)
    public void testRegisterInvalidCharactersUsername()
        throws Throwable{
        // The Username contains non-alphanumeric character.
        aqms.register("Us123","Password1!","1234");
    }

    @Test(expected = InvalidPasswordException.class)
    public void testRegisterShortPassword()
            throws Throwable{
        // The password is short.
        aqms.register("UserNameC","Pass","1234");
    }

    @Test(expected = InvalidPasswordException.class)
    public void testRegisterWithoutLetterPassword()
            throws Throwable{
        // The password without letter.
        aqms.register("UserNameC","12345678!","1234");
    }

    @Test(expected = InvalidPasswordException.class)
    public void testRegisterWithoutNumberPassword()
            throws Throwable{
        // The password without number.
        aqms.register("UserNameC","Password!","1234");
    }

    @Test(expected = InvalidPasswordException.class)
    public void testRegisterWithoutSpecialCharacterPassword()
            throws Throwable{
        // The password without special character.
        aqms.register("UserNameC","Password1!","1234");
    }

    @Test(expected = InvalidDeviceIDException.class)
    public void testRegisterInvalidLengthDeviceID()
            throws Throwable{
        // The DeviceID is not exactly 4 characters long.
        aqms.register("UserNameC","Password1!","12345");
    }

    @Test(expected = InvalidDeviceIDException.class)
    public void testRegisterNonNumericDeviceID()
            throws Throwable{
        // The DeviceID contains non-numeric character.
        aqms.register("UserNameC","Password1!","a234");
    }

    @Test
    public void testRegisterValidDeviceID()
            throws Throwable{
        // The DeviceID is valid.
        aqms.register("UserNameC","Password1!","1234");
        assertTrue(aqms.isUser("UserNameC"));
    }

    // Testcase for Login

    @Test(expected = NoSuchUserException.class)
    public void testLoginNoSuchUser()
            throws Throwable{
        // Username is not existed.
        aqms.login("usernameA","Password1!","1234");
    }

    @Test(expected = IncorrectPasswordException.class)
    public void testLoginIncorrectPassword()
            throws Throwable{
        // Wrong password.
        aqms.login("UserNameC","WrongPassword1!","1234");
    }

    @Test(expected = InvalidDeviceIDException.class)
    public void testLoginIncorrectDeviceID()
            throws Throwable{
        // Wrong deviceID.
        aqms.login("UserNameC","Password1!","4321");
    }

    @Test
    public void testRegisterCorrectDeviceID()
            throws Throwable{
        // Correct deviceID.
        aqms.register("UserNameC","Password1!","1234");
        assertTrue(aqms.isAuthenticated("UserNameC"));
    }

    // Testcase for assignRole

    @Test(expected = NoSuchUserException.class)
    public void testAssignRoleNoSuchUser()
            throws Throwable{
        //  Username is not existed.
        aqms.assignRole("usernameA",AQMS.Role.ADMIN);
    }

    @Test(expected = InvalidDeviceIDException.class)
    public void testAssignRoleDowngrade()
            throws Throwable{
        // Downgrade User1 from ADMIN to USER.
        aqms.assignRole("UserM",AQMS.Role.USER);
    }

    @Test
    public void testAssignRoleKeep()
            throws Throwable{
        // Keep User2 as USER.
        aqms.assignRole("UserN",AQMS.Role.USER);
        assertEquals(AQMS.Role.USER,aqms.getRole("UserN"));
    }

    // Testcase for getData

    @Test(expected = NoSuchUserException.class)
    public void testGetDataNoSuchUser()
            throws Throwable{
        // Username is not existed.
        aqms.getData("usernameA",0);
    }

    @Test(expected = UnauthenticatedUserException.class)
    public void testGetDataUnauthenticatedUser()
            throws Throwable{
        // User Unauthenticated
        aqms.getData("UserN",0);
    }

    @Test(expected = AccessRightException.class)
    public void testGetDataUserAccessRight()
            throws Throwable{
        // User2 login as USER and trying to getData.
        aqms.login("UserN","Password1!","5678");
        aqms.getData("UserN",0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetDataIndexOutOfBounds()
            throws Throwable{
        // User1 login and trying to getData but index out of bounds.
        aqms.login("UserM","Password1!","1234");
        aqms.getData("UserM",10);
    }

    @Test
    public void testGetDataValid()
        throws Throwable{
        // User1 login and trying to getData.
        aqms.login("UserM","Password1!","1234");
        List<Integer> data = aqms.getData("UserM",0);
        assertEquals(Arrays.asList(10, 20, 30), data);
    }

}
