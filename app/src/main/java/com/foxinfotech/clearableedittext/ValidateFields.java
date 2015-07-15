package com.foxinfotech.clearableedittext;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateFields {

    public static final String REQUIRED_MSG = "Required";
    private static final String EMAIL_MSG = "Invalid email";
    private static final String PERSON_NAME_MSG = "Person name should not have any special character";
    private static final String PWD_MSG_LENGTH = "Password must be 8-20 characters long";
    private static final String TAG ="info" ;

    public ValidateFields() {

    }

    public static String validate(String textValue, int fieldType) {

        Log.i("info", "Validate mathod called for type :: " + fieldType);
        String error_text = null;
        switch (fieldType) {

            case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
                error_text = validateEmailAddress(textValue);
                break;
            case InputType.TYPE_TEXT_VARIATION_PERSON_NAME:
                error_text = validatePersonName(textValue);
                break;
            case InputType.TYPE_TEXT_VARIATION_PASSWORD:
                error_text = validateTextPassword(textValue);
                break;

            default:
                break;
        }
        return error_text;
    }

    private static String validateTextPassword(String textValue) {
        // TODO Auto-generated method stub
        Log.i("info", "Validate password called");
        if (textValue.length() >= 3 && textValue.length() <= 20) {
            return null;
        } else {
            return "Minimum 8 characters and max 20 characters";
        }
    }

    public static boolean validatePassword(String password, String cnfrmPassword) {
        return password.equals(cnfrmPassword);
    }

    private static String validatePersonName(String textValue) {
        // TODO Auto-generated method stub
        String PERSON_NAME = "^[\\w+\\s*\\w*]{3,}$";
        Pattern pattern = Pattern.compile(PERSON_NAME);
        Matcher matcher = pattern.matcher(PERSON_NAME);

        if (matcher.matches()) {
            return null;
        } else {
            return "Person name should not have any special character.";
        }
    }

    private static String validateEmailAddress(String textValue) {
        // TODO Auto-generated method stub
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(textValue);
        Log.i("info", "Validate E-Mail called");
        if (matcher.matches()) {
            return null;
        } else {
            return "Enter a valid E-Mail";
        }
    }

    private static String validateNumber(String textValue) {
        String NUMBER_PATTERN = "^[0-9]+$";
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(textValue);
        if (matcher.matches()) {
            return null;
        } else {
            return "Number errror";
        }
    }

    public static boolean validateMandatoryFields(View lay, boolean bool) {
        // TODO Auto-generated method stub
        boolean flag = bool;
        try {
            ViewGroup layout = (ViewGroup) lay;
            Log.i("info", "child count = " + layout.getChildCount());
            for (int i = 0; i < layout.getChildCount(); i++) {
                View view = layout.getChildAt(i);
                if (view instanceof ClearableEditText) {
                    ClearableEditText et = (ClearableEditText) view;
                    String tag = et.getTag() != null ? et.getTag().toString() : "";
                    //Drawable[] drawable = et.getCompoundDrawables();
                    boolean error_visible = false;
//					if (drawable[0] != null)
//						error_visible = (drawable[0]).isVisible();

                    error_visible = et.isError();
                    if (et.isMandatory() || error_visible) {
                        if (et.getText().toString().trim().equalsIgnoreCase("")
                                || error_visible) {
                            Log.i("info", "Missing field = "
                                    + et.getText().toString());

                            et.setBackgroundResource(R.drawable.error_edt_txt_bg);

                            if (flag) {
                                et.setSelected(true);
                                flag = false;
                            }
                        }
                    }
                } else if (view instanceof Spinner) {
                    Spinner sp = (Spinner) view;

                    flag= (sp.getSelectedItemPosition()) == 0 ? false : true;
                } else if (view instanceof ViewGroup) {
                    flag = validateMandatoryFields(view, flag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
