package com.sdsolutions.pos.pos_mcr_flutter.vpos.apipackage;

/* loaded from: classes.dex */
public class APDU_SEND {
    public byte[] Command;
    public byte[] DataIn;

    /* renamed from: Lc */
    public short f46Lc;

    /* renamed from: Le */
    public short f47Le;

    public APDU_SEND(byte[] Command, short Lc, byte[] DataIn, short Le) {
        this.Command = null;
        this.DataIn = null;
        this.Command = new byte[Command.length];
        this.DataIn = new byte[DataIn.length];
        this.Command = Command;
        this.f46Lc = Lc;
        this.DataIn = DataIn;
        this.f47Le = Le;
    }

    public byte[] getBytes() {
        byte[] buf = new byte[520];
        System.arraycopy(this.Command, 0, buf, 0, this.Command.length);
        buf[4] = (byte) (this.f46Lc / 256);
        buf[5] = (byte) (this.f46Lc % 256);
        System.arraycopy(this.DataIn, 0, buf, 6, this.DataIn.length);
        buf[518] = (byte) (this.f47Le / 256);
        buf[519] = (byte) (this.f47Le % 256);
        return buf;
    }
}
