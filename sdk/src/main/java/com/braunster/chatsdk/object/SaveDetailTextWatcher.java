package com.braunster.chatsdk.object;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import com.braunster.chatsdk.dao.BMetadata;
import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.network.BNetworkManager;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by braunster on 04/11/14.
 *
 *
 * This class was made to make it easy to listen to text change then save them to the right user metadata.
 * Also this class update the user in the server.
 *
 * Currently only for String meta data.
 */
public class SaveDetailTextWatcher implements TextWatcher {

    public static final long INDEX_DELAY_DEFAULT = 500;
    private long indexDelay = INDEX_DELAY_DEFAULT;

    private String metaKey;

    /** Contain the string that was last typed.*/
    private Editable editable;

    public SaveDetailTextWatcher(long indexDelay, String metaKey) {
        this.indexDelay = indexDelay;
        this.metaKey = metaKey;
    }

    public SaveDetailTextWatcher(String metaKey) {
        this.metaKey = metaKey;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        editable = s;
        handler.removeCallbacks(indexRunnable);
        handler.postDelayed(indexRunnable, INDEX_DELAY_DEFAULT);
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    private Runnable indexRunnable = new Runnable() {
        @Override
        public void run() {
            if (StringUtils.isBlank(editable.toString()))
                return;

            BUser user = BNetworkManager.sharedManager().getNetworkAdapter().currentUser();
            BMetadata metadata = user.getMetadataForKey(metaKey, BMetadata.Type.STRING);

            if (StringUtils.isNotBlank( metadata.getValue() ) && metadata.getValue().equals(editable.toString()))
                return;

            user.setMetadataString(metaKey, editable.toString());
            BNetworkManager.sharedManager().getNetworkAdapter().pushUserWithCallback(null);
        }
    };

}
