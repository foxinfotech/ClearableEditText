package com.foxinfotech.clearableedittext;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener{

    ClearableEditText name;
    ClearableEditText email;
    ClearableEditText password;
    ClearableEditText phone;
    ClearableEditText description;
    Spinner roles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Name field
        name=(ClearableEditText)findViewById(R.id.edt_first_name);
        name.setMandatory(true);
        name.setRegex(RegexConstants.VALIDATE_PERSON_NAME, "Invalid Name");

        email=(ClearableEditText)findViewById(R.id.edt_email);
        email.setMandatory(true);
        email.setRegex(RegexConstants.VALIDATE_EMAIL, "Invalid email address");

        password=(ClearableEditText)findViewById(R.id.edt_password);
        password.setMandatory(true);
        password.setRegex(RegexConstants.VALIDATE_PERSON_NAME, "Invalid Password");

        phone=(ClearableEditText)findViewById(R.id.edt_phone);
        phone.setMandatory(true);
        phone.setRegex(RegexConstants.VALIDATE_PHONE_NUMBER_LEN_10, "Invalid Phone number");

        description=(ClearableEditText)findViewById(R.id.edt_description);
        description.setMandatory(false);

        roles=(Spinner)findViewById(R.id.spin_role);


        Button btnSubmit=(Button)findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
    }

 @Override
    public void onClick(View v) {
        Button submit=(Button)v;
        boolean hasNoError = ValidateFields.getInstance().validateMandatoryFields(
                MainActivity.this.findViewById(android.R.id.content),true);
        Toast.makeText(MainActivity.this, "result :: "+hasNoError, Toast.LENGTH_SHORT).show();
    }
}
