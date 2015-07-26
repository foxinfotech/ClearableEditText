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



public class ClearableEditText extends EditText implements View.OnFocusChangeListener{

    private String error = null;
    public String defaultText = null;
    private boolean hasError = false;
    private boolean isMandatory=false;
    private String errorMessage;

    private String regex;
    private Context context;

    private Drawable clearDrawable = getResources().getDrawable(
            android.R.drawable.presence_offline); // X image

    private Drawable warningDrawable = getResources().getDrawable(
            android.R.drawable.ic_dialog_alert); // X image

    private int errorBackgroundDrawable = R.drawable.error_edt_txt_bg; // drawable to make outline of field red
    private int normalBackgroundDrawable = R.drawable.edt_txt_shape; // curved drawable for an


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

    public ClearableEditText(Context context,String defaultText) {
        super(context);
        this.context = context;
        this.defaultText =defaultText;
        init();
    }

    void init() {

        // Set bounds of our X button
        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(),
                clearDrawable.getIntrinsicHeight());

        warningDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(),
                clearDrawable.getIntrinsicHeight());

        // There may be initial text in the field, so we may need to display the
        // button
        manageClearButton();
        this.setText(defaultText == null ? "" : defaultText);

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
                        - clearDrawable.getIntrinsicWidth()) {
                    et.setText("");
                    ClearableEditText.this.removeClearButton();
                }

                if (event.getX() < et.getPaddingLeft()
                        + warningDrawable.getIntrinsicWidth()
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        checkEditText();
    }

    private void checkEditText() {
        ClearableEditText et = ClearableEditText.this;
        String text_val = et.getText().toString().trim();
        String validationRegex = et.getRegex();
        if (!text_val.trim().equalsIgnoreCase("") && validationRegex!=null) {
            boolean results = ValidateFields.getInstance().validate(text_val,
                    validationRegex);
            if (!results) {
                showErrorOnField();
                et.setErrorMessage(errorMessage);
                et.setHasError(true);
                et.setBackgroundResource(errorBackgroundDrawable);
           } else {
                removeErrorOnField();
                et.setErrorMessage(null);
                et.setHasError(false);
                et.setBackgroundResource(normalBackgroundDrawable);
            }
        }
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex, String errorMessage) {
        this.regex = regex;
        this.errorMessage=errorMessage;
    }


    public void setErrorBackgroundDrawable(int errorBackgroundDrawable) {
        this.errorBackgroundDrawable = errorBackgroundDrawable;
    }

    public void setNormalBackgroundDrawable(int normalBackgroundDrawable) {
        this.normalBackgroundDrawable = normalBackgroundDrawable;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    private void setHasError(boolean isError) {
        this.hasError = isError;
    }

    public boolean hasError() {
        return hasError;
    }

    private void setErrorMessage(String error) {
        this.error = error;
    }

    private String getErrorMessage() {
        return error;
    }

    /**
     * Display clear button on the edit text based on the input in the field.
     */
    void manageClearButton() {
        if (this.getText().toString().trim().equals(""))
            removeClearButton();
        else
            addClearButton();
    }


    /**
     * Add clear button on the text field
     */
    void addClearButton() {
        this.setCompoundDrawables(this.getCompoundDrawables()[0],
                this.getCompoundDrawables()[1], clearDrawable,
                this.getCompoundDrawables()[3]);
    }


    /**
     * Remove clear button from the text field
     */
    void removeClearButton() {
        this.setCompoundDrawables(null, this.getCompoundDrawables()[1], null,
                this.getCompoundDrawables()[3]);
    }

    /**
     * Display error message and icon on the field if the input text is not validated.
     */
    void showErrorOnField() {
        this.setCompoundDrawables(warningDrawable, this.getCompoundDrawables()[1],
                clearDrawable, this.getCompoundDrawables()[3]);
        this.setCompoundDrawablePadding(5);

    }

    /**
     * Removes error message and icon from the field if the input text is validated.
     */
    void removeErrorOnField() {
        this.setCompoundDrawables(null, this.getCompoundDrawables()[1],
                clearDrawable, this.getCompoundDrawables()[3]);

    }

    /**
     *
     * @return
     */
    public String getDefaultText() {
        return defaultText;
    }

    /**
     *
     * @param defaultText the default value for the edit text
     */
    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    /**
     *
     */
    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    /**
     *
     * @return get the Drawable used for clearing text.
     */
    public Drawable getClearDrawable() {
        return clearDrawable;
    }

    /**
     *
     * @param clearDrawable the drawable for clearing text on the field
     */
    public void setClearDrawable(Drawable clearDrawable) {
        this.clearDrawable = clearDrawable;
    }

    /**
     *
     * @return get the Drawable used for displaying warning on field.
     */
    public Drawable getWarningDrawable() {
        return warningDrawable;
    }

    /**
     *
     * @param warningDrawable the drawable for warning on the fields
     */
    public void setWarningDrawable(Drawable warningDrawable) {
        this.warningDrawable = warningDrawable;
    }

    /**
     *
     * @return true or false based on whether current field is mandatory or not.
     */
    public boolean isMandatory() {
        return isMandatory;
    }


}
