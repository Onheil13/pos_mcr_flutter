package com.sdsolutions.pos.pos_mcr_flutter.vpos.apipackage;

/* loaded from: classes.dex */
public class IDCard {
    public static native int Lib_IDCardClose();

    public static native int Lib_IDCardOpen();

    public static native int Lib_IDCardRead(String[] strArr, byte[] bArr);

    public static native int Lib_IDCardRead2(String[] strArr);

    static {
        System.loadLibrary("PosApi");
    }
}
