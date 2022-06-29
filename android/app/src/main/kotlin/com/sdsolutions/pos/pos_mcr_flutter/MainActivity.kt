package com.sdsolutions.pos.pos_mcr_flutter

import android.os.Bundle
import android.os.Message
import com.sdsolutions.pos.pos_mcr_flutter.vpos.apipackage.PosApiHelper
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import java.util.*

class MainActivity : FlutterActivity() {

    var track1 = ByteArray(250)
    var track2 = ByteArray(250)
    var track3 = ByteArray(250)

    private var RESULT_CODE = 0
    var isQuit = false
    var isOpen = false
    var ret = -1

    var posApiHelper: PosApiHelper = PosApiHelper.getInstance()
    var m_MSRThread: MSR_Thread? = null

    //Flutter
    private var eventSink: EventChannel.EventSink? = null
    private val METHOD_CHANNEL: String = "pos_method_channel"
    private val EVENT_CHANNEL: String = "pos_event_channel"

    @Override
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            METHOD_CHANNEL
        ).setMethodCallHandler { call, result ->

            if (call.method.equals("initSDK")) {
                onInitMCRSDK()
                result.success(true)
            }else if(call.method.equals("endSDK")){
                onDestroyMCRSDK()
                result.success(true)
            }
            else{
                eventSink?.success("Hello")
            }

        }

        EventChannel(flutterEngine.dartExecutor.binaryMessenger, EVENT_CHANNEL).setStreamHandler(
            object : EventChannel.StreamHandler{
                override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                    eventSink = events
                }

                override fun onCancel(arguments: Any?) {
                    eventSink = null
                }

            }
        )
    }

    private fun onInitMCRSDK() {
         try {

            isOpen = false
            isQuit = false

            m_MSRThread = MSR_Thread()
            m_MSRThread!!.start()

        } catch (e: Exception) {
            e.message.toString()
        }
    }

    private fun onDestroyMCRSDK() {
         try {
            isOpen = false
            isQuit = true

            posApiHelper.McrClose()
            m_MSRThread!!.interrupt()

        } catch (e: Exception) {
            e.message.toString()
        }
    }

    inner class MSR_Thread : Thread() {
        val isThreadFinished = false
        override fun run() {
            synchronized(this) {
                val reset1: Int = posApiHelper.McrOpen()
                if (reset1 == 0) {
                    eventSink?.success("Please swipe your card!")
                    isOpen = true
                } else {
                    eventSink?.success("Unknown Error has occurred!")
                }

                while (!isQuit && isOpen) {
                    var temp = -1
                    posApiHelper.McrOpen()
                    while (temp != 0 && !isQuit) {
                        try {
                            temp = posApiHelper.McrCheck()
                            sleep(200L)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    if (!isQuit) {
                        Arrays.fill(track1, 0.toByte())
                        Arrays.fill(track2, 0.toByte())
                        Arrays.fill(track3, 0.toByte())
                        ret = posApiHelper.McrRead(
                            0.toByte(),
                            0.toByte(),
                            track1,
                            track2,
                            track3
                        )
                        if (ret > 0) {
                            RESULT_CODE = 0
                            val msg3 = Message()
                            val b = Bundle()
                            var string = ""
                            if (ret <= 7) {
                                if (ret and 1 == 1) {
                                    string =
                                        "track1:" + String(track1).trim { it <= ' ' }
                                }
                                if (ret and 2 == 2) {
                                    string =
                                        """
                                    $string

                                    track2:${String(track2).trim { it <= ' ' }}
                                    """.trimIndent()
                                }
                                if (ret and 4 == 4) {
                                    string =
                                        """
                                    $string

                                    track3:${String(track3).trim { it <= ' ' }}
                                    """.trimIndent()
                                }
                            } else {
                                RESULT_CODE = -1
                                string = "Lib_MsrRead check data error"
                            }
                            eventSink?.success(string)
                            posApiHelper.SysBeep()
                        } else {
                            eventSink?.success("Lib_MsrRead fail")
                        }
                    } else {
                        return
                    }
                }
            }
        }
    }

}
