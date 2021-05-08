/**
 * Represents a door prize. In the original Monty Hall problem, there are only
 * two possible prizes: A car, or a goat!
 */
sealed trait DoorPrize {}

case class Car() extends DoorPrize

case class Goat() extends DoorPrize