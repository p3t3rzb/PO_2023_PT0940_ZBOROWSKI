package model

fun <V> Map<Vector2D,V>.randomPosition() = this.keys.randomOrNull()

fun <V> Map<Vector2D,V>.randomFreePosition(mapSize: Vector2D): Vector2D? {
    val positions = mutableListOf<Vector2D>()

    for (x in 0..<mapSize.x) {
        for (y in 0..<mapSize.y) {
            positions.add(Vector2D(x,y))
        }
    }

    return (positions-this.keys).randomOrNull();
}