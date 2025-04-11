package uk.co.drnaylor.lbdmaze.util

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object Timer {

    private var startTime: Long = 0

    fun start() {
        startTime = System.currentTimeMillis()
    }

    fun since(): Duration = (System.currentTimeMillis() - startTime).milliseconds

}