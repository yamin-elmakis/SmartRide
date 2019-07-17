package com.example.smartride.widgets

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.smartride.R
import com.example.smartride.utils.changed
import kotlinx.android.synthetic.main.view_time_counter.view.*
import lib.yamin.easylog.EasyLog

class TimeCounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var listener: TimerCallbacks? = null
    private var currentMillis: Long? = null

    private var counter: CountDownTimer? = null
    private var timeData: TimerData? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_time_counter, this)
    }

    fun setTime(time: Long) {
//        if (time - System.currentTimeMillis() < 1000) {
//            text = endMessage
//            return
//        }

        stopTimer()
        initTimer(time)
        counter?.start()
    }

    private fun initTimer(time: Long) {
        counter = object : CountDownTimer(time - System.currentTimeMillis(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                post {

                    currentMillis = millisUntilFinished
                    listener?.onTick(currentMillis)

                    val newTimeData = millisUntilFinished.toTimerData()
                    newTimeData.changed(timeData, { hour }, action = {
                        counterHr.animateSetText("%02d".format(it))
                    })
                    newTimeData.changed(timeData, { minute }, action = {
                        counterMin.animateSetText("%02d".format(it))
                    })
                    newTimeData.changed(timeData, { second }, action = {
                        counterSec.animateSetText("%02d".format(it))
                    })
                    timeData = newTimeData
                }
            }

            override fun onFinish() {
                EasyLog.e()
//                text = endMessage
                currentMillis = 0
                listener?.onTick(0)
            }
        }
    }

    fun stopTimer() {
        counter?.let {
            it.cancel()
            counter = null
            timeData = null
        }
    }

    override fun onDetachedFromWindow() {
        stopTimer()
        super.onDetachedFromWindow()
    }

    fun getTime(): Long? {
        return currentMillis
    }

    fun setListener(listener: TimerCallbacks?) {
        this.listener = listener
    }

    interface TimerCallbacks {
        fun onTick(currentMillis: Long?)
    }
}

data class TimerData(val hour: Int = 0, val minute: Int = 0, val second: Int = 0)

fun Long.toTimerData(): TimerData {
    if (this <= 0) {
        return TimerData()
    }
    var diff = this
    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60

    val hours = diff / hoursInMilli
    diff %= hoursInMilli
    val minutes = (diff / minutesInMilli).toInt()
    diff %= minutesInMilli
    val seconds = (diff / secondsInMilli).toInt()

    return TimerData(
        hour = hours.toInt(),
        minute = minutes,
        second = seconds
    )
}