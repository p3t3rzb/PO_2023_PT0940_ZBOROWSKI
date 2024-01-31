package model

interface WorldMap {
    fun place(animal: Animal)

    fun isOccupied(position: Vector2D): Boolean

    fun objectAt(position: Vector2D): Animal?

    fun getElements(): List<Animal>
}