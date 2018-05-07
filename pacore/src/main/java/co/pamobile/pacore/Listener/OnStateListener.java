package co.pamobile.pacore.Listener;

import java.util.HashMap;

/**
 * Created by Dev04 on 11/28/2017.
 */

public interface OnStateListener {
    void onSuccess(HashMap<String, Object> maps);
    void onFailed(HashMap<String, Object> maps);

}
