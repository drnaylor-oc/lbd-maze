package uk.co.drnaylor.lbdmaze.netty.handlers.clientbound

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame
import uk.co.drnaylor.lbdmaze.data.Command
import uk.co.drnaylor.lbdmaze.data.Direction
import uk.co.drnaylor.lbdmaze.data.Direction.Companion.reverse
import uk.co.drnaylor.lbdmaze.data.RESET
import uk.co.drnaylor.lbdmaze.netty.packets.clientbound.ClientboundPacket
import uk.co.drnaylor.lbdmaze.netty.packets.clientbound.S2CCell
import uk.co.drnaylor.lbdmaze.netty.packets.serverbound.C2SCommand
import uk.co.drnaylor.lbdmaze.solver.MazeSolver

class ClientboundPacketHandler(private val solver: MazeSolver) : SimpleChannelInboundHandler<ClientboundPacket>() {
    private var previousDirection: Command? = null

    override fun channelRead0(ctx: ChannelHandlerContext, msg: ClientboundPacket) {
        val p: Direction? = previousDirection as? Direction
        val prevDirectionName = p?.name ?: "start"

        if (previousDirection == null) {
            // we need to reset
            println("[ClientboundPacketHandler] Resetting puzzle")
            ctx.writeAndFlush(C2SCommand(RESET))
            previousDirection = RESET
        } else {
            // connection logic here.
            when (msg) {
                is S2CCell -> { // when guards are experimental so not using them for now.
                    if (msg.availableDirections.isEmpty()) {
                        // we found it, log that and close the connection
                        println("Found the dream job: ${msg.title}, description: ${msg.description}, ID: ${msg.id}")
                        ctx.writeAndFlush(CloseWebSocketFrame())
                        ctx.close()
                    } else {
                        println("Available directions: ${msg.availableDirections.joinToString(separator = ", ") { it.name }}, arrived from: ${prevDirectionName}, ID: ${msg.id}")

                        // select direction
                        val nextDirection = solver.nextStep(
                            msg.id,
                            p?.reverse() ?: Direction.DOWN,
                            msg.availableDirections
                        )

                        println("Moving in direction: ${nextDirection.name}")

                        // issue command
                        ctx.writeAndFlush(C2SCommand(nextDirection))
                        previousDirection = nextDirection
                    }
                }
            }
        }
    }
}