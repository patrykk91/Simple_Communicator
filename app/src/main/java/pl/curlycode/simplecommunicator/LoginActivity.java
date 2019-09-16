package pl.curlycode.simplecommunicator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();

    EditText editTextPassword;
    EditText editTextLogin;
    Button buttonLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPassword = findViewById(R.id.editTextPassword);
        editTextLogin = findViewById(R.id.editTextLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        final String login = editTextLogin.getText().toString();
        final String password = editTextPassword.getText().toString();
        if (login.trim().length() > 0 && password.trim().length() > 0) {
            firebaseAuth.signInWithEmailAndPassword(login, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete success: ");
                        userLoggedIn(login);
                    } else {
                        Toast.makeText(LoginActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onComplete fail: ");
                        if (task.getException().getMessage().contains("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                            firebaseAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "onComplete: ");
                                    if (task.isSuccessful()) {
                                        userLoggedIn(login);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void userLoggedIn(String email) {
        String name = email.split("@")[0];
        FirebaseDatabase.getInstance().getReference("users").child(name).setValue(new Person(name, firebaseAuth.getCurrentUser().getUid())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: ");
            }
        });
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intent);
        LoginActivity.this.finish();
    }
}
