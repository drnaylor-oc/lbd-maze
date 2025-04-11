package uk.co.drnaylor.lbdmaze.netty.packets.clientbound

import kotlinx.serialization.Serializable
import uk.co.drnaylor.lbdmaze.data.Direction

@Serializable
data class S2CCell(
    val id: String,
    val name: String,
    val title: String,
    val description: String,
    val availableDirections: List<Direction>
) : ClientboundPacket
