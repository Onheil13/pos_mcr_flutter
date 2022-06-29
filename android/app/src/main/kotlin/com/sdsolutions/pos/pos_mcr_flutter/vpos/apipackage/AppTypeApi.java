package com.sdsolutions.pos.pos_mcr_flutter.vpos.apipackage;

import android.content.Context;

/* loaded from: classes.dex */
public class AppTypeApi {
    public static native int initCxt(Context context);

    public static native int showAppWin(int i, byte[] bArr);

    static {
        System.loadLibrary("PosApi");
    }
}
