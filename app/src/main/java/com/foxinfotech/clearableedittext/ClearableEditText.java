package com.foxinfotech.clearableedittext;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;



public class ClearableEditText extends EditText {

    private String error = null;
    public String defaultValue = "";
    private boolean isError = false;
    private boolean isMandatory;
    private Context context;

    final Drawable img_clear = getResources().getDrawable(
            android.R.drawable.presence_offline); // X image
    final Drawable img_warning = getResources().getDrawable(
            android.R.drawable.ic_dialog_alert); // X image


    public ClearableEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    void init() {

        // Set bounds of our X button
        img_clear.setBounds(0, 0, img_clear.getIntrinsicWidth(),
                img_clear.getIntrinsicHeight());

        img_warning.setBounds(0, 0, img_clear.getIntrinsicWidth(),
                img_clear.getIntrinsicHeight());

        // There may be initial text in the field, so we may need to display the
        // button
        manageClearButton();
        this.setText(defaultValue);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ClearableEditText et = ClearableEditText.this;
                // Is there an X showing? array elements in order for left top
                // right bottom
                if (et.getCompoundDrawables()[2] == null
                        && et.getCompoundDrawables()[0] == null) {
                    return false;
                }
                // Only do this for up touches
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }

                // Is touch on our clear button?
                if (event.getX() > et.getWidth() - et.getPaddingRight()
                        - img_clear.getIntrinsicWidth()) {
                    et.setText("");
                    ClearableEditText.this.removeClearButton();
                }

                if (event.getX() < et.getPaddingLeft()
                        + img_warning.getIntrinsicWidth()
                        && et.getCompoundDrawables()[0] != null) {

                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .create();
                    dialog.setTitle("Error");
                    dialog.setIcon(android.R.drawable.ic_dialog_info);
                    dialog.setMessage(et.getErrorMessage());
                    dialog.show();

                }

                return false;
            }

        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                ClearableEditText.this.manageClearButton();
                checkEditText();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

        this.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                ClearableEditText et = ClearableEditText.this;
                String tag = et.getTag() != null ? et.getTag().toString() : "";
                String text_val = et.getText().toString().trim();
                int input_type = et.getInputType();
                // validate only if a field has lost focus and the edit text is
                // not blank
                if (!hasFocus && !text_val.equalsIgnoreCase("")) {
                    String error_text = ValidateFields.validate(text_val,
                            input_type - 1);
                    if (error_text != null) {
                        showErrorOnField();
                        et.setErrorMessage(error_text);
                        et.setError(true);


                    } else {
                        removeErrorOnField();
                        et.setErrorMessage(null);
                        et.setError(false);



                    }
                }
            }
        });

    }

    private void checkEditText() {
        ClearableEditText et = ClearableEditText.this;
        String tag = et.getTag() != null ? et.getTag().toString() : "";
        String text_val = et.getText().toString().trim();
        int input_type = et.getInputType();
        // validate only if a field has lost focus and the edit text is
        // not blank
        if (!text_val.equalsIgnoreCase("")) {
            String error_text = ValidateFields.validate(text_val,
                    input_type - 1);
            if (error_text != null) {

                showErrorOnField();
                et.setErrorMessage(error_text);
                et.setError(true);

                  et.setBackgroundResource(R.drawable.error_edt_txt_bg);


            } else {
                removeErrorOnField();
                et.setErrorMessage(null);
                et.setError(false);

                    et.setBackgroundResource(R.drawable.edt_txt_shape);

            }
        } else {
            String error_text = ValidateFields.REQUIRED_MSG;
            if (error_text != null) {
                et.setErrorMessage(error_text);
                et.setError(true);
                et.setBackgroundResource(R.drawable.error_edt_txt_bg);
                showErrorOnField();
            }
        }
    }

    private void setError(boolean isError) {
        this.isError = isError;
    }

    public boolean isError() {
        return isError;
    }

    private void setErrorMessage(String error) {
        this.error = error;
        Log.i("info", "Error is  :: " + error);
    }

    private String getErrorMessage() {
        return error;
    }

    void manageClearButton() {
        if (this.getText().toString().trim().equals(""))
            removeClearButton();
        else
            addClearButton();
    }

    void addClearButton() {
        this.setCompoundDrawables(this.getCompoundDrawables()[0],
                this.getCompoundDrawables()[1], img_clear,
                this.getCompoundDrawables()[3]);
    }

    void removeClearButton() {
        this.setCompoundDrawables(null, this.getCompoundDrawables()[1], null,
                this.getCompoundDrawables()[3]);
    }

    void showErrorOnField() {
        this.setCompoundDrawables(img_warning, this.getCompoundDrawables()[1],
                img_clear, this.getCompoundDrawables()[3]);

    }

    void removeErrorOnField() {
        this.setCompoundDrawables(null, this.getCompoundDrawables()[1],
                img_clear, this.getCompoundDrawables()[3]);

    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setMandatory() {
        isMandatory = true;
    }

    public boolean isMandatory() {
        return isMandatory;
    }
}
