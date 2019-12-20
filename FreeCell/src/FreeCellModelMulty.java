import java.util.List;



/**
 * Created by 28609 on 2/15/2017.
 * the FreeCellModel that allows single move and multi-move.
 */
public class FreeCellModelMulty extends FreecellModel {


  /**
   * to calculate the max number of cards that can be move at one time.
   * @return an int.
   */
  protected int maxMovable() {
    int n = 0;/* to represent the number of emtpy open piles.*/
    int k = 0;/* to represent the number of emty cascade piles.*/
    for (int i = 0; i < opens.size(); i++) {
      if (opens.get(i).size() == 0) {
        n++;
      }
    }
    for (int j = 0; j < cascades.size(); j++) {
      if (cascades.get(j).size() == 0) {
        k++;
      }
    }
    int f = (int) Math.pow(2.0, k);
    int max = (n + 1) * f;
    return max;
  }

  /**
   * to return the list of list of piles according to the given PileType.
   * @param source given the source pile type.
   * @return the list of list of piles.
   */
  private List<List<CardType>> getPile(PileType source) {
    if (source == PileType.CASCADE) {
      return cascades;
    }
    else if (source == PileType.FOUNDATION) {
      return foundation;
    }
    else {
      return opens;
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                    int destPileNumber) throws IllegalArgumentException {
    List<List<CardType>> sourceType = getPile(source);
    List<List<CardType>> destinationType = getPile(destination);

    if (cardIndex < 0 || destPileNumber < 0 || pileNumber < 0 || !this.isGameStarted
            || sourceType.size() <= pileNumber || destinationType.size() <= destPileNumber) {
      throw new IllegalArgumentException("the wanted move is not possible!");
    }
    else if (source == PileType.CASCADE && destination == PileType.CASCADE) {
      if ( cardIndex < cascades.get(pileNumber).size()) {
        List<CardType> sourceP = cascades.get(pileNumber);
        List<CardType> destinationP = cascades.get(destPileNumber);
        if (isBuild(pileNumber, cardIndex)
                && this.maxMovable() >= sourceP.size() - cardIndex) {
          if (destinationP.size() == 0
                  || (destinationP.get(destinationP.size() - 1)
                  .differFromColor(sourceP.get(cardIndex))
                  && destinationP.get(destinationP.size() - 1).number
                  == sourceP.get(cardIndex).number + 1)) {
            for (int i = cardIndex; i < sourceP.size(); i++) {
              CardType tem = sourceP.get(i);
              destinationP.add(tem);
            }
            for (int j = sourceP.size() - 1; j >= cardIndex; j--) {
              CardType tem = sourceP.get(j);
              sourceP.remove(tem);
            }
          }
          else {
            throw new IllegalArgumentException("the wanted move is not possible!");
          }
        }

        else {
          throw new IllegalArgumentException("the wanted move is not possible!");
        }
      }
      else {
        throw new IllegalArgumentException("the wanted move is not possible!");
      }
    }
    else if (cardIndex == sourceType.get(pileNumber).size() - 1) {
      int s = destinationType.get(destPileNumber).size();
      if (destination == PileType.OPEN && s == 0) {
        moveHelper(source, pileNumber, cardIndex, destination, destPileNumber);
      }
      else if (destination == PileType.CASCADE) {

        if (s == 0) {
          moveHelper(source, pileNumber, cardIndex, destination, destPileNumber);
        }
        else {
          CardType des = destinationType.get(destPileNumber).get(s - 1);
          CardType tem = sourceType.get(pileNumber).get(cardIndex);
          if (des.differFromColor(tem) && des.number == tem.number + 1) {
            moveHelper(source, pileNumber, cardIndex, destination, destPileNumber);
          }
          else {
            throw new IllegalArgumentException("the wanted move is not possible!");
          }
        }
      }
      else if (destination == PileType.FOUNDATION) {

        if (s == 0) {
          moveHelper(source, pileNumber, cardIndex, destination, destPileNumber);
        }
        else {
          CardType des = destinationType.get(destPileNumber).get(s - 1);
          CardType tem = sourceType.get(pileNumber).get(cardIndex);
          if (des.type.equals(tem.type) && des.number == tem.number - 1) {
            moveHelper(source, pileNumber, cardIndex, destination, destPileNumber);
          }
          else {
            throw new IllegalArgumentException("the wanted move is not possible!");
          }
        }
      }
      else {
        throw new IllegalArgumentException("the wanted move is not possible!");

      }
    }
    else {
      throw new IllegalArgumentException("the wanted move is not possible!");
    }
  }


  /**
   * TO move a card from a given pile to another pile.
   * @param source given pileType.
   * @param pileNumber given pile number.
   * @param cardIndex given cardIndex.
   * @param destination given pileType.
   * @param desNumber given Pile number.
   */
  private void moveHelper(PileType source, int pileNumber, int cardIndex, PileType destination,
                          int desNumber)  {
    List<List<CardType>> sourceType = getPile(source);
    List<List<CardType>> destinationType = getPile(destination);
    CardType tem = sourceType.get(pileNumber).get(cardIndex);
    destinationType.get(desNumber).add(tem);
    sourceType.get(pileNumber).remove(tem);
  }





  /**
   * to determine if the pile is a build from the given index to the the end.
   * @param sourcePileNumber the source pile number of cascade.
   * @param cardIndex the given cardIndex.
   * @return a boolean.
   */
  private boolean isBuild(int sourcePileNumber, int cardIndex) {
    List<CardType> pile = cascades.get(sourcePileNumber);
    if (cardIndex == pile.size() - 1) {
      return true;
    }
    else {
      for (int i = cardIndex + 1; i < pile.size(); i++) {
        CardType before = pile.get(i - 1);
        CardType after = pile.get(i);
        if (before.number != after.number + 1 || !before.differFromColor(after)) {
          return false;
        }
      }
      return true;
    }
  }

}
