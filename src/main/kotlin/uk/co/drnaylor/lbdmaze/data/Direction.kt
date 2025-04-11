package uk.co.drnaylor.lbdmaze.data

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

object DirectionsSerialiser: KSerializer<Direction> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("uk.co.drnaylor.lbdmaze.enums.Direction", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Direction) {
        encoder.encodeString(value.name.lowercase(Locale.getDefault()))
    }

    override fun deserialize(decoder: Decoder): Direction {
        return when (decoder.decodeString().lowercase(Locale.getDefault())) {
            "up" -> Direction.UP
            "down" -> Direction.DOWN
            "left" -> Direction.LEFT
            "right" -> Direction.RIGHT
            else -> throw IllegalArgumentException()
        }
    }
}

@Serializable(with = DirectionsSerialiser::class)
enum class Direction : Command {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    companion object {
        fun Direction.reverse(): Direction {
            return when (this) {
                UP -> DOWN
                DOWN -> UP
                LEFT -> RIGHT
                RIGHT -> LEFT
            }
        }

        fun Direction.turnLeft(): Direction {
            return when (this) {
                UP -> RIGHT
                RIGHT -> DOWN
                DOWN -> LEFT
                LEFT -> UP
            }
        }
    }
}

