/**
 * Created by 28609 on 1/26/2017.
 */


/*
*this is the card class, from here to construct cards.
*
 */
public class CardType {
  public final String c;/* c is  one of J, Q, K, A, 2, 3, 4, 5, 6, 7, 8, 9, 10.*/
  public final int number;/* number is responded toc, 1 to A, 2 to 2, 11 to J, 12 to Q, 13 to K*/
  public final String type;/*type is one of Clubs, Diamonds, Hearts, or Spades*/

  /**
   * constructor of cards.
   * @param c represents the card's face value like"A", "2" etc.
   * @param number represents the number value of a card such as A = 1, 2 = 2, K = 13.
   * @param type is one of "Clubs", "Spades", "Hearts", and "Diamonds".
   */
  public CardType(String c, int number, String type) {
    this.c = c;
    this.number = number;
    this.type = type;
  }

  @Override
  public String toString() {
    if (this.type.equals("Clubs")) {
      return c + "♣";
    }
    else if (this.type.equals("Diamonds")) {
      return c + "♦";
    }
    else if (this.type.equals("Hearts")) {
      return c + "♥";
    }
    else {
      return c + "♠";
    }

  }

  /**
   * to determine if the two cards differ from color.
   * @param given the given card.
   * @return a boolean.
   */
  public boolean differFromColor(CardType given) {
    if (type.equals("Clubs") || type.equals("Spades")) {
      return given.type.equals("Hearts") || given.type.equals("Diamonds");
    }
    else {
      return given.type.equals("Clubs") || given.type.equals("Spades");
    }
  }

  /**
   * to determine if the two cards are the same.
   * @param given a card.
   * @return a boolean.
   */
  boolean sameCard(CardType given) {
    return number == given.number && type.equals(given.type);
  }
}