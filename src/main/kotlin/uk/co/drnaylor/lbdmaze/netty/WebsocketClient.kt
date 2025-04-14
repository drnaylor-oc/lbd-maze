package uk.co.drnaylor.lbdmaze.netty

import io.netty.bootstrap.Bootstrap
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uk.co.drnaylor.lbdmaze.netty.initialiser.WebsocketChannelInitialiser
import uk.co.drnaylor.lbdmaze.util.Logging
import java.net.URI


class WebsocketClient private constructor(private val uri: URI, sslContext: SslContext?) {

    private val eventGroup: EventLoopGroup = NioEventLoopGroup()
    private val bootstrap = Bootstrap()
        .group(eventGroup)
        .channel(NioSocketChannel::class.java)
        .handler(WebsocketChannelInitialiser(uri, sslContext));

    suspend fun kickoff(): Unit = coroutineScope {
        // Once this is connected, the system will send packets back and forth as appropriate until it's done.
        Logging.debug("[WebsocketClient] Attempting to connect to $uri")
        val channel = bootstrap.connect(uri.host, uri.port).sync().channel()

        Logging.debug("[WebsocketClient] Channel open.")
        Logging.debug("[WebsocketClient] Handlers in pipeline: ${channel.pipeline().names()}")
        launch {
            while (channel.isActive) {
                delay(500)
            }
            eventGroup.shutdownGracefully().awaitUninterruptibly()
            Logging.debug("[WebsocketClient] Channel closed.")
        }
    }

    companion object {

        fun create(stringURI: String): WebsocketClient {
            val uri = createURI(stringURI)
            val sslCtx = createContext(uri)
            return WebsocketClient(uri, sslCtx)
        }

        private fun createURI(stringURI: String): URI {
            // https://github.com/netty/netty/blob/4.1/example/src/main/java/io/netty/example/http/websocketx/client/WebSocketClient.java
            val uri = URI(stringURI)
            val scheme = if (uri.scheme == null) "ws" else uri.scheme
            if (!"ws".equals(scheme, ignoreCase = true) && !"wss".equals(scheme, ignoreCase = true)) {
                throw IllegalArgumentException("Only WS(S) is supported.")
            }

            val host = if (uri.host == null) "127.0.0.1" else uri.host
            val port = if (uri.port == -1) {
                // we know we have either ws or wss at this point.
                if ("wss".equals(scheme, ignoreCase = true)) 443 else 80
            } else {
                uri.port
            }

            return URI(scheme, uri.userInfo, host, port, uri.path, uri.query, uri.fragment)
        }

        private fun createContext(uri: URI): SslContext? {
            return if ("wss".equals(uri.scheme, ignoreCase = true)) {
                SslContextBuilder.forClient().build()
            } else {
                null
            }
        }
    }



}