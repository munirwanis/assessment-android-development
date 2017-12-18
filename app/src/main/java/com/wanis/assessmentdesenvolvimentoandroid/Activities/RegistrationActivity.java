package com.wanis.assessmentdesenvolvimentoandroid.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.wanis.assessmentdesenvolvimentoandroid.Database.FirebaseDB;
import com.wanis.assessmentdesenvolvimentoandroid.Entities.Users;
import com.wanis.assessmentdesenvolvimentoandroid.Helpers.Base64Extension;
import com.wanis.assessmentdesenvolvimentoandroid.Helpers.AndroidPreferences;
import com.wanis.assessmentdesenvolvimentoandroid.R;

import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements Validator.ValidationListener  {

    Validator validator;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    private EditText etCadNome;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    @Email(message = "Digite um email valido")
    private EditText etCadEmail;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    @Password
    private EditText etCadSenha;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    @ConfirmPassword(message = "Os campos Senha e Confirmar Senha devem ser iguais!")
    private EditText etCadConfirmarSenha;

    @NotEmpty(message = "Este campo não pode ficar vazio")
    private EditText etCadCpf;


    private Button btnCadastrar, btnLimpar;
    private Users users;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        validator = new Validator(this);
        validator.setValidationListener(this);


        etCadNome = findViewById(R.id.etCadNome);
        etCadEmail = findViewById(R.id.etCadEmail);
        etCadSenha = findViewById(R.id.etCadSenha);
        etCadConfirmarSenha = findViewById(R.id.etCadConfirmarSenha);
        etCadCpf = findViewById(R.id.etCadCpf);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnLimpar = findViewById(R.id.btnLimpar);


        //criando mascara para o cpf
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(etCadCpf, smf);
        etCadCpf.addTextChangedListener(mtw);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });


        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //limpar os campos edit text
                etCadNome.setText("");
                etCadEmail.setText("");
                etCadSenha.setText("");
                etCadConfirmarSenha.setText("");
                etCadCpf.setText("");
            }
        });

    }

    private void registerUser(){
        autenticacao = FirebaseDB.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(
                users.getEmail(),
                users.getPassword()
        ).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "Usuario Cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                    String identificadorUsuario = Base64Extension.encode(users.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    users.setId(identificadorUsuario);
                    users.save();

                    AndroidPreferences androidPreferences = new AndroidPreferences(RegistrationActivity.this);
                    androidPreferences.saveUserPreferences(identificadorUsuario, users.getName());
                    abrirLoginUsuario();
                } else {

                    String exceptionMessage;

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        exceptionMessage = "Digite uma senha mais forte, contendo no mínimo 8 caracteres entre letras e números";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exceptionMessage = "O email digitado é inválido , digite um novo email.";

                    } catch (FirebaseAuthUserCollisionException e) {
                        exceptionMessage = "Esse email já está cadastrado no sistema";

                    } catch (Exception e) {
                        exceptionMessage = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(RegistrationActivity.this, "Erro: " + exceptionMessage, Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void abrirLoginUsuario(){
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public void onValidationSucceeded() {
        if (etCadSenha.getText().toString().equals(etCadConfirmarSenha.getText().toString())) {


            users = new Users();
            users.setName(etCadNome.getText().toString());
            users.setEmail(etCadEmail.getText().toString());
            users.setPassword(etCadSenha.getText().toString());
            users.setCpf(etCadCpf.getText().toString());


            registerUser();
        } else {
            Toast.makeText(RegistrationActivity.this, "As Senhas não são correspondentes", Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {

                ((EditText) view).setError(message);

            } else {
                Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_LONG).show();
            }

        }
    }
}
