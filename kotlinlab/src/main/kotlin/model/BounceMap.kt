package model

import java.util.Collections.unmodifiableList

class BounceMap(private val width: Int, private val height: Int): WorldMap, MoveValidator {
    private val mapStart = Vector2D(0,0)
    private val mapEnd = Vector2D(width-1,height-1)
    private val map: HashMap<Vector2D,Animal> = HashMap<Vector2D,Animal>()
    private val animals: List<Animal> = ArrayList<Animal>()

    override fun objectAt(position: Vector2D): Animal? = map[position]

    override fun getElements(): List<Animal> = unmodifiableList(animals)

    override fun place(animal: Animal) {
        if(!canMoveTo(animal.position)) {
            return
        }
        if(map.containsValue(animal)) {
            map.remove(map.entries.find {it.value == animal}?.key)
        }
        if(isOccupied(animal.position)) {
            var position = map.randomFreePosition(mapEnd)
            if(position != null) {
                animal.position = position
                map[position] = animal
            } else {
                position = map.randomPosition()
                if(position != null) {
                    animal.position = position
                    map.remove(position)
                    map[position] = animal
                }
            }
        }
    }

    override fun isOccupied(position: Vector2D): Boolean = map[position] != null

    override fun canMoveTo(position: Vector2D) = position >= mapStart && position <= mapEnd
}