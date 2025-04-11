# Learn by Doing -- Maze: Netty and Kotlin

This is a solution in Kotlin using Netty as the IO layer for https://maze.robanderson.dev/.

This solution uses Netty's event driven system with handlers. Some of the auxiliary code is a little messy, but the 
intent is there.

The interesting code for networking is all in the netty package. Specifically, it sets up a set of handlers to set
up the web socket handshake, using the standard HTTP connections and such. 

Once that's done and the handshake is complete (ClientboundWebsocketHandshakeHandler), the handler removes itself from 
the pipeline and adds the websocket frame encoder/decoder (ClientboundPacketDecoder/ServerboundPacketEncoder) **after**
the standard Websocket frame encoder/decoders Netty provides, then the handler "ClientboundResetHandler" -- which
responds to the first Json message with a reset command to ensure we start at the beginning of the maze.

When the reset handler sends the reset command, it removes itself from the pipeline, and adds the "ClientboundPacketHander"
to the pipeline, also supplying it with a solver (for now, the LeftHandSolver). The solve then sends commands back to the
socket in response to the location it sees, the previous direction it moved and the direction the solver providers.

The solver itself is a ["hand on wall"](https://en.wikipedia.org/wiki/Maze-solving_algorithm#Hand_On_Wall_Rule) algorithm,
specifically left-handed. If I can work out how those IDs encode the direction to go, then I'll create a new solver.

---

Some of the code could be more modular, but this was more an effort to get Netty working with web sockets.
https://github.com/netty/netty/blob/4.1/example/src/main/java/io/netty/example/http/websocketx/client/WebSocketClient.java
was invaluable!