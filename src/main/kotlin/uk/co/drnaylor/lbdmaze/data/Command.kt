package uk.co.drnaylor.lbdmaze.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

object CommandSerialiser: KSerializer<Command> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("uk.co.drnaylor.lbdmaze.data.Command", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Command) {
        when (value) {
            is Direction -> encoder.encodeString("go ${value.name.lowercase(Locale.getDefault())}")
            else -> encoder.encodeString(value.toString().lowercase(Locale.getDefault()))
        }
    }

    override fun deserialize(decoder: Decoder): Command {
        return when (decoder.decodeString().lowercase(Locale.getDefault())) {
            "go up" -> Direction.UP
            "go down" -> Direction.DOWN
            "go left" -> Direction.LEFT
            "go right" -> Direction.RIGHT
            "reset" -> RESET
            else -> throw IllegalArgumentException()
        }
    }
}

@Serializable(with = CommandSerialiser::class)
sealed interface Command

object RESET : Command {
    override fun toString() = "reset"
}

