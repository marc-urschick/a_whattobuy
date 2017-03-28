package com.wgmc.whattobuy.fragment;

import android.app.Fragment;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by proxie on 21.03.17.
 */

public abstract class ContentFragment extends Fragment implements Observer {
    public static final int REDIRECT_BACK_ACTION = 1;
    public static final int NO_BACK_ACTION = 2;
    public static final int MOVE_TO_PARENT_BACK_ACTION = 3;

    private ContentFragment parent;

    /**
     *
     * @return boolean true if the back action shall be redirected to the calling class
     */
    public int backAction() {
        if (getParent() == null)
            return REDIRECT_BACK_ACTION;
        return MOVE_TO_PARENT_BACK_ACTION;
    }

    public ContentFragment getParent() {
        return parent;
    }

    public void setParent(ContentFragment parent) {
        this.parent = parent;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
