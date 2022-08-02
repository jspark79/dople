package com.dople.util;


import android.content.Context;
import android.content.SharedPreferences;


//SimpleStore.saveString(this, Config.KEY_PASSWORD, "0000");

public class SimpleStore {
    private 	static SharedPreferences m_Pref = null;

    private static void open(Context context)
    {
        if(m_Pref == null) {
            m_Pref = context.getSharedPreferences("global_store", Context.MODE_PRIVATE);
        }
    }

    public static void saveString(Context context, String key, String value)
    {
        String encKey 	= key;//Encrypt.encrypt(key);
        String encValue = value; //Encrypt.encrypt(value);

        open(context);
        SharedPreferences.Editor editor = m_Pref.edit();
        editor.putString(encKey, encValue);
        editor.commit();
    }

    public static String getString(Context context, String key)
    {
        String encKey 	= key; //Encrypt.encrypt(key);
        open(context);

        return m_Pref.getString(encKey, "");
    }
}
