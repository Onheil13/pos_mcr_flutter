package com.sdsolutions.pos.pos_mcr_flutter.vpos.apipackage;

/* loaded from: classes.dex */
public class Key {
    public static native int Lib_KbCheck();

    public static native int Lib_KbFlush();

    public static native int Lib_KbGetKey();

    static {
        System.loadLibrary("PosApi");
    }
}
