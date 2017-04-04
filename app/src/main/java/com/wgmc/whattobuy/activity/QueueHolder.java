package com.wgmc.whattobuy.activity;

import com.wgmc.whattobuy.fragment.ContentFragment;
import com.wgmc.whattobuy.fragment.MainFragment;

import java.util.Stack;

/**
 * Created by proxie on 1.4.17.
 */

public class QueueHolder {
    public static final Stack<ContentFragment> contentQueue = new Stack<>();
    public static ContentFragment viewingFragment = new MainFragment();
}
