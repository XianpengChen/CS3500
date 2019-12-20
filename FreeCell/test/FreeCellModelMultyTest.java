import org.junit.Test;

import java.util.List;


import static org.junit.Assert.assertEquals;

/**
 * Created by 28609 on 2/15/2017.
 */
public class FreeCellModelMultyTest {
  @Test
  public void testmaxMovable() throws Exception {
    FreeCellModelMulty now = new FreeCellModelMulty();
    List<CardType> deck = now.getDeck();
    now.startGame(deck, 8, 4, false);
    assertEquals(5, now.maxMovable());

  }

  @Test
  public void testmaxMovable2() throws Exception {
    FreeCellModelMulty now = new FreeCellModelMulty();
    List<CardType> deck = now.getDeck();
    now.startGame(deck, 8, 3, false);
    assertEquals(4, now.maxMovable());
  }

  @Test
  public void testMove() {
    FreecellModel model1 = new FreeCellModelMulty();
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
    model1.move(PileType.CASCADE, 1, 6, PileType.OPEN, 3);
    assertEquals(13, model1.opens.get(3).get(0).number);
    model1.move(PileType.CASCADE, 1, 5, PileType.CASCADE, 0);
    model1.move(PileType.CASCADE, 6, 4, PileType.CASCADE, 0);
    model1.move(PileType.CASCADE, 1, 4, PileType.CASCADE, 0);
    assertEquals(9, model1.cascades.get(0).get(10).number);
    model1.move(PileType.CASCADE, 6, 3, PileType.CASCADE, 0);
    model1.move(PileType.CASCADE, 6, 2, PileType.FOUNDATION, 0);
    model1.move(PileType.CASCADE, 6, 1, PileType.FOUNDATION, 1);
    model1.move(PileType.CASCADE, 6, 0, PileType.FOUNDATION, 3);
    model1.move(PileType.OPEN, 0, 0, PileType.CASCADE, 6);
    model1.move(PileType.CASCADE, 1, 3, PileType.CASCADE, 0);
    model1.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 0);
    model1.move(PileType.CASCADE, 0, 10, PileType.CASCADE, 6);
    assertEquals(6, model1.cascades.get(6).get(4).number);
    model1.move(PileType.CASCADE, 0, 9, PileType.FOUNDATION, 0);
    try {
      model1.move(PileType.CASCADE, 6, 0, PileType.CASCADE, 0);
    }
    catch (IllegalArgumentException e) {
      System.out.print("Exception 1\n");
    }
    model1.move(PileType.CASCADE, 6, 3, PileType.CASCADE, 7);
    assertEquals(6, model1.cascades.get(7).get(5).number);
    try {
      model1.move(PileType.CASCADE, 0, 7, PileType.CASCADE, 2);
    }
    catch (IllegalArgumentException e) {
      System.out.print("Exception 2\n");
    }
    try {
      model1.move(PileType.CASCADE, -1, 2, PileType.FOUNDATION, 1);
    }
    catch (IllegalArgumentException e) {
      System.out.print("Exception 3\n");
    }
    try {
      model1.move(PileType.CASCADE, 1, -2, PileType.FOUNDATION, 1);
    }
    catch (IllegalArgumentException e) {
      System.out.print("Exception 4\n");
    }
    try {
      model1.move(PileType.CASCADE, 1, 2, PileType.FOUNDATION, -11);
    }
    catch (IllegalArgumentException e) {
      System.out.print("Exception 5\n");
    }
    try {
      model1.move(PileType.CASCADE, 13, 2, PileType.OPEN, 6);
    }
    catch (IllegalArgumentException e) {
      System.out.print("Exception 6\n");
    }
    try {
      model1.move(PileType.OPEN, 2, 1, PileType.OPEN, 3);
    }
    catch (IllegalArgumentException e) {
      System.out.print("Exception 7\n");
    }

    try {
      model1.move(PileType.OPEN, 2, 0, PileType.OPEN, 3);
    }
    catch (IllegalArgumentException e) {
      System.out.print("Exception 8\n");
    }

    try {
      model1.move(PileType.OPEN, 2, 0, PileType.FOUNDATION, 3);
    }
    catch (IllegalArgumentException e) {
      System.out.print("Exception 9\n");
    }
    try {
      model1.move(PileType.OPEN, 2, 0, PileType.CASCADE, 3);
    }
    catch (IllegalArgumentException e) {
      System.out.print("Exception 10\n");
    }


  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException1() {
    FreecellModel model1 = new FreeCellModelMulty();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.CASCADE, 9, 5, PileType.OPEN, 5);


  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException2() {
    FreecellModel model1 = new FreeCellModelMulty();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.CASCADE, 7, 5, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException3() {
    FreecellModel model1 = new FreeCellModelMulty();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException4() {
    FreecellModel model1 = new FreeCellModelMulty();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.OPEN, 1, 5, PileType.OPEN, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException5() {
    FreecellModel model1 = new FreeCellModelMulty();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.FOUNDATION, 9, 5, PileType.OPEN, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException6() {
    FreecellModel model1 = new FreeCellModelMulty();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.FOUNDATION, 3, 0, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveException7() {
    FreecellModel model1 = new FreeCellModelMulty();
    List<CardType> l1 = model1.getDeck();
    model1.startGame(l1, 8, 4, false);
    model1.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 0);
  }

}