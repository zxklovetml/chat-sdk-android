package com.braunster.chatsdk.interfaces;


import com.braunster.chatsdk.dao.BMessage;
import com.braunster.chatsdk.dao.BThread;

/**
 * Created by itzik on 6/8/2014.
 */
public interface ActivityListener {
    public void onThreadAdded(BThread thread);

    public void onMessageAdded(BMessage message);
}
