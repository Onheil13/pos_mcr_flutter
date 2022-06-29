package com.sdsolutions.pos.pos_mcr_flutter.vpos.apipackage;

/* loaded from: classes.dex */
public class Scan {
    public static native int Lib_ScanClose();

    public static native int Lib_ScanOpen();

    public static native int Lib_ScanRead(short s, String[] strArr);

    static {
        System.loadLibrary("PosApi");
    }
}
