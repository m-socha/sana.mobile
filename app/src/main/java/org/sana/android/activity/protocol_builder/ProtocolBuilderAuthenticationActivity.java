package org.sana.android.activity.protocol_builder;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.sana.R;
import org.sana.android.activity.BaseActivity;

/**
 * Activity for authenticating with protocol builder.
 */
public class ProtocolBuilderAuthenticationActivity extends BaseActivity {

    private EditText mInputUsername;
    private EditText mInputPassword;

    private Button mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewLocalized(R.layout.activity_protocol_builder_authentication);

        mInputUsername = (EditText) findViewById(R.id.input_username);
        mInputPassword = (EditText) findViewById(R.id.input_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    private void logIn() {
        String username = mInputUsername.getText().toString().trim();
        String password = mInputPassword.getText().toString();

        // TODO: Implement login
    }

    protected boolean validUsernameAndPassword(String username, String password) {
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
    }

    private void enableInput() {
        mInputUsername.setEnabled(true);
        mInputPassword.setEnabled(true);
        mButtonLogin.setEnabled(true);
    }

    private void disableInput() {
        mInputUsername.setEnabled(false);
        mInputPassword.setEnabled(false);
        mButtonLogin.setEnabled(false);
    }
}
