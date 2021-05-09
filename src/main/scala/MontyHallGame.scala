import MontyHallGame.{GameState, doorPrizes, mapIndex}

import scala.util.Random

/**
 * Represents an instance of a monty hall game.
 */
case class MontyHallGame() {
  private val game = doorPrizes
  private var revealedDoor = 0

  /**
   * When given an initial choice, returns the state of game show when the host
   * would reveal a door where a goat is located
   * @param doorChoice the player's first choice
   * @return The state of the game
   */
  def pickInitialDoor(doorChoice: FirstDoorChoice): GameState = {
    val index = mapIndex(doorChoice)

    val revealedDoorIndex = revealOtherDoor(index)
    revealedDoor = revealedDoorIndex
    game
      .map(_ => Option.empty[DoorPrize])
      .updated(revealedDoorIndex, Some(Goat()))
  }

  /**
   * Returns the value of a player's choice after the host has revealed
   * @param choice the door choice that the player makes (stay or switch)
   * @param initialChoice the initial choice that the player made
   * @return The door prize from the choice they made
   */
  def applySecondChoice(choice: SecondDoorChoice, initialChoice: FirstDoorChoice): DoorPrize = {
    choice match {
      case _: Stay => game(mapIndex(initialChoice))
      case _: Switch =>
        game
          .zipWithIndex
          .filter(p => p._2 != mapIndex(initialChoice) && p._2 != revealedDoor)
          .head._1
    }
  }

  /**
   * Marks a door to be selected as a Goat door.
   */
  private def revealOtherDoor(choice: Int): Int =
    Random.shuffle(game.zipWithIndex.filter(p => p._1 match {
      case _: Goat => choice != p._2
      case _ => false
    })).head._2
}

object MontyHallGame {
  // None would represent a hidden door, while Some is a revealed door choice
  type PossibleDoorPrize = Option[DoorPrize]

  // The game state, which has 3 doors to pick from.
  type GameState = List[PossibleDoorPrize]

  /**
   * Initializes a list of door prizes for the Monty Hall problem
   */
  private def doorPrizes: List[DoorPrize] =
    Random.shuffle(List(Car(), Goat(), Goat()))

  private def mapIndex(firstDoorChoice: FirstDoorChoice) = firstDoorChoice match {
    case _: One => 0
    case _: Two => 1
    case _: Three => 2
  }
}
