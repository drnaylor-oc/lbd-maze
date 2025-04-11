package uk.co.drnaylor.lbdmaze.netty.handlers.serverbound

import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame
import kotlinx.serialization.json.Json
import uk.co.drnaylor.lbdmaze.netty.packets.serverbound.C2SCommand
import uk.co.drnaylor.lbdmaze.netty.packets.serverbound.ServerboundPacket

class ServerboundPacketEncoder : MessageToMessageEncoder<ServerboundPacket>() {

    override fun encode(ctx: ChannelHandlerContext, msg: ServerboundPacket, out: MutableList<Any>) {
        when(msg) {
            is C2SCommand -> {
                println("[ServerboundPacketEncoder] Encoding: ${msg.command}")
                val encoded = Json.encodeToString(msg)
                println("[ServerboundPacketEncoder] Encoded: $encoded")
                out.add(TextWebSocketFrame(encoded))
            }
        }
    }


}