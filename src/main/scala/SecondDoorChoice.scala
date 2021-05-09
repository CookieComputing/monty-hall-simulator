/**
 * Represents the choices that a participant can make when determining whether
 * they want to switch or stay on the door they picked.
 */
sealed trait SecondDoorChoice {}

case class Stay() extends SecondDoorChoice

case class Switch() extends SecondDoorChoice