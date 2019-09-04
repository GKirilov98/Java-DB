package gamestore.domain;

public class Messages {
    //REGISTERED
    public static final String EMAIL_CANNOT_BE_NULL = "Email cannot be null!";
    public static final String INVALID_EMAIL = "Invalid Email!";
    //PASSWORD
    public static final String CONFIRM_PASSWORD_CANNOT_BE_NULL = "Confirm password cannot be null!";
    public static final String PASSWORD_CANNOT_BE_NULL = "Password cannot be null!";
    public static final String PASSWORD_DONT_MATCH = "Passwords don't match.";
    public static final String INVALID_PASSWORD =  "Invalid password! It must be at least 6 symbols and must contain at least 1 uppercase, 1 lowercase letter and 1 digit.";
    //USER
    public static final String USER_ALREADY_EXIST = "User already exist!";
    public static final String SUCCESS_REGISTERED = "%s was registered.";
    //LOG OUT
    public static final String CANNOT_LOG_OUT = "Cannot log out. No user was logged in.";
    public static final String SUCCESS_LOG_OUT = "User %s successfully logged out.%n";
    //LOG IN
    public static final String SESSION_IS_TAKEN = "Session is taken.";
    public static final String SUCCESS_LOG_IN= "Successfully logged in ";
    public static final String LOG_IN_AS_ADMIN= "Please log in as Admin!";
    public static final String NO_SUCH_USER = "There is no such user!";
    public static final String INCORECT_PASSWORD_EMAIL = "Incorrect username / password";
    //ADD GAME
    // Title
    public static final String TITLE_NOT_NULL = "The Name must be at least 3 Symbols";
    //PRICE
    public static final String PRICE_CANNOT_BE_NULL = "Price can not be null and must be a positive number with precision up to 2 digits after the floating point.";
    //SIZE
    public static final String SIZE_CANNOT_BE_NULL = "Size can not be null and must be a positive number with precision up to 1 digit after the floating point." ;
    //TRAILER
    public static final String TRAILER_CANNOT_BE_NULL = "Trailer can not be null and their ID is exactly 11 characters";
    public static final String THUMBNAIL_ADD = "Thumbnail should be a plain text starting with http://, https:// or null";
    //DESCRIPTION
    public static final String DESCRIPTION_LENGTH = "Description must be at least 20 symbols";

    public static final String GAME_ALREADY_EXIST = "THE GAME - %s already exist!";
    public static final String SUCCESS_GAME_ADDED = "The GAME - %s is successfully added!";
    public static final String GAME_DOESNT_EXIST = "The GAME with ID %d does not exist!";
    public static final String DELETED_GAME = "Deleted %s";
    public static final String EDITED_GAME = "Edited %s";
}
