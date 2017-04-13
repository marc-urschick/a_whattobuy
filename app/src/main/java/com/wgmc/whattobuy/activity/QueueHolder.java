package com.wgmc.whattobuy.activity;

import com.wgmc.whattobuy.fragment.ContentFragment;
import com.wgmc.whattobuy.fragment.MainFragment;

import java.util.Stack;

/**
 * Created by proxie on 1.4.17.
 */

public class QueueHolder {
    // queue of contents that are displayed on the screen
    // this field holds the order of the contents how the user clicked on it
    public static final Stack<ContentFragment> contentQueue = new Stack<>();

    // content that is visible and selected and the act
    public static ContentFragment viewingFragment = new MainFragment();
}
