package muryshkin.alexey.diseasediagnosis.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import muryshkin.alexey.diseasediagnosis.Data.Authentication;
import muryshkin.alexey.diseasediagnosis.R;

public class AuthenticationActivity extends AppCompatActivity {

    private RelativeLayout loginRelativeLayout;
    private RelativeLayout signUpRelativeLayout;
    private ImageButton backImageButton;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText usernameRegEditText;
    private EditText passwordRegEditText;
    private EditText repeatPasswordRegEditText;
    private Button loginButton;
    private Button signUpButton;
    private TextView loginTextView;
    private TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        if ( Authentication.getInstance(this).isLoggedIn() )
            openProfile();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.customized_action_bar);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClick();
            }
        });

        loginRelativeLayout = (RelativeLayout) findViewById(R.id.loginRelativeLayout);
        loginRelativeLayout.setEnabled(true);
        loginRelativeLayout.setVisibility(View.VISIBLE);

        signUpRelativeLayout = (RelativeLayout) findViewById(R.id.signUpRelativeLayout);
        signUpRelativeLayout.setEnabled(false);
        signUpRelativeLayout.setVisibility(View.INVISIBLE);

        signUpTextView = (TextView) findViewById(R.id.signUpTextView);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpView();
            }
        });

        loginTextView = (TextView) findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogInView();
            }
        });

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogInClick();
            }
        });

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpClick();
            }
        });

        usernameRegEditText = (EditText) findViewById(R.id.usernameRegEditText);
        passwordRegEditText = (EditText) findViewById(R.id.passwordRegEditText);
        repeatPasswordRegEditText = (EditText) findViewById(R.id.repeatPasswordRegEditText);
    }

    private void onSignUpClick() {
        Authentication authentication = Authentication.getInstance(this);
        String username = usernameRegEditText.getText().toString();
        String password = passwordRegEditText.getText().toString();
        String rpassword = repeatPasswordRegEditText.getText().toString();

        if ( username == null || username.isEmpty() || password == null || password.isEmpty() || rpassword == null || rpassword.isEmpty()) {
            Toast.makeText(this, "Some fields are empty", Toast.LENGTH_SHORT).show();
        } else if ( !password.equals(rpassword) ) {
            Toast.makeText(this, "The passwords are different", Toast.LENGTH_SHORT).show();
        } else if ( authentication.register(username, password) ) {
            Toast.makeText(this, "You are successfully registered!", Toast.LENGTH_SHORT).show();
            openProfile();
        } else
            Toast.makeText(this, "Some unexpected error occured!", Toast.LENGTH_SHORT).show();
    }

    private void onLogInClick() {
        Authentication authentication = Authentication.getInstance(this);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if ( username == null || username.isEmpty() || password == null || password.isEmpty() ) {
            Toast.makeText(this, "Some fields are empty", Toast.LENGTH_SHORT).show();
        } else if ( authentication.logIn(username, password) ) {
            Toast.makeText(this, "You successfully loged in", Toast.LENGTH_SHORT).show();
            openProfile();
        } else
            Toast.makeText(this, "The username or password are incorrect. Recheck Them!!!", Toast.LENGTH_SHORT).show();
    }

    private void openProfile() {
        if ( getIntent().getBooleanExtra("finish", false) ) {
            setResult(RESULT_OK);
            finish();
        } else {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void openLogInView() {
        loginRelativeLayout.setEnabled(true);
        loginRelativeLayout.setVisibility(View.VISIBLE);
        signUpRelativeLayout.setEnabled(false);
        signUpRelativeLayout.setVisibility(View.INVISIBLE);
    }

    private void openSignUpView() {
        loginRelativeLayout.setEnabled(false);
        loginRelativeLayout.setVisibility(View.INVISIBLE);
        signUpRelativeLayout.setEnabled(true);
        signUpRelativeLayout.setVisibility(View.VISIBLE);
    }

    private void onBackClick() {
        finish();
    }
}
