package com.wgmc.whattobuy.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.service.SettingsService;

/**
 * Created by proxie on 4.4.17.
 */

public class SettingsFragment extends ContentFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        ((Switch) root.findViewById(R.id.settings_shakeToCheckItems)).setChecked(
                SettingsService.getInstance().getSetting(
                        SettingsService.SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS
                ).equals("true")
        );

        ((Switch) root.findViewById(R.id.settings_shakeToCheckItems)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsService.getInstance().putSetting(SettingsService.SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS, isChecked);
                SettingsService.getInstance().notifyObservers(SettingsService.SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS);
            }
        });

        ((Switch) root.findViewById(R.id.settings_show_startup_tipps)).setChecked(
                SettingsService.getInstance().getSetting(
                        SettingsService.SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL
                ).equals("true")
        );

        ((Switch) root.findViewById(R.id.settings_show_startup_tipps)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(SettingsFragment.this.getClass().getSimpleName(), "checked state: " + isChecked);
                SettingsService.getInstance().putSetting(SettingsService.SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL, isChecked);
                SettingsService.getInstance().notifyObservers(SettingsService.SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL);
            }
        });

        return root;
    }
}
