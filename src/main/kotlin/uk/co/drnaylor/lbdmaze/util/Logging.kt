package uk.co.drnaylor.lbdmaze.util

object Logging {

    private val debug = System.getProperty("lbdmaze.debug") == "true"

    fun debug(message: String) {
        if (debug) {
            println(message)
        }
    }

}