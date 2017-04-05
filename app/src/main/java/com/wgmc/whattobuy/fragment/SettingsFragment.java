package com.wgmc.whattobuy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

        ((Switch) root.findViewById(R.id.settings_shakeToCheckItems)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsService.getInstance().putSetting(SettingsService.SETTING_ENABLE_SHAKE_TO_CHECK_ITEMS, isChecked);
            }
        });

        return root;
    }
}
