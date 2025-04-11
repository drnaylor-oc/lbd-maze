package uk.co.drnaylor.lbdmaze.netty.handlers.serverbound

import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame
import kotlinx.serialization.json.Json
import uk.co.drnaylor.lbdmaze.netty.packets.serverbound.C2SCommand
import uk.co.drnaylor.lbdmaze.netty.packets.serverbound.ServerboundPacket
import uk.co.drnaylor.lbdmaze.util.Logging

class ServerboundPacketEncoder : MessageToMessageEncoder<ServerboundPacket>() {

    override fun encode(ctx: ChannelHandlerContext, msg: ServerboundPacket, out: MutableList<Any>) {
        when(msg) {
            is C2SCommand -> {
                Logging.debug("[ServerboundPacketEncoder] Encoding: ${msg.command}")
                val encoded = Json.encodeToString(msg)
                Logging.debug("[ServerboundPacketEncoder] Encoded: $encoded")
                out.add(TextWebSocketFrame(encoded))
            }
        }
    }


}