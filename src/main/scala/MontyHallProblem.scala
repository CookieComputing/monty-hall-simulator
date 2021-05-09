import MontyHallGame.GameState

import scala.annotation.tailrec
import scala.util.Random

/**
 * The monty hall problem.
 */
object MontyHallProblem {
  type CarsWon = Int
  type GoatsWon = Int

  // First element of tuple is the number of attempts
  type SimulationResult = (Int, CarsWon, GoatsWon)

  /**
   * Simulates the monty hall problem several times
   * @param trials The number of trials that will be applied
   * @return
   */
  def simulate(trials: Int)(secondDoorStrategy: ((GameState, FirstDoorChoice) => SecondDoorChoice)): SimulationResult = {
    @tailrec
    def simulateTail(trials: Int, prizes: LazyList[DoorPrize]): LazyList[DoorPrize] = {
      if (trials <= 0) prizes
      else {
        simulateTail(trials-1, LazyList.cons(participate(secondDoorStrategy), prizes))
      }
    }
    simulateTail(trials, LazyList.empty[DoorPrize]).foldLeft((0, 0, 0)) {
      case (acc, prize) =>
          prize match {
            case _: Car => (acc._1 + 1, acc._2 + 1, acc._3)
            case _: Goat => (acc._1 + 1, acc._2, acc._3 + 1)
          }
    }
  }

  /**
   * Participates in a Monty Hall game and returns the result of the game-show.
   * @param secondDoorStrategy The strategy that is used to determine what
   *                           door to pick.
   * @return The result of playing the monty hall game
   */
  def participate(secondDoorStrategy: ((GameState, FirstDoorChoice) => SecondDoorChoice)): DoorPrize = {
    val montyHallGame = MontyHallGame()

    val firstDoorChoice = Random.shuffle(List(One(), Two(), Three())).head
    val gameState = montyHallGame.pickInitialDoor(firstDoorChoice)

    montyHallGame.applySecondChoice(secondDoorStrategy(gameState, firstDoorChoice), firstDoorChoice)
  }

  def main(args: Array[String]): Unit = {
    def printTrialResults(simulationResult: SimulationResult): Unit =
      println(s"num trials: ${simulationResult._1}, cars: ${simulationResult._2}, dogs: ${simulationResult._3}, % cars: ${simulationResult._2.toDouble / simulationResult._1.toDouble}")
    val trials = 100000
    println("always switch")
    printTrialResults(simulate(trials)((_, _) => Switch()))
    println()
    println("always stay")
    printTrialResults(simulate(trials)((_, _) => Stay()))
    println()
    println("50/50")
    printTrialResults(simulate(trials)((_, _) => Random.shuffle(List(Stay(), Switch())).head))
  }
}
