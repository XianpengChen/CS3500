import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;




/**
 * Created by 28609 on 2/5/2017.
 * the controller class that take user input and excute.
 */
class MainRunner {
  public static void main(String[] args) throws IOException {
    FreecellModel model = FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);

    List<CardType> deck = model.getDeck();
    FreecellController controller =
            new FreecellController(new InputStreamReader(System.in), System.out);
    controller.playGame(deck,model,52,4,false);
  }

}



public class FreecellController implements IFreecellController<CardType> {
  private final Readable rd;
  private final Appendable ap;
  private FreecellOperations model;

  /**
   * a constructor for FreecellController.
   * @param rd readable input.
   * @param ap apendable output.
   */

  public FreecellController(Readable rd, Appendable ap) {
    this.rd = rd;
    this.ap = ap;
    this.model = new FreecellModel();
  }

  /**
   * the convenient constructor.
   */
  public FreecellController() {
    this.rd = null;
    this.ap = null;
    this.model = null;
  }

  @Override
  public void playGame(List<CardType> deck, FreecellOperations<CardType> model,
                       int numCascades, int numOpens, boolean shuffle) {
    if (deck == null || model == null) {
      throw new IllegalArgumentException("deck or model is null");
    }
    else if (rd == null || ap == null) {
      throw new IllegalStateException("readable or appendable is null");
    }

    try {
      model.startGame(deck, numCascades, numOpens, shuffle);

    }
    catch (IllegalArgumentException e) {
      try {
        this.ap.append("Could not start game.");


      } catch (IOException e1) {
        e1.printStackTrace();
      }
      return;
    }
    this.model = model;
    start();


  }

  /**
   * input a string, return the piletype.
   * @param now is a given string.
   * @return a piletype.
   */
  protected PileType getType(String now) {
    if (now.equals("C")) {
      return PileType.CASCADE;
    }
    else if (now.equals("O")) {
      return PileType.OPEN;
    }
    else if (now.equals("F")) {
      return  PileType.FOUNDATION;
    }
    else {
      throw new IllegalArgumentException("no such type!");
    }

  }

  /**
   * to determine if the given string could be converted to an int.
   * @param given a string.
   * @return a boolean value.
   */
  protected boolean isInteger(String given) {
    if (given == null) {
      return false;
    }
    int length = given.length();
    if (length == 0) {
      return false;
    }
    else {

      for (int i = 0; i < length; i++) {
        char c = given.charAt(i);
        if (c < '0' || c > '9') {
          return false;
        }
      }
      return true;
    }
  }

  /**
   * to determine if the given string is a valid input for piletype and number.
   * @param given a string.
   * @return a boolean
   */
  protected boolean valid(String given) {
    if (given == null) {
      return false;
    }
    else {
      int length = given.length();
      if (length == 0) {
        return false;
      } else {
        String pileType = given.substring(0, 1);
        String pileNumber = given.substring(1);
        if (!(pileType.equals("C") || pileType.equals("F") || pileType.equals("O"))) {
          return false;
        } else {
          return isInteger(pileNumber);
        }
      }
    }
  }

  /**
   * to determine if the givenstring is a valid cardindex.
   * @param given a string.
   * @return a boolean.
   */
  protected boolean validCardIndex(String given) {
    if (isInteger(given)) {
      return Integer.valueOf(given) > 0;
    }
    else {
      return false;
    }
  }

  /**
   * take user input, make moves in the model.
   */
  protected void start() {
    String sourcePileandNumber = null;
    String cardindex = null;
    String destinationPileandNumber = null;
    Scanner scan = new Scanner(this.rd);
    scan.useDelimiter("\\s");
    while (scan.hasNext()) {
      if (sourcePileandNumber == null) {
        sourcePileandNumber = scan.next();
        if (sourcePileandNumber.equalsIgnoreCase("q")) {
          try {
            this.ap.append("Game quit prematurely.");
          } catch (IOException e) {
            e.printStackTrace();
          }
          return;
        }

        if (!valid(sourcePileandNumber)) {
          try {
            this.ap.append("Please enter a valid source PileandNumber: \n");
          } catch (IOException e) {
            e.printStackTrace();
          }
          sourcePileandNumber = null;
          continue;

        }
      }
      if (cardindex == null) {
        cardindex = scan.next();
        if (cardindex.equalsIgnoreCase("q")) {
          try {
            this.ap.append("Game quit prematurely.");
          } catch (IOException e) {
            e.printStackTrace();
          }
          return;
        }
        if (!validCardIndex(cardindex)) {
          try {
            this.ap.append("Please enter a valid cardindex: \n");
          } catch (IOException e) {
            e.printStackTrace();
          }
          cardindex = null;
          continue;
        }
      }
      if (destinationPileandNumber == null) {
        destinationPileandNumber = scan.next();
        if (destinationPileandNumber.equalsIgnoreCase("q")) {
          try {
            this.ap.append("Game quit prematurely.");
          } catch (IOException e) {
            e.printStackTrace();
          }
          return;
        }
        if (!valid(destinationPileandNumber)) {
          try {
            this.ap.append("Please enter a valid destination PileandNumber: \n");
          } catch (IOException e) {
            e.printStackTrace();
          }
          destinationPileandNumber = null;
          continue;
        }
      }

      try {
        makeMove(model, sourcePileandNumber, cardindex, destinationPileandNumber);
      } catch (IOException e) {
        e.printStackTrace();
      }
      sourcePileandNumber = null;
      cardindex = null;
      destinationPileandNumber = null;
      if (model.isGameOver()) {
        try {
          this.ap.append(model.getGameState() + "\nGame over.");
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
      }

      try {
        this.ap.append(model.getGameState() + "\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * use String input to make moves in the model.
   * @param model the given model.
   * @param source the given source pile and number.
   * @param cardIndex the given cardindex.
   * @param destination the given destination pile and number.
   * @throws IOException when occured.
   */
  protected void makeMove(FreecellOperations<CardType> model, String source,
                       String cardIndex, String destination) throws IOException {
    PileType sourceType = getType(source.substring(0, 1));
    int sourcePileNumber = Integer.valueOf(source.substring(1)) - 1;
    int index = Integer.valueOf(cardIndex) - 1;
    PileType destinationType = getType(destination.substring(0, 1));
    int destinationPileNumber = Integer.valueOf(destination.substring(1)) - 1;
    try {
      model.move(sourceType, sourcePileNumber, index, destinationType, destinationPileNumber);
    }
    catch (IllegalArgumentException e) {
      this.ap.append("Invalid move. Try again. Below is the current game state: \n");
    }
  }
}
