package net.ddns.scravlon.splitmefull;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Scravlon on 22-May-17.
 */

public class settingActivity extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String ROUNDOFFME ="net.ddns.scravlon.splitmefull.roundoff";
    public static final String ROUNDOFF ="net.ddns.scravlon.splitmefull.roundoff";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
        Log.d("Setting", key);
        if (key.equals("ROUNDOFF")) {
            CheckBoxPreference chkround = (CheckBoxPreference) findPreference(key);

        }

    }
}
