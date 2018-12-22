package com.wordpress.commonplayground.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wordpress.commonplayground.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mUsernameView, mEmailView, mPasswordView, mPasswordConfirmView;
    private String username, email, password, passwordConfirm;
    private View mProgressView;
    private View mLoginFormView;
    private boolean cancel = false;
    private View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupRegisteringForm();
    }

    private void setupRegisteringForm() {
        mUsernameView = (EditText) findViewById(R.id.username);
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRegister(textView); /* RICHTIGE VIEW?!*/
                    return true;
                }
                return false;
            }
        });

        mPasswordConfirmView = (EditText) findViewById(R.id.password_confirm);

        Button mRegistrationButton = (Button) findViewById(R.id.registration_button);
        mRegistrationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister(view);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister(View view) {
        resetErrors();
        storeRegisteringCredentials();

        // Check for valid input from the bottom to the top that the focus is at the top if there are several mistakes
        checkIfPasswordsAreEqual();
        checkForValidPassword();
        checkForValidEMailAddress();
        checkForFilledUsername();

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            requestToBackendRegister(view);
        }
    }

    private void resetErrors() {
        mUsernameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordConfirmView.setError(null);
        cancel = false;
    }

    private void storeRegisteringCredentials() {
        username = mUsernameView.getText().toString();
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
        passwordConfirm = mPasswordConfirmView.getText().toString();
    }

    private void checkForValidPassword() {
        if (TextUtils.isEmpty(passwordConfirm)) {
            mPasswordConfirmView.setError(getString(R.string.error_field_required));
            focusView = mPasswordConfirmView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (password.length() < 8) {
            mPasswordView.setError(getString(R.string.error_short_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (password.length() > 30) {
            mPasswordView.setError(getString(R.string.error_long_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
    }

    private void checkForValidEMailAddress() {
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
    }

    private void checkForFilledUsername() {
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }
    }

    private void checkIfPasswordsAreEqual() {
        boolean passwordsEqual = isPasswordConfirmed(password, passwordConfirm);
        if (!passwordsEqual) {
            mPasswordConfirmView.setError(getString(R.string.error_invalid_password_confirm));
            focusView = mPasswordConfirmView;
            cancel = true;
        }
    }

    private boolean isEmailValid(String email) {
        String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";
        Matcher matcher = Pattern.compile(validemail).matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        String validpassword = "^([a-zA-Z0-9@*#!?$&.-_]{8,30})$";
        Matcher matcher = Pattern.compile(validpassword).matcher(password);
        return matcher.matches();
    }

    private boolean isPasswordConfirmed(String password, String passwordConfirm) {
        if(password.equals(passwordConfirm)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void requestToBackendRegister(View view) {
        /*get screen content*/
        final String username = mUsernameView.getText().toString();
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/registerNewUser";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = new String();
                Log.d("Response.Register", response.toString());
                switch (Integer.parseInt(response.toString())){
                    case -3: result = getString(R.string.email_double_error); break;
                    case -2: result = getString(R.string.username_double_error); break;
                    case 0: result = getString(R.string.registration_succsess);
                }
                Snackbar.make(view, result, 5000)
                        .setAction("Action", null).show();
                if (Integer.parseInt(response.toString())==0) {
                    Intent openLoginActivity = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(openLoginActivity);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Register", String.valueOf(error));
                Snackbar.make(view, getString(R.string.new_error), 5000)
                        .setAction("Action", null).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", username);
                MyData.put("email", email);
                MyData.put("password", password);
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }
}