package com.foxinfotech.clearableedittext;

/**
 * Created by suu on 7/24/2015.
 */
public class RegexConstants {

    /**
     * regex for allowing only text and no special character
     */
    public static final String VALIDATE_PERSON_NAME="^[\\w+\\s*\\w*]{3,}$";

    /**
     * regex to allow special character {.}{_}{@}{-}. Mainly allowed to define user name.
     */
    public static final String VALIDATE_USER_NAME_TEXT="^[\\w+\\s*\\w*]{3,}$";

    /**
     * regex to check for valid mobile number of length without country code
     */
    public static final String VALIDATE_PHONE_NUMBER_LEN_10="^[0-9]+$";
    public static final String VALIDATE_PHONE_NUMBER_LEN_15="";

    public static final String VALIDATE_EMAIL="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * regex that allows input to be between 4 to 15 characters in length
     */
    public static final String VALIDATE_PASSWORD_TEXT_MIN_4_MAX_15="^[-\\w]+(?:\\W+[-\\w]+){3,14}\\W*$";

    public static final String VALIDATE_CHARACTER_LIMIT_LEN_30="[\\w]{1,30}";
    public static final String VALIDATE_CHARACTER_LIMIT_LEN_150="[\\w]{1,150}";

    public static final String VALIDATE_ZIP_CODE="^\\d{5,6}(?:[-\\s]\\d{4})?$";




}
