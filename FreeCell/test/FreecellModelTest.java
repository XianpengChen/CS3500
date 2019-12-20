import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Created by 28609 on 1/27/2017.
 */
public class FreecellModelTest {
  CardType hearts8 = new CardType("8", 8, "Hearts");
  CardType clubs7 = new CardType("7", 7, "Clubs");
  CardType clubs6 = new CardType("6", 6, "Clubs");

  @Test
  public void testGetDeck() {
    FreecellOperations<CardType> model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();

    assertEquals(l1.get(0).number, 1);
    assertEquals(l1.get(10).c, "3");
    assertEquals(l1.get(12).type, "Clubs");
    assertEquals(l1.get(51).type, "Hearts");
    assertEquals(l1.get(48).number, 13);
    assertEquals(l1.get(36).c, "10");
  }

  @Test
  public void testIsValidDeck() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    List<CardType> l2 = model1.getDeck();
    CardType tem = l1.get(23);
    CardType add = new CardType("A", 1, "Clubs");
    l1.remove(tem);
    l1.add(add);
    assertEquals(false, model1.isValidDeck(l1));
    assertEquals(true, model1.isValidDeck(l2));
  }


  @Test
  public void testStartGame() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    List<CardType> l2 = model1.getDeck();


    model1.startGame(l1, 8, 4, false);

    assertEquals(1, model1.cascades.get(0).get(0).number);
    assertEquals("K", model1.cascades.get(0).get(6).c);
    assertEquals("Hearts", model1.cascades.get(7).get(5).type);
    assertEquals("Spades", model1.cascades.get(5).get(5).type);
    assertEquals(8, model1.cascades.size());
    assertEquals(4, model1.opens.size());
    assertEquals(4, model1.foundation.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameException() {
    FreecellOperations<CardType> model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    List<CardType> l2 = model1.getDeck();
    l2.add(hearts8);
    model1.startGame(l2, 6, 4, false);
  }

  @Test
  public void testMove() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();

    model1.startGame(l1, 8, 4, false);

    model1.move(PileType.CASCADE, 7, 5, PileType.CASCADE, 0);
    assertEquals(12, model1.cascades.get(0).get(7).number);
    model1.move(PileType.CASCADE, 7, 4, PileType.OPEN, 3);
    assertEquals("Hearts", model1.opens.get(3).get(0).type);
    model1.move(PileType.CASCADE, 6, 5, PileType.FOUNDATION, 2);
    assertEquals(12, model1.foundation.get(2).get(0).number);
    model1.move(PileType.OPEN, 3, 0, PileType.FOUNDATION, 0);
    assertEquals(10, model1.foundation.get(0).get(0).number);
    model1.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 0);
    assertEquals("Hearts", model1.opens.get(0).get(0).type);
    model1.move(PileType.FOUNDATION, 2, 0, PileType.FOUNDATION, 2);
    assertEquals(12, model1.foundation.get(2).get(0).number);
    model1.move(PileType.FOUNDATION, 2, 0, PileType.CASCADE, 1);
    assertEquals("Diamonds", model1.cascades.get(1).get(7).type);
    model1.move(PileType.OPEN, 0, 0, PileType.OPEN, 0);
    assertEquals(10, model1.opens.get(0).get(0).number);
    model1.move(PileType.CASCADE, 1, 7, PileType.OPEN, 1);
    model1.move(PileType.CASCADE, 1, 6, PileType.OPEN, 2);
    model1.move(PileType.OPEN, 0, 0, PileType.CASCADE, 1);
    assertEquals(10, model1.cascades.get(1).get(6).number);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException1() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.CASCADE, 9, 5, PileType.OPEN, 5);


  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException2() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.CASCADE, 7, 5, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException3() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException4() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.OPEN, 1, 5, PileType.OPEN, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException5() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.FOUNDATION, 9, 5, PileType.OPEN, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException6() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.FOUNDATION, 3, 0, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException7() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 0);
  }

  @Test
  public void testIsGameOver() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    assertEquals(false, model1.isGameOver());
  }

  @Test
  public void testGetGameState1() {

    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    String state = model1.getGameState();
    assertNotEquals(
            "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n" +
            "C2: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n" +
            "C3: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n" +
            "C4: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n" +
            "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n" +
            "C6: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n" +
            "C7: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n" +
            "C8: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥", state);
  }

  @Test
  public void testGetGameState2() {
    FreecellModel model1 = new FreecellModel();
    List<CardType> l1 = model1.getDeck();
    assertEquals("", model1.getGameState());
  }



}