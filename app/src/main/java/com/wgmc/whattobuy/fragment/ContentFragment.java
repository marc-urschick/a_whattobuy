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
// basic fragment that every content should extend
    // this class cannot be used without proper implementation
    // it must be the root of every content
public abstract class ContentFragment extends Fragment implements Observer {
    public static final int NO_BACK_ACTION = 2;
    public static final int MOVE_TO_PARENT_BACK_ACTION = 3;

    private List<DefaultService> observingServices = new ArrayList<>();

    // adds service to list of services that shall be observed
    protected void addObservingService(DefaultService s) {
        observingServices.add(s);
    }

    /**
     *
     * @return boolean true if the back action shall be redirected to the calling class
     */
    // default implementation of this method
    public int backAction() {
        return MOVE_TO_PARENT_BACK_ACTION;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // register this as observer to all services that shall be observed
        for (DefaultService s : observingServices) {
            s.addObserver(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // unregister this as observer from all services
        for (DefaultService s : observingServices) {
            s.deleteObserver(this);
        }
    }

    // default empty implementation of update(...)-method
    @Override
    public void update(Observable o, Object arg) {

    }
}
