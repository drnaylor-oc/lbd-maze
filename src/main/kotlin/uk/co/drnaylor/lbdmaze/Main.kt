package uk.co.drnaylor.lbdmaze

import kotlinx.coroutines.runBlocking
import uk.co.drnaylor.lbdmaze.netty.WebsocketClient

fun main(args: Array<String>) {
    val id: String = if (args.isEmpty()) {
        "D32TSM4HQC4SA"
    } else {
        args[0]
    }

    println("Starting maze solution for: ${id}")
    runBlocking {
        WebsocketClient.create("wss://maze.robanderson.dev/ws/${id.uppercase()}").kickoff()
    }
}