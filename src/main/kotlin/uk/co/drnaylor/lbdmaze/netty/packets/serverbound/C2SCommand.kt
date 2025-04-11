package uk.co.drnaylor.lbdmaze.netty.packets.serverbound

import kotlinx.serialization.Serializable
import uk.co.drnaylor.lbdmaze.data.Command

@Serializable
data class C2SCommand(val command: Command) : ServerboundPacket
