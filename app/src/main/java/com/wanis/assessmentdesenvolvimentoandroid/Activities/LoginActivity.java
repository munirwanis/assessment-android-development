package com.wanis.assessmentdesenvolvimentoandroid.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wanis.assessmentdesenvolvimentoandroid.Database.FirebaseDB;
import com.wanis.assessmentdesenvolvimentoandroid.Entities.Users;
import com.wanis.assessmentdesenvolvimentoandroid.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnLogin;
    private FirebaseAuth autenticacao;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etEmail.getText().toString().equals("") && !etSenha.getText().toString().equals("")) {
                    users = new Users();
                    users.setEmail(etEmail.getText().toString());
                    users.setPassword(etSenha.getText().toString());
                    validarLogin();

                } else {
                    Toast.makeText(LoginActivity.this, "Preencha os campos do e-mail e senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void validarLogin() {
        autenticacao = FirebaseDB.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(users.getEmail(), users.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    telaTarefas();
                    Toast.makeText(LoginActivity.this, "Login Efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Digite o Login e Senha corretamente!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void telaTarefas() {
        Intent intent = new Intent(LoginActivity.this, TasksActivity.class);
        startActivity(intent);
    }
}
