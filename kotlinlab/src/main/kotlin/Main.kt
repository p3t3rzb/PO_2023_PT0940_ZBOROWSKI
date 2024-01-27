import model.Animal
import model.MapDirection
import model.MoveDirection
import model.Vector2D

fun main(args: Array<String>) {
    println("Hello World!")
    var test: MoveDirection = MoveDirection.FORWARD

    var test2: Vector2D  = -Vector2D(2,3)+Vector2D(5,5)

    var test3: Animal = Animal(MapDirection.NORTH,Vector2D(2,3))

    println("Solution: ${Vector2D(2,3) > Vector2D(2,2)}")
}