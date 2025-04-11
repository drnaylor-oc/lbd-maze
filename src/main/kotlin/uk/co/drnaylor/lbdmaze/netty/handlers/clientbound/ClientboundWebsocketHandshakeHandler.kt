package uk.co.drnaylor.lbdmaze.netty.handlers.clientbound

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPipeline
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.DefaultHttpHeaders
import io.netty.handler.codec.http.FullHttpResponse
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException
import io.netty.handler.codec.http.websocketx.WebSocketVersion
import uk.co.drnaylor.lbdmaze.netty.handlers.serverbound.ServerboundPacketEncoder
import uk.co.drnaylor.lbdmaze.solver.LeftHandSolver
import java.net.URI

class ClientboundWebsocketHandshakeHandler(uri: URI) : SimpleChannelInboundHandler<FullHttpResponse>() {

    private val handshaker = WebSocketClientHandshakerFactory.newHandshaker(
        uri, WebSocketVersion.V13, null, true, DefaultHttpHeaders()
    )

    override fun channelActive(ctx: ChannelHandlerContext) {
        println("[Handshake] Starting handshake")
        handshaker.handshake(ctx.channel())
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: FullHttpResponse) {
        val ch: Channel = ctx.channel()
        try {
            handshaker.finishHandshake(ch, msg)
            println("[Handshake] Handshake complete, switching to frame handling phase")
            switchToSocketPhase(ctx.pipeline())
        } catch (e: WebSocketHandshakeException) {
            println("[Handshake] WebSocket Client failed to connect")
            ch.close()
        }
    }

    private fun switchToSocketPhase(ctx: ChannelPipeline) {
        ctx.remove(this)
        ctx.addLast(ServerboundPacketEncoder())
        ctx.addLast(ClientboundWebsocketFrameHandler())
        ctx.addLast(ClientboundPacketHandler(LeftHandSolver())) //TODO, make this configurable?
    }
}