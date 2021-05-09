/**
 * Represents the initial door choice that a player can make
 */
sealed trait FirstDoorChoice {}

case class One() extends FirstDoorChoice

case class Two() extends FirstDoorChoice

case class Three() extends FirstDoorChoice