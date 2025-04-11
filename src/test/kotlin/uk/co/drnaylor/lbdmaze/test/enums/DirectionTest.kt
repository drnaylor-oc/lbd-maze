package uk.co.drnaylor.lbdmaze.test.enums

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import uk.co.drnaylor.lbdmaze.data.Direction
import uk.co.drnaylor.lbdmaze.data.Direction.Companion.reverse
import uk.co.drnaylor.lbdmaze.data.DirectionsSerialiser
import kotlin.test.Test
import kotlin.test.assertEquals

class DirectionTest {

    @Test
    fun testReverse() {
        assertEquals(Direction.UP.reverse(), Direction.DOWN)
        assertEquals(Direction.DOWN.reverse(), Direction.UP)
        assertEquals(Direction.LEFT.reverse(), Direction.RIGHT)
        assertEquals(Direction.RIGHT.reverse(), Direction.LEFT)
    }

    @Test
    fun testSerialisation() {
        assertEquals(Json.encodeToJsonElement(DirectionsSerialiser, Direction.UP), JsonPrimitive("up"))
        assertEquals(Json.encodeToJsonElement(DirectionsSerialiser, Direction.DOWN), JsonPrimitive("down"))
        assertEquals(Json.encodeToJsonElement(DirectionsSerialiser, Direction.LEFT), JsonPrimitive("left"))
        assertEquals(Json.encodeToJsonElement(DirectionsSerialiser, Direction.RIGHT), JsonPrimitive("right"))
    }

    @Test
    fun testDeserialization() {
        assertEquals(Json.decodeFromJsonElement(DirectionsSerialiser, JsonPrimitive("up")), Direction.UP)
        assertEquals(Json.decodeFromJsonElement(DirectionsSerialiser, JsonPrimitive("down")), Direction.DOWN)
        assertEquals(Json.decodeFromJsonElement(DirectionsSerialiser, JsonPrimitive("left")), Direction.LEFT)
        assertEquals(Json.decodeFromJsonElement(DirectionsSerialiser, JsonPrimitive("right")), Direction.RIGHT)
    }
}