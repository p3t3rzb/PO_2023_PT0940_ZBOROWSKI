package model

enum class MapDirection(internal val directionVector: Vector2D, private val directionName : String) {
    NORTH(Vector2D(0,1),"Północ"),
    EAST(Vector2D(1,0),"Wschód"),
    SOUTH( Vector2D(0,-1),"Południe"),
    WEST(Vector2D(-1,0),"Zachód");

    fun next(): MapDirection {
        return when(this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    }

    fun previous(): MapDirection {
        return when(this) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            WEST -> SOUTH
        }
    }

    override fun toString() = directionName
}