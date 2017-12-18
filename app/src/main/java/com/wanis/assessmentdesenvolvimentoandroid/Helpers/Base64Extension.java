package com.wanis.assessmentdesenvolvimentoandroid.Helpers;

import android.util.Base64;

/**
 * Created by munirwanis on 17/12/17.
 */

public class Base64Extension {
    public static String encode(String s) {

        return Base64.encodeToString(s.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

    }

    public static String decode(String s) {

        return new String(Base64.decode(s, Base64.DEFAULT));

    }
}
