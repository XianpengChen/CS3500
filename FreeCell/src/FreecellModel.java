import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by 28609 on 1/25/2017.
 */
public class FreecellModel implements FreecellOperations<CardType> {
  public List<List<CardType>> foundation;
  public List<List<CardType>> opens;
  public List<List<CardType>> cascades;
  public boolean isGameStarted;

  /**
   * the constructor if an initial model.
   */
  public FreecellModel() {
    this.foundation = new ArrayList<List<CardType>>();
    this.opens = new ArrayList<List<CardType>>();
    this.cascades = new ArrayList<List<CardType>>();
    this.isGameStarted = false;
  }

  @Override
  public List<CardType> getDeck() {
    List<CardType> tem = new ArrayList<CardType>();
    return getDeckHelper(tem, 1);
  }

  /**
   * the helper method to create a deck of 52b cards.
   * @param given the given list of cards.
   * @param index the number value of next 4 cards which would be added to the given list.
   * @return a list of cards.
   */

  public List<CardType> getDeckHelper(List<CardType> given, int index) {
    if (given.size() >= 52) {
      return given;
    }
    else {
      String c = "";
      if (index == 1) {
        c = "A";
      }
      else if (index == 11) {
        c = "J";
      }
      else if (index == 12) {
        c = "Q";
      }
      else if (index == 13) {
        c = "K";
      }
      else {
        c = Integer.toString(index);
      }
      CardType club = new CardType(c, index, "Clubs");
      CardType spade = new CardType(c, index, "Spades");
      CardType diamond = new CardType(c, index, "Diamonds");
      CardType heart = new CardType(c, index, "Hearts");
      given.add(club);
      given.add(spade);
      given.add(diamond);
      given.add(heart);
      return getDeckHelper(given, index + 1);
    }
  }


  /**
   * to determine if a given deck is valid.
   * @param given a deck.
   * @return a boolean.
   */
  protected boolean isValidDeck(List<CardType> given) {
    boolean now = true;
    if (given.size() == 52) {
      for (int i = 0; i < given.size(); i++) {
        CardType tem =  given.get(i);
        int a = count(given, tem);
        if (tem.number <= 13 && tem.number >= 1
                && (tem.type.equals("Clubs") || tem.type.equals("Hearts")
                || tem.type.equals("Spades") || tem.type.equals("Diamonds"))) {
          if (a > 1) {
            now = false;
          }
        }
        else {
          return false;
        }
      }
      return now;
    }
    else {
      return false;
    }
  }

  /**
   * count how many times the card appears in the given list.
   * @param given a list of cards.
   * @param c the given card.
   * @return an int.
   */
  int count(List<CardType> given, CardType c) {
    int tem = 0;
    for (int i = 0; i < given.size(); i++) {
      if (given.get(i).sameCard(c)) {
        tem = tem + 1;
      }
    }
    return tem;
  }



  @Override
  public void startGame(List<CardType> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
          throws IllegalArgumentException {
    if (!isValidDeck(deck) || numCascadePiles < 4
            || numOpenPiles < 1) {
      isGameStarted = false;
      throw new IllegalArgumentException("invalid deck or invalid " +
              "numCascadePiles or invalid numOpenPiles");
    }

    else if (shuffle) {
      Collections.shuffle(deck);
      this.foundation = new ArrayList<List<CardType>>();
      this.opens = new ArrayList<List<CardType>>();
      this.cascades = new ArrayList<List<CardType>>();
      distributeCards(deck, numCascadePiles);
      buildOpens(numOpenPiles);
      buildFoundations();
      this.isGameStarted = true;

    }
    else {
      this.foundation = new ArrayList<List<CardType>>();
      this.opens = new ArrayList<List<CardType>>();
      this.cascades = new ArrayList<List<CardType>>();
      distributeCards(deck, numCascadePiles);
      buildOpens(numOpenPiles);
      buildFoundations();
      this.isGameStarted = true;
    }

  }


  /**
   * distribute cards in deck to those cascade piles in round robin way.
   * @param deck the given deck.
   * @param numCascadePiles the number of Piles of cascades needed to be built.
   */
  public void distributeCards(List<CardType> deck, int numCascadePiles) {
    for (int i = 0; i < numCascadePiles; i++) {
      List<CardType> tem = new ArrayList<CardType>();
      for (int j = i; j < deck.size(); j = j + numCascadePiles ) {
        tem.add(deck.get(j));
      }
      this.cascades.add(tem);
    }
  }


  /**
   * build up the open piles according to the input number.
   * @param numOpenPiles the number of open piles needed to build
   */
  public void buildOpens(int numOpenPiles) {
    for (int i = 1; i <= numOpenPiles; i++) {
      ArrayList<CardType> tem = new  ArrayList<CardType>();
      this.opens.add(tem);
    }
  }

  /**
   * build the four foundation piles.
   */
  public void buildFoundations() {
    for (int i = 1; i <= 4; i++) {
      ArrayList<CardType> tem = new  ArrayList<CardType>();
      this.foundation.add(tem);
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException {
    if (cardIndex < 0 || destPileNumber < 0 || pileNumber < 0 || !this.isGameStarted) {
      throw new IllegalArgumentException("the wanted move is not possible!");
    }
    else if (destination == PileType.OPEN && source == PileType.CASCADE) {
      if (opens.size() > destPileNumber && opens.get(destPileNumber).size() < 1
              && cascades.size() > pileNumber
              && cascades.get(pileNumber).size() - 1 == cardIndex) {
        CardType toMove = cascades.get(pileNumber).get(cardIndex);
        opens.get(destPileNumber).add(toMove);
        cascades.get(pileNumber).remove(toMove);
      }
      else {
        throw new IllegalArgumentException("the move wanted is not possible!");
      }
    }
    else if (destination == PileType.FOUNDATION && source == PileType.CASCADE) {
      if (4 > destPileNumber && cascades.size() > pileNumber
              && cascades.get(pileNumber).size() - 1 == cardIndex) {
        if (foundation.get(destPileNumber).size() == 0) {
          CardType c = cascades.get(pileNumber).get(cardIndex);
          foundation.get(destPileNumber).add(c);
          cascades.get(pileNumber).remove(c);
        }
        else if (foundation.get(destPileNumber).size() > 0) {
          int f = foundation.get(destPileNumber).size();
          CardType c = cascades.get(pileNumber).get(cardIndex);
          CardType cinF = foundation.get(destPileNumber).get(f - 1);
          if (c.type.equals(cinF.type) && c.number == cinF.number + 1) {
            foundation.get(destPileNumber).add(c);
            cascades.get(pileNumber).remove(c);
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
    else if (destination == PileType.FOUNDATION && source == PileType.OPEN) {
      if (4 > destPileNumber && opens.size() > pileNumber
              && opens.get(pileNumber).size() == 1 && cardIndex == 0) {
        if (foundation.get(destPileNumber).size() == 0) {
          CardType c = opens.get(pileNumber).get(cardIndex);
          foundation.get(destPileNumber).add(c);
          opens.get(pileNumber).remove(c);
        }
        else if (foundation.get(destPileNumber).size() > 0) {
          int f = foundation.get(destPileNumber).size();
          CardType o = opens.get(pileNumber).get(0);
          CardType cinF = foundation.get(destPileNumber).get(f - 1);
          if (o.type.equals(cinF.type) && o.number == cinF.number + 1) {
            foundation.get(destPileNumber).add(o);
            opens.get(pileNumber).remove(o);
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
    else if (destination == PileType.CASCADE && source == PileType.CASCADE) {
      if (cascades.size() > destPileNumber && cascades.size() > pileNumber
              && cascades.get(pileNumber).size() - 1 == cardIndex) {
        if (cascades.get(destPileNumber).size() == 0) {
          CardType fs = cascades.get(pileNumber).get(cardIndex);
          cascades.get(destPileNumber).add(fs);
          cascades.get(pileNumber).remove(fs);
        }

        else if (cascades.get(destPileNumber).size() > 0) {
          int fd = cascades.get(destPileNumber).size();
          CardType fs = cascades.get(pileNumber).get(cardIndex);
          CardType fde = cascades.get(destPileNumber).get(fd - 1);
          if (fs.differFromColor(fde)
                  && fs.number == fde.number - 1
                  && destPileNumber != pileNumber) {
            cascades.get(destPileNumber).add(fs);
            cascades.get(pileNumber).remove(fs);
          }
          else if (destPileNumber == pileNumber) {
            if (cascades.get(destPileNumber).size() == 1) {
              /*not do anything*/
            }
            else if (cascades.get(destPileNumber).size() > 1
                    && cascades.get(destPileNumber).get(fd - 2).differFromColor(fs)
                    && cascades.get(destPileNumber).get(fd - 2).number == fs.number + 1) {
              /*not do anything*/
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
      else {
        throw new IllegalArgumentException("the wanted move is not possible!");
      }
    }
    else if (destination == PileType.CASCADE && source == PileType.OPEN) {
      if (cascades.size() > destPileNumber && opens.size() > pileNumber
              && opens.get(pileNumber).size() == 1 && cardIndex == 0) {
        if (cascades.get(destPileNumber).size() == 0) {
          CardType os = opens.get(pileNumber).get(0);
          cascades.get(destPileNumber).add(os);
          opens.get(pileNumber).remove(os);
        }
        else if (cascades.get(destPileNumber).size() > 0) {


          int cd = cascades.get(destPileNumber).size();
          CardType os = opens.get(pileNumber).get(0);
          CardType cde = cascades.get(destPileNumber).get(cd - 1);
          if (os.number == cde.number - 1 && os.differFromColor(cde)) {
            cascades.get(destPileNumber).add(os);
            opens.get(pileNumber).remove(os);
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
    else if (destination == PileType.OPEN && source == PileType.OPEN) {
      if (destPileNumber != pileNumber) {
        if (opens.size() > destPileNumber && opens.size() > pileNumber
                && opens.get(destPileNumber).size() == 0
                && opens.get(pileNumber).size() == 1
                && cardIndex == 0) {
          CardType tem = opens.get(pileNumber).get(cardIndex);
          opens.get(destPileNumber).add(tem);
          opens.get(pileNumber).remove(tem);

        }
        else {
          throw new IllegalArgumentException("the wanted move is not possible!");
        }
      }
      else {
        /*not do anything*/
      }
    }
    else if (destination == PileType.OPEN && source == PileType.FOUNDATION) {
      if (opens.size() > destPileNumber && 4 > pileNumber
              && opens.get(destPileNumber).size() == 0
              && foundation.get(pileNumber).size() == cardIndex + 1) {
        CardType tem = foundation.get(pileNumber).get(cardIndex);
        opens.get(destPileNumber).add(tem);
        foundation.get(pileNumber).remove(tem);
      }
      else {
        throw new IllegalArgumentException("the wanted move is not possible!");
      }
    }
    else if (destination == PileType.CASCADE && source == PileType.FOUNDATION ) {
      if (cascades.size() > destPileNumber && 4 > pileNumber
              && foundation.get(pileNumber).size() == cardIndex + 1) {
        if (cascades.get(destPileNumber).size() == 0) {
          CardType tem = foundation.get(pileNumber).get(cardIndex);
          cascades.get(destPileNumber).add(tem);
          foundation.get(pileNumber).remove(tem);
        }
        else if (cascades.get(destPileNumber).size() > 0) {
          int dc = cascades.get(destPileNumber).size();
          CardType c = cascades.get(destPileNumber).get(dc - 1);
          CardType tem = foundation.get(pileNumber).get(cardIndex);
          if (c.number == tem.number + 1 && c.differFromColor(tem)) {
            cascades.get(destPileNumber).add(tem);
            foundation.get(pileNumber).remove(tem);
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
    else if (destination == PileType.FOUNDATION && source == PileType.FOUNDATION) {
      if (4 > destPileNumber && 4 > pileNumber
              && foundation.get(pileNumber).size() == cardIndex + 1) {
        if (foundation.get(destPileNumber).size() == 0) {
          CardType tem = foundation.get(pileNumber).get(cardIndex);
          foundation.get(destPileNumber).add(tem);
          foundation.get(pileNumber).remove(tem);
        }
        else if (foundation.get(destPileNumber).size() > 0) {
          if (destPileNumber != pileNumber) {
            int s = foundation.get(destPileNumber).size();
            CardType tem = foundation.get(pileNumber).get(cardIndex);
            CardType des = foundation.get(destPileNumber).get(s - 1);
            if (tem.type.equals(des.type) && tem.number == des.number + 1) {
              foundation.get(destPileNumber).add(tem);
              foundation.get(pileNumber).remove(tem);
            }
            else {
              throw new IllegalArgumentException("the wanted move is not possible!");
            }
          }
          else {
            /*not do anything*/
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

  @Override
  public boolean isGameOver() {

    return foundation.get(0).size() == 13
            && foundation.get(1).size() == 13
            && foundation.get(2).size() == 13
            && foundation.get(3).size() == 13;
  }

  @Override
  public String getGameState() {
    if (isGameStarted) {
      String o = buildString2(opens, "O");
      String f = buildString2(foundation, "F");
      String c = buildString2(cascades, "C");
      return f + System.lineSeparator() + o + System.lineSeparator() + c;
    }
    else {
      return "";
    }
  }

  /**
   * given a list of cards, return its String representation.
   * @param given a list of cards.
   * @return a String.
   */
  public String buildString1(List<CardType> given) {
    String tem = "";
    if (given.size() == 0) {
      return "";
    }
    else {
      for (int i = 0; i < given.size(); i++) {
        String card = given.get(i).toString();
        tem = tem + ", " + card;
      }
      String wanted = tem.substring(1);
      return wanted;
    }
  }

  /**
   *
   * @param given a List such as opens, foundations, or cascades
   * @param type is a String "F", "O", or "C".
   * @return a string to represent the whole list.
   */
  public String buildString2(List<List<CardType>> given, String type) {
    String tem = type + Integer.toString(1) + ":" + buildString1(given.get(0));
    for (int i = 1; i < given.size(); i++) {
      tem = tem  + System.lineSeparator() + type
              + Integer.toString(i + 1) + ":" + buildString1(given.get(i));
    }
    return tem;
  }
}
