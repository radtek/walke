package com.game4u.arwinebottle.ar;

import android.content.ContextWrapper;

import com.unity3d.player.UnityPlayer;

/**
 * Created by walke.Z on 2017/11/17.
 */

public class MyUnityPlayer extends UnityPlayer {
    public MyUnityPlayer(ContextWrapper contextWrapper) {
        super(contextWrapper);
    }

    @Override
    protected void kill() {
        //super.kill();
    }
}
