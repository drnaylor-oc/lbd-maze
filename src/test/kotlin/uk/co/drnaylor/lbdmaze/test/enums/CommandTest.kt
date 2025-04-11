package uk.co.drnaylor.lbdmaze.test.enums

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import uk.co.drnaylor.lbdmaze.data.CommandSerialiser
import uk.co.drnaylor.lbdmaze.data.Direction
import uk.co.drnaylor.lbdmaze.data.Direction.Companion.reverse
import uk.co.drnaylor.lbdmaze.data.DirectionsSerialiser
import uk.co.drnaylor.lbdmaze.data.RESET
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandTest {

    @Test
    fun testSerialisation() {
        assertEquals(Json.encodeToJsonElement(CommandSerialiser, Direction.UP), JsonPrimitive("go up"))
        assertEquals(Json.encodeToJsonElement(CommandSerialiser, Direction.DOWN), JsonPrimitive("go down"))
        assertEquals(Json.encodeToJsonElement(CommandSerialiser, Direction.LEFT), JsonPrimitive("go left"))
        assertEquals(Json.encodeToJsonElement(CommandSerialiser, Direction.RIGHT), JsonPrimitive("go right"))
        assertEquals(Json.encodeToJsonElement(CommandSerialiser, RESET), JsonPrimitive("go right"))
    }

    @Test
    fun testDeserialization() {
        assertEquals(Json.decodeFromJsonElement(CommandSerialiser, JsonPrimitive("go up")), Direction.UP)
        assertEquals(Json.decodeFromJsonElement(CommandSerialiser, JsonPrimitive("go down")), Direction.DOWN)
        assertEquals(Json.decodeFromJsonElement(CommandSerialiser, JsonPrimitive("go left")), Direction.LEFT)
        assertEquals(Json.decodeFromJsonElement(CommandSerialiser, JsonPrimitive("go right")), Direction.RIGHT)
        assertEquals(Json.decodeFromJsonElement(CommandSerialiser, JsonPrimitive("reset")), RESET)
    }
}