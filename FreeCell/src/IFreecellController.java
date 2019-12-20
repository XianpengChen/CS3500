import java.util.List;



/**
 * Created by 28609 on 2/5/2017.
 */

public interface IFreecellController<CardType> {

  /**
   * This method should start a new game of Freecell using the provided model,
   * number of cascade and open piles and the provided deck.
   * If "shuffle" is set to false the deck must be used as-is,
   * else the deck should be shuffled.
   * It should throw an IllegalStateException only if,
   * the controller has not been initialized properly to receive input and transmit output.
   * The nature of input/output will be an implementation detail.
   * @param deck given a list of cards as deck.
   * @param model given a freecell model.
   * @param numCascades given the number of cascades piles.
   * @param numOpens given the number of opens piles.
   * @param shuffle shuffle the deck or not.
   */
  void playGame(List<CardType> deck, FreecellOperations<CardType> model, int numCascades,
                int numOpens, boolean shuffle);

}
