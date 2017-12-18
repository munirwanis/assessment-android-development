package com.wanis.assessmentdesenvolvimentoandroid.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by munirwanis on 17/12/17.
 */

public class AndroidPreferences {
    private Context context;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "ProjetoAndroid.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificarUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";

    public AndroidPreferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }


    public void saveUserPreferences(String userId, String userName) {
        editor.putString(CHAVE_IDENTIFICADOR, userId);
        editor.putString(CHAVE_NOME, userName);
        editor.commit();
    }

    public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNome() {
        return preferences.getString(CHAVE_NOME, null);
    }
}
