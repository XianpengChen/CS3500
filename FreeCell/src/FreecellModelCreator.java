

/**
 * Created by 28609 on 2/15/2017.
 * the FreecellModelCreator: define the two enums of game type and define a static method to create
 * A freecellModel according to given GameType.
 */
public class FreecellModelCreator {

  /**
   * define enum of two game type: single or multi.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE;

  }

  /**
   * a static factory method to create a FreeCellModel according to the given gameType.
   * @param type given the GameType.
   * @return an FreeCellModel.
   */
  public static FreecellModel create(GameType type) {
    if (type == GameType.SINGLEMOVE) {
      return new FreecellModel();
    }
    else {
      return new FreeCellModelMulty();
    }
  }
}
