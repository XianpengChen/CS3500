import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by 28609 on 2/7/2017.
 */
public class FreecellControllerTest {
  FreecellController noe = new FreecellController();
  FreecellOperations<CardType> model = new FreecellModel();




  @Test
  public void isInteger() throws Exception {
    assertEquals(false, noe.isInteger("a890"));
    assertEquals(true, noe.isInteger("4567"));
  }


  @Test
  public void testValid() throws Exception {
    assertEquals(true, noe.valid("C3"));
    assertEquals(false, noe.valid("c3"));
  }

  @Test
  public void testGetType() throws Exception {
    assertEquals(PileType.CASCADE, noe.getType("C"));
    assertEquals(PileType.FOUNDATION, noe.getType("F"));
    assertEquals(PileType.OPEN, noe.getType("O"));
  }

  @Test
  public void testCardIndex() throws Exception {
    assertEquals(true, noe.validCardIndex("5"));
    assertEquals(false, noe.validCardIndex("0"));
    assertEquals(false, noe.validCardIndex("c5"));
  }

  @Test
  public void testController1() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1\n7\nO1\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 8, 4, false);


    assertEquals(
            "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: K♣\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣\n" +
            "C2: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n" +
            "C3: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n" +
            "C4: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n" +
            "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n" +
            "C6: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n" +
            "C7: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n" +
            "C8: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥", out.toString().trim().replace("\r",""));
  }

  @Test
  public void testController2() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("q");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 8, 4, false);

    assertEquals("Game quit prematurely.", out.toString());
  }



  @Test
  public void testController3() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("F2\n1\nO2\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 8, 4, false);

    assertEquals(
            "Invalid move. Try again. Below is the current game state: \n" +
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
            "C8: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥", out.toString().trim().replace("\r",""));
  }

  @Test
  public void testController4() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("p1\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 8, 4, false);
    assertEquals("Please enter a valid source PileandNumber: \n", out.toString());
  }

  @Test
  public void testController5() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1\nA2\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 8, 4, false);
    assertEquals("Please enter a valid cardindex: \n", out.toString());
  }

  @Test
  public void testController6() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1\n7\na2\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 8, 4, false);
    assertEquals("Please enter a valid destination PileandNumber: \n", out.toString());
  }


  @Test
  public void testController7() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1\nq\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 8, 4, false);

    assertEquals("Game quit prematurely.", out.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testController8() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1\nq\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, null, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testController9() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1\nq\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(null, model, 8, 4, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testController10() {
    StringBuffer out = null;
    Reader in = new StringReader("C1\nq\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 8, 4, false);
  }


  @Test
  public void testController12() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1\n7\nO1\nC2\n7\nO2\nq\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 6, 4, false);
    assertEquals("Invalid move. Try again. Below is the current game state: \n" +
            "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 4♣, 5♦, 7♣, 8♦, 10♣, J♦, K♣\n" +
            "C2: A♠, 2♥, 4♠, 5♥, 7♠, 8♥, 10♠, J♥, K♠\n" +
            "C3: A♦, 3♣, 4♦, 6♣, 7♦, 9♣, 10♦, Q♣, K♦\n" +
            "C4: A♥, 3♠, 4♥, 6♠, 7♥, 9♠, 10♥, Q♠, K♥\n" +
            "C5: 2♣, 3♦, 5♣, 6♦, 8♣, 9♦, J♣, Q♦\n" +
            "C6: 2♠, 3♥, 5♠, 6♥, 8♠, 9♥, J♠, Q♥\n" +
            "Invalid move. Try again. Below is the current game state: \n" +
            "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♣, 2♦, 4♣, 5♦, 7♣, 8♦, 10♣, J♦, K♣\n" +
            "C2: A♠, 2♥, 4♠, 5♥, 7♠, 8♥, 10♠, J♥, K♠\n" +
            "C3: A♦, 3♣, 4♦, 6♣, 7♦, 9♣, 10♦, Q♣, K♦\n" +
            "C4: A♥, 3♠, 4♥, 6♠, 7♥, 9♠, 10♥, Q♠, K♥\n" +
            "C5: 2♣, 3♦, 5♣, 6♦, 8♣, 9♦, J♣, Q♦\n" +
            "C6: 2♠, 3♥, 5♠, 6♥, 8♠, 9♥, J♠, Q♥\n" +
            "Game quit prematurely.", out.toString().trim().replace("\r",""));

  }

  @Test
  public void testController13() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1\n7\nO1\nC2\n7\nO2\nq\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 3, 4, false);
    assertEquals("Could not start game.", out.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testController14() {
    StringBuffer out = new StringBuffer();
    Reader in = null;
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 8, 4, false);
  }

  @Test
  public void testController11() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1\n7\nO1\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 8, 4, true);


    assertNotEquals(
            "F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1: K♣\n" +
                    "O2:\n" +
                    "O3:\n" +
                    "O4:\n" +
                    "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣\n" +
                    "C2: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n" +
                    "C3: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n" +
                    "C4: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n" +
                    "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n" +
                    "C6: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n" +
                    "C7: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n" +
                    "C8: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥", out.toString().trim().replace("\r",""));
  }

  @Test
  public void testController21() {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1\n1\nF1\nC2\n1\nF2\nC3\n1\nF3\nC4\n1\nF4\n"
            + "C5\n1\nF1\nC6\n1\nF2\nC7\n1\nF3\nC8\n1\nF4\n"
            + "C9\n1\nF1\nC10\n1\nF2\nC11\n1\nF3\nC12\n1\nF4\n"
            + "C13\n1\nF1\nC14\n1\nF2\nC15\n1\nF3\nC16\n1\nF4\n"
            + "C17\n1\nF1\nC18\n1\nF2\nC19\n1\nF3\nC20\n1\nF4\n"
            + "C21\n1\nF1\nC22\n1\nF2\nC23\n1\nF3\nC24\n1\nF4\n"
            + "C25\n1\nF1\nC26\n1\nF2\nC27\n1\nF3\nC28\n1\nF4\n"
            + "C29\n1\nF1\nC30\n1\nF2\nC31\n1\nF3\nC32\n1\nF4\n"
            + "C33\n1\nF1\nC34\n1\nF2\nC35\n1\nF3\nC36\n1\nF4\n"
            + "C37\n1\nF1\nC38\n1\nF2\nC39\n1\nF3\nC40\n1\nF4\n"
            + "C41\n1\nF1\nC42\n1\nF2\nC43\n1\nF3\nC44\n1\nF4\n"
            + "C45\n1\nF1\nC46\n1\nF2\nC47\n1\nF3\nC48\n1\nF4\n"
            + "C49\n1\nF1\nC50\n1\nF2\nC51\n1\nF3\nC52\n1\nF4\n");
    FreecellController controller = new FreecellController(in, out);
    FreecellModel model = new FreecellModel();
    List<CardType> deck = model.getDeck();
    controller.playGame(deck, model, 52, 4, false);


    assertEquals(true, model.isGameOver());
  }

}