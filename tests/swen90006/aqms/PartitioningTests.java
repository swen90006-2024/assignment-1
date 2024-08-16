package swen90006.aqms;

import org.junit.*;

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
            throws DuplicateUserException, InvalidUsernameException, InvalidPasswordException, InvalidDeviceIDException {
        // Initialize the AQMS instance and create a dummy user. This setup runs before each test
        aqms = new AQMS();
        aqms.register("UserNameA", "Password1!", "1234");
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

}
