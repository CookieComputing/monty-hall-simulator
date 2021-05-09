import org.scalacheck.Gen
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import scala.util.Random

/**
 * Property testing the monty hall problem.
 */
class MontyHallProblemTest extends AnyPropSpec with ScalaCheckPropertyChecks {
  property("always switching in a series of games should be close to 66%") {
    forAll(Gen.chooseNum(10000, 100000)) { n =>
      val simulationResult = MontyHallProblem.simulate(n)((_, _) => Switch())
      assert((simulationResult._2.toDouble / simulationResult._1.toDouble) - (2.0 / 3.0) < 1)
    }
  }

  property("always staying in a series of games should be close to 66%") {
    forAll(Gen.chooseNum(10000, 100000)) { n =>
      val simulationResult = MontyHallProblem.simulate(n)((_, _) => Stay())
      assert((simulationResult._2.toDouble / simulationResult._1.toDouble) - (1.0 / 3.0) < 1)
    }
  }

  property("switching between stay and switch in a series of games should" +
    "be close to 50%") {
    forAll(Gen.chooseNum(10000, 100000)) { n =>
      val simulationResult = MontyHallProblem.simulate(n)((_, _) => Random.shuffle(List(Stay(), Switch())).head)
      assert((simulationResult._2.toDouble / simulationResult._1.toDouble) - (1.0 / 2.0) < 1)
    }
  }
}
