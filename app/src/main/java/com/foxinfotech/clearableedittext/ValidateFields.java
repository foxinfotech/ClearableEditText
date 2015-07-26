package com.foxinfotech.clearableedittext;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateFields {

    private static ValidateFields instance;
    private int defaultErrorBackgroundResource =R.drawable.error_edt_txt_bg;
    private int defaultNormalBackgroundResource =R.drawable.edt_txt_shape;


    public static ValidateFields getInstance(){
        if(instance==null)
            instance=new ValidateFields();

        return instance;

    }

    public void setDefaultErrorBackgroundResource(int defaultErrorBackgroundResource) {
        this.defaultErrorBackgroundResource = defaultErrorBackgroundResource;
    }

    public void setDefaultNormalBackgroundResource(int defaultNormalBackgroundResource) {
        this.defaultNormalBackgroundResource = defaultNormalBackgroundResource;
    }

    public boolean validate(String textValue,String regex) {

        Log.i("info", "Validation Regex is :: " + regex);
        String error_text = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textValue);

        // return true if the input text is valid as per regex
        if (matcher.matches())
            return true;

        return false;
    }

    public boolean validateMandatoryFields(View lay, boolean bool) {
        boolean flag = bool;
        try {
            ViewGroup layout = (ViewGroup) lay;
            for (int i = 0; i < layout.getChildCount(); i++) {
                View view = layout.getChildAt(i);
                if (view instanceof ClearableEditText) {
                   ClearableEditText et = (ClearableEditText) view;
                   if (et.isMandatory() || et.hasError()) {
                       if (et.getText().toString().trim().equalsIgnoreCase("") || et.hasError()) {
                           et.setBackgroundResource(defaultErrorBackgroundResource);
                           if (flag) {
                               et.setSelection(0);
                               flag = false;
                           }
                       }
                   }
                } else if (view instanceof Spinner) {
                    // spinners are always mandatory
                    Spinner sp = (Spinner) view;
                    boolean isSomethingSelected= (sp.getSelectedItemPosition()) == 0 ? false : true;
                    if(!isSomethingSelected){
                        sp.setBackgroundResource(defaultErrorBackgroundResource);
                        if (flag) {
                            sp.setSelected(true);
                            flag = false;
                        }
                    }else
                        sp.setBackgroundResource(defaultNormalBackgroundResource);

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
