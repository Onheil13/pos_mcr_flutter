package com.sdsolutions.pos.pos_mcr_flutter.vpos.apipackage;

import android.graphics.Bitmap;
import android.util.Log;


import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class Print {
    private final String tag = "Print";

    public static native int Lib_CTNPrnStart();

    public static native int Lib_PrnCheckStatus();

    public static native int Lib_PrnClose();

    public static native int Lib_PrnContinuous(int i);

    public static native int Lib_PrnConventional(int i);

    public static native int Lib_PrnCutPicture(byte[] bArr);

    public static native int Lib_PrnCutPictureStr(byte[] bArr, byte[] bArr2, int i);

    public static native int Lib_PrnFeedPaper(int i);

    public static native int Lib_PrnGetFont(byte[] bArr, byte[] bArr2, byte[] bArr3);

    public static native int Lib_PrnInit();

    public static native int Lib_PrnIsCharge(int i);

    public static native int Lib_PrnLogo(byte[] bArr);

    public static native int Lib_PrnSetAlign(int i);

    public static native int Lib_PrnSetCharSpace(int i);

    public static native int Lib_PrnSetFont(byte b, byte b2, byte b3);

    public static native int Lib_PrnSetGray(int i);

    public static native int Lib_PrnSetLeftIndent(int i);

    public static native int Lib_PrnSetLeftSpace(int i);

    public static native int Lib_PrnSetLineSpace(int i);

    public static native int Lib_PrnSetSpace(byte b, byte b2);

    public static native int Lib_PrnSetSpeed(int i);

    public static native int Lib_PrnSetVoltage(int i);

    public static native int Lib_PrnStart();

    public static native int Lib_PrnStep(int i);

    public static native int Lib_PrnStr(byte[] bArr);

    public static native int Lib_SetLinPixelDis(char c);

    static {
        System.loadLibrary("PosApi");
    }

    public static int Lib_PrnStr(String str) {
        byte[] strbytes = null;
        try {
            PrintStream printStream = System.out;
            printStream.println("original string---" + str);
            strbytes = str.getBytes("UnicodeBigUnmarked");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Lib_PrnStr(strbytes);
        return 0;
    }

    public int Lib_PrnBmp(Bitmap bitmap) {
        PrinterBitmap pPrinterBmpLine = Bitmap2PrintDot(bitmap);
        int iBufferSize = pPrinterBmpLine.m_iRowBytes * pPrinterBmpLine.m_iHeight;
        byte[] byLogoBuffer = new byte[iBufferSize + 5];
        System.arraycopy(pPrinterBmpLine.m_pDotByteBuffer, 0, byLogoBuffer, 5, iBufferSize);
        byLogoBuffer[0] = (byte) (pPrinterBmpLine.m_iWidth / 256);
        byLogoBuffer[1] = (byte) (pPrinterBmpLine.m_iWidth % 256);
        byLogoBuffer[2] = (byte) (pPrinterBmpLine.m_iHeight / 256);
        byLogoBuffer[3] = (byte) (pPrinterBmpLine.m_iHeight % 256);
        int iRetCode = Lib_PrnLogo(byLogoBuffer);
        if (iRetCode != 0) {
            return iRetCode;
        }
        return iRetCode;
    }

    public int prnCutQrCode(Bitmap bitmap) {
        PrinterBitmap pPrinterBmpLine = Bitmap2PrintDot(bitmap);
        int iBufferSize = pPrinterBmpLine.m_iRowBytes * pPrinterBmpLine.m_iHeight;
        byte[] byLogoBuffer = new byte[iBufferSize + 5];
        System.arraycopy(pPrinterBmpLine.m_pDotByteBuffer, 0, byLogoBuffer, 5, iBufferSize);
        byLogoBuffer[0] = (byte) (pPrinterBmpLine.m_iWidth / 256);
        byLogoBuffer[1] = (byte) (pPrinterBmpLine.m_iWidth % 256);
        byLogoBuffer[2] = (byte) (pPrinterBmpLine.m_iHeight / 256);
        byLogoBuffer[3] = (byte) (pPrinterBmpLine.m_iHeight % 256);
        int iRetCode = Lib_PrnCutPicture(byLogoBuffer);
        if (iRetCode != 0) {
            return iRetCode;
        }
        return iRetCode;
    }

    public int prnCutQrCodeStr(Bitmap bitmap, String txt, int distance) {
        PrinterBitmap pPrinterBmpLine = Bitmap2PrintDot(bitmap);
        int iBufferSize = pPrinterBmpLine.m_iRowBytes * pPrinterBmpLine.m_iHeight;
        byte[] byLogoBuffer = new byte[iBufferSize + 5];
        System.arraycopy(pPrinterBmpLine.m_pDotByteBuffer, 0, byLogoBuffer, 5, iBufferSize);
        byLogoBuffer[0] = (byte) (pPrinterBmpLine.m_iWidth / 256);
        byLogoBuffer[1] = (byte) (pPrinterBmpLine.m_iWidth % 256);
        byLogoBuffer[2] = (byte) (pPrinterBmpLine.m_iHeight / 256);
        byLogoBuffer[3] = (byte) (pPrinterBmpLine.m_iHeight % 256);
        byte[] strbytes = null;
        try {
            PrintStream printStream = System.out;
            printStream.println("original string---" + txt);
            strbytes = txt.getBytes("UnicodeBigUnmarked");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PrintStream printStream2 = System.out;
        printStream2.println("original string---strbytes.length" + strbytes.length);
        int iRetCode = Lib_PrnCutPictureStr(byLogoBuffer, strbytes, distance);
        if (iRetCode != 0) {
            return iRetCode;
        }
        return iRetCode;
    }

    private PrinterBitmap Bitmap2PrintDot(Bitmap m_pBitmap) {
        int iH;
        int iW;
        int iBufferSize;
        int iW2 = m_pBitmap.getWidth();
        int iH2 = m_pBitmap.getHeight();
        Log.i("iW = ", Integer.toString(iW2));
        Log.i("iH = ", Integer.toString(iH2));
        int i = 8;
        int iRowBytes = (iW2 + 7) / 8;
        Log.i("iRowBytes = ", Integer.toString(iRowBytes));
        int iBufferSize2 = iRowBytes * iH2;
        Log.i("iBufferSize = ", Integer.toString(iBufferSize2));
        byte[] byBuffer = new byte[iBufferSize2];
        for (int iBufferPos = 0; iBufferPos < iBufferSize2; iBufferPos++) {
            byBuffer[iBufferPos] = 0;
        }
        int y = 0;
        while (y < iH2) {
            int x = 0;
            int x2 = 0;
            while (x2 < iRowBytes) {
                int x3 = x;
                int x4 = 0;
                while (x4 < i) {
                    x3 = (x2 * 8) + x4;
                    if (iW2 <= x3) {
                        break;
                    }
                    int iValue = m_pBitmap.getPixel(x3, y);
                    if (-16777216 == iValue) {
                        int i2 = (y * iRowBytes) + x2;
                        double d = byBuffer[i2];
                        iBufferSize = iBufferSize2;
                        iW = iW2;
                        iH = iH2;
                        double pow = Math.pow(2.0d, 7 - x4);
                        Double.isNaN(d);
                        byBuffer[i2] = (byte) (d + pow);
                    } else {
                        iW = iW2;
                        iH = iH2;
                        iBufferSize = iBufferSize2;
                    }
                    x4++;
                    iBufferSize2 = iBufferSize;
                    iW2 = iW;
                    iH2 = iH;
                    i = 8;
                }
                x = x3;
                x2++;
                iBufferSize2 = iBufferSize2;
                iW2 = iW2;
                iH2 = iH2;
                i = 8;
            }
            y++;
            i = 8;
        }
        PrinterBitmap bmp = new PrinterBitmap();
        bmp.m_pDotByteBuffer = byBuffer;
        bmp.m_iRowBytes = iRowBytes;
        bmp.m_iWidth = m_pBitmap.getWidth();
        bmp.m_iHeight = m_pBitmap.getHeight();
        return bmp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class PrinterBitmap {
        public int m_iHeight;
        public int m_iRowBytes;
        public int m_iWidth;
        public byte[] m_pDotByteBuffer;

        public PrinterBitmap() {
            this.m_pDotByteBuffer = null;
            this.m_iRowBytes = 0;
            this.m_iWidth = 0;
            this.m_iHeight = 0;
            this.m_pDotByteBuffer = null;
            this.m_iRowBytes = 0;
            this.m_iWidth = 0;
            this.m_iHeight = 0;
        }
    }
}
