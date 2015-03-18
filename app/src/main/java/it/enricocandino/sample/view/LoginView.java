package it.enricocandino.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import it.enricocandino.sample.R;

/**
 * Created by enrico on 20/02/15.
 */
public class LoginView extends RelativeLayout {

    private LoginViewClickListener listener;

    private View loginContainer;
    private ProgressBar loadingWheel;

    private EditText usernameET;
    private EditText passwordET;
    private Button loginBtn;

    public LoginView(Context context) {
        super(context);
        initView();
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.login_view, this);

        loginContainer = findViewById(R.id.login_container);
        loadingWheel = (ProgressBar) findViewById(R.id.loading_wheel);

        usernameET = (EditText) findViewById(R.id.username_edittext);
        passwordET = (EditText) findViewById(R.id.password_edittext);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void setListener(LoginViewClickListener listener) {
        this.listener = listener;
    }

    public void login() {
        String username = usernameET.getText().toString().trim().toLowerCase();
        String password = passwordET.getText().toString().trim();

        // TODO check username/password and handle errors
        if(listener != null) {
            listener.onLoginClick(username, password);
        }
    }

    public void showLoading(boolean show) {
        if(show) {
            loginContainer.setVisibility(View.GONE);
            loadingWheel.setVisibility(View.VISIBLE);
        } else {
            loginContainer.setVisibility(View.VISIBLE);
            loadingWheel.setVisibility(View.GONE);
        }
    }

    public String getUsername() {
        return usernameET.getText().toString().trim().toLowerCase();
    }

    public String getPassword() {
        return passwordET.getText().toString().trim();
    }

    public interface LoginViewClickListener {
        public void onLoginClick(String username, String password);
    }
}
