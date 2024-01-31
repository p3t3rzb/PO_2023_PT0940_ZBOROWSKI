package model

import kotlin.math.max
import kotlin.math.min

data class Vector2D(internal val x: Int, internal val y: Int) {
    operator fun unaryMinus() = Vector2D(-x,-y)

    operator fun plus(other: Vector2D) = Vector2D(x+other.x,y+other.y)

    operator fun minus(other: Vector2D) = Vector2D(x-other.x,y-other.y)

    fun upperRight(other: Vector2D) = Vector2D(min(x,other.x),max(y,other.y))

    fun lowerLeft(other: Vector2D) = Vector2D(max(x,other.x),min(y,other.y))

    override fun toString() = "($x,$y)"

    operator fun compareTo(other: Vector2D): Int {
        return if(x == other.x && y == other.y) {
            0
        } else if(x > other.x && y > other.y) {
            1
        } else {
            -1
        }
    }


}

fun MapDirection.toUnitVector() = directionVector