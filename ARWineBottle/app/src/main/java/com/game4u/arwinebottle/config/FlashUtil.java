package com.game4u.arwinebottle.config;

import android.os.IBinder;
import android.os.IHardwareService;

import java.lang.reflect.Method;

/**
 * Created by walke.Z on 2017/11/13.
 */

public class FlashUtil  {

    /**
     * 设置闪光灯的开启和关闭
     * @param isEnable
     * @author linc
     * @date 2012-3-18
     */
    public static void setFlashlightEnabled(boolean isEnable) {
        try {
            Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, new Object[] { "hardware" });

            IHardwareService localhardwareservice = IHardwareService.Stub.asInterface(binder);
            localhardwareservice.setFlashlightEnabled(isEnable);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }



}
