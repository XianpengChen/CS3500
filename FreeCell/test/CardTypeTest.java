import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by 28609 on 1/28/2017.
 */
public class CardTypeTest {

  CardType a1 = new CardType("A", 1, "Clubs");
  CardType b2 = new CardType("2", 2, "Hearts");
  CardType c3 = new CardType("3", 3, "Spades");
  CardType d2 = new CardType("2", 2, "Hearts");

  @Test
  public void testToString() {
    assertEquals("A♣", a1.toString());
    assertEquals("2♥", b2.toString());
    assertEquals("3♠", c3.toString());
  }

  @Test
  public void testDifferFromColor() {
    assertTrue(a1.differFromColor(b2));
    assertTrue(b2.differFromColor(c3));
    assertEquals(false, a1.differFromColor(c3));
  }

  @Test
  public void testSameCard() {
    assertEquals(false, a1.sameCard(b2));
    assertEquals(true, b2.sameCard(d2));
  }

}