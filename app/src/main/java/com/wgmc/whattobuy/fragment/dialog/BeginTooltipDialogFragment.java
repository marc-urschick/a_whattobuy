package com.wgmc.whattobuy.fragment.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.wgmc.whattobuy.R;
import com.wgmc.whattobuy.activity.MainActivity;
import com.wgmc.whattobuy.service.SettingsService;

/**
 * Created by proxie on 10.4.17.
 */
// Dialog which shows Tooltips on startup that can be enabled and disabled from within here or within settings
public class BeginTooltipDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_fragment_tooltip_startup, container, false);

        root.findViewById(R.id.dialog_tooltip_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                MainActivity.tooltipOpen = false;
            }
        });

        ((CheckBox) root.findViewById(R.id.dialog_tooltip_show_on_start)).setChecked(
                SettingsService
                        .getInstance()
                        .getSetting(SettingsService.SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL)
                        .equals("true")
        );

        ((CheckBox) root.findViewById(R.id.dialog_tooltip_show_on_start)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsService
                        .getInstance()
                        .putSetting(
                                SettingsService.SETTING_SHOW_STARTUP_TOOLTIP_TUTORIAL,
                                isChecked
                        );
            }
        });

        return root;
    }
}
