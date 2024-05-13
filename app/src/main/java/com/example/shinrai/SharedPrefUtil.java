package com.example.shinrai;
import static android.view.Gravity.apply;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class SharedPrefUtil {

    private static final String SHARED_APP_PREFERENCE_NAME = "SharedPref";
    Context cxt;
    private final SharedPreferences pref;
    private SharedPreferences.Editor mEditor;

    public SharedPrefUtil(@NonNull Context context) {
        this.pref = context.getSharedPreferences(SHARED_APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }


    @NonNull
    @Contract("_ -> new")
    public static SharedPrefUtil getInstance(Context context){
        return new SharedPrefUtil(context);
    }

    public void putString(String key, String value){
        pref.edit().putString(key,value).apply();
    }

    public void putInteger(String key,int value) {
        pref.edit().putInt(key, value).apply();
    }
    public void putBoolean(String key, boolean value){
        pref.edit().putBoolean(key, value).apply();
    }
    public String getString(String key){
    return pref.getString(key,"");
    }
    public int getInteger(String key){
        return pref.getInt(key,0);
    }
    public boolean getBoolean(String key){
        return pref.getBoolean(key,false);
    }


    public void putliststring(List<String> list){
        for( int i=0 ; i< list.size();i++){
            putString("app"+i,list.get(i));
        }
        putInteger("ListSize",list.size());
    }
    public List<String> getliststring(){
        int size = getInteger("ListSize");
        List<String> temp = new ArrayList<>();
        for( int i=0 ; i< size;i++){
          temp.add(  getString("app"+i));
        }
        return temp;
    }
}
