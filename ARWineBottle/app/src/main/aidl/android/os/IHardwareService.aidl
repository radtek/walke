// IHardwareService.aidl
package android.os;

// Declare any non-default types here with import statements
/** {@hide} */
interface IHardwareService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    // obsolete flashlight support
    boolean getFlashlightEnabled();
    void setFlashlightEnabled(boolean on);

}
