package uk.co.drnaylor.lbdmaze.netty.handlers.clientbound

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import uk.co.drnaylor.lbdmaze.data.RESET
import uk.co.drnaylor.lbdmaze.netty.packets.clientbound.ClientboundPacket
import uk.co.drnaylor.lbdmaze.netty.packets.serverbound.C2SCommand
import uk.co.drnaylor.lbdmaze.solver.LeftHandSolver
import uk.co.drnaylor.lbdmaze.util.Logging
import uk.co.drnaylor.lbdmaze.util.Timer

class ClientboundResetHandler : SimpleChannelInboundHandler<ClientboundPacket>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: ClientboundPacket) {
        // we need to reset
        Logging.debug("[ClientboundPacketHandler] Resetting puzzle")
        ctx.writeAndFlush(C2SCommand(RESET))
        Logging.debug("[ClientboundPacketHandler] Switching to solve phase")
        ctx.pipeline().remove(this)
        ctx.pipeline().addLast(ClientboundPacketHandler(LeftHandSolver()))
        // start the timer now
        Timer.start()
    }
}