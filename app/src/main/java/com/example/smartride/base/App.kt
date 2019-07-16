package com.example.smartride.base

import android.app.Application
import com.example.smartride.BuildConfig
import lib.yamin.easylog.EasyLog
import lib.yamin.easylog.EasyLogFormatter
import java.util.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        EasyLog.showLogs(BuildConfig.DEBUG)
        EasyLog.setTag("MobilityClub")
        EasyLog.setFormatter(object : EasyLogFormatter() {
            override fun format(classname: String, methodName: String, lineNumber: Int): String {
                return String.format(Locale.getDefault(), "%s.%s:[%d] => ", classname, methodName, lineNumber)
            }
        })

        EasyLog.e()
    }
}