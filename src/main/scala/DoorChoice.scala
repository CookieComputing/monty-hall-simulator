/**
 * Represents the choices that a participant can make when determining whether
 * they want to switch or stay on the door they picked.
 */
sealed trait DoorChoice {}

case class Stay() extends DoorChoice

case class Switch() extends DoorChoice