package swen90006.aqms;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

import org.junit.*;
import static org.junit.Assert.*;

//By extending PartitioningTests, we inherit the tests from that class
public class BoundaryTests
    extends PartitioningTests
{
    //Add another test
    @Test public void anotherTest()
    {
	//include a message for better feedback
	final int expected = 2;
	final int actual = 2;
	assertEquals("Some failure message", expected, actual);
    }

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
