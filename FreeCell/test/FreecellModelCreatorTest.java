import org.junit.Test;



import static org.junit.Assert.assertEquals;
/**
 * Created by 28609 on 2/15/2017.
 */
public class FreecellModelCreatorTest {

  @Test
  public void create() throws Exception {
    FreecellModelCreator fc = new FreecellModelCreator();
    FreecellModel multy = fc.create(FreecellModelCreator.GameType.MULTIMOVE);
    FreecellModel single = fc.create(FreecellModelCreator.GameType.SINGLEMOVE);
    assertEquals(true, multy instanceof FreeCellModelMulty);
    assertEquals(true, single instanceof FreecellModel);
  }

}