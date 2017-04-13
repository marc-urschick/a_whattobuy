package com.wgmc.whattobuy.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wgmc.whattobuy.service.DefaultService;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by proxie on 21.03.17.
 */

public abstract class ContentFragment extends Fragment implements Observer {
    public static final int NO_BACK_ACTION = 2;
    public static final int MOVE_TO_PARENT_BACK_ACTION = 3;

    private List<DefaultService> observingServices = new ArrayList<>();

    protected void addObservingService(DefaultService s) {
        observingServices.add(s);
    }

    /**
     *
     * @return boolean true if the back action shall be redirected to the calling class
     */
    public int backAction() {
        return MOVE_TO_PARENT_BACK_ACTION;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        for (DefaultService s : observingServices) {
            Log.d(getClass().getSimpleName() + "-> adding to observers", "s: " + s + ", instof" + s.getClass().getSimpleName());
            s.addObserver(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (DefaultService s : observingServices) {
            s.deleteObserver(this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
