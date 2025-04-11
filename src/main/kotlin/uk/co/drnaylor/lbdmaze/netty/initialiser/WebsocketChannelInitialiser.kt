package uk.co.drnaylor.lbdmaze.netty.initialiser

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpClientCodec
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler
import io.netty.handler.ssl.SslContext
import uk.co.drnaylor.lbdmaze.netty.handlers.clientbound.ClientboundWebsocketHandshakeHandler
import java.net.URI

class WebsocketChannelInitialiser(private val uri: URI, private val sslContext: SslContext?) : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        println("[WebsocketChannelInitialiser] Creating pipeline.")
        val p = ch.pipeline();
        if (sslContext != null) {
            p.addLast(sslContext.newHandler(ch.alloc(), uri.host, uri.port));
        }
        p.addLast(
            HttpClientCodec(),
            HttpObjectAggregator(8192),
            WebSocketClientCompressionHandler.INSTANCE,
            ClientboundWebsocketHandshakeHandler(uri)
        );
    }
}