package model

class Animal(private var orientation: MapDirection = MapDirection.NORTH, internal var position: Vector2D = Vector2D(2,2)) {

    override fun toString(): String {
        return when(orientation) {
            MapDirection.NORTH -> "^"
            MapDirection.EAST -> ">"
            MapDirection.SOUTH -> "V"
            MapDirection.WEST -> "<"
        }
    }

    fun isAt(position: Vector2D) = this.position == position

    fun move(direction: MoveDirection, validator: MoveValidator) {
        val temp: Vector2D

        when(direction) {
            MoveDirection.RIGHT -> {
                orientation = orientation.next()
            }
            MoveDirection.LEFT -> {
                orientation = orientation.previous()
            }
            MoveDirection.FORWARD -> {
                temp = position + orientation.toUnitVector()
                if(validator.canMoveTo(temp)) {
                    position = temp
                }
            }
            MoveDirection.BACKWARD -> {
                temp = position - orientation.toUnitVector()
                if(validator.canMoveTo(temp)) {
                    position = temp
                }
            }
        }


    }
}