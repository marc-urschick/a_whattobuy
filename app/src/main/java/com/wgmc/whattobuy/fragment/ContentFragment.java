package com.wgmc.whattobuy.fragment;

import android.app.Fragment;

/**
 * Created by proxie on 21.03.17.
 */

public abstract class ContentFragment extends Fragment {
    /**
     *
     * @return boolean true if the back action shall be redirected to the calling class
     */
    public abstract boolean backAction();
}
