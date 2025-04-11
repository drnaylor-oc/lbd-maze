package uk.co.drnaylor.lbdmaze.netty.handlers.clientbound

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame
import io.netty.handler.codec.http.websocketx.WebSocketFrame
import kotlinx.serialization.json.Json
import uk.co.drnaylor.lbdmaze.netty.packets.clientbound.S2CCell

class ClientboundWebsocketFrameHandler : SimpleChannelInboundHandler<WebSocketFrame>() {

    override fun channelRead0(ctx: ChannelHandlerContext, msg: WebSocketFrame) {
        when (msg) {
            is TextWebSocketFrame -> {
                println("[ClientboundWebsocketFrameHandler]: Received ${msg.text()}")
                // We are only expecting one json message here, but if we had multiple
                // checks would be done here to decode the right message.
                val decoded = Json.decodeFromString<S2CCell>(msg.text())
                // Pushes the message to the next handler for separation.
                ctx.fireChannelRead(decoded)
            }
            is PongWebSocketFrame -> {
                println("[ClientboundWebsocketFrameHandler]: Received pong frame. Ignoring.")
            }
            is CloseWebSocketFrame -> {
                println("[ClientboundWebsocketFrameHandler] Close frame received. Closing websocket.")
                ctx.close()
            }
        }

    }
}