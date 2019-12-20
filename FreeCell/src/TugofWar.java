import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 28609 on 2/23/2017.
 */
public class TugofWar {
  double average;
  int siz;
  List<Integer> input;
  TugofWar(double in, int siz, List<Integer> input) {
    this.average = in;
    this.siz = siz;
    this.input = input;

  }
  public static void main(String[] args) throws IOException {
    Scanner scan = new Scanner(System.in);
    scan.useDelimiter("\\s");
    int number = scan.nextInt();
    int allWeight = 0;
    List<Integer> inputList = new ArrayList<Integer>();
    while(inputList.size() < number) {
      int now = scan.nextInt();
      inputList.add(now);
      allWeight = allWeight + now;
    }
    double in = allWeight / 2;
    TugofWar tug = new TugofWar(in, number, inputList);
    int needNumber = number / 2;
    AW answer = tug.bestCombo( 0, needNumber, 0, new ArrayList<Integer>());
    List<Integer> wanted = answer.loindex;
    int weight = answer.weight;

    System.out.printf(tug.outPut(wanted, weight));




  }

  AW bestCombo(int index, int numberNeeded, int currentWeight, List<Integer> currentSlected) {


    if (numberNeeded == 1) {
      int now = input.get(index);
      int currIndex = index;

      for (int i = index; i < siz; i++) {
        int curr = input.get(i);
        if (Math.abs(average - currentWeight - curr) < Math.abs(average - currentWeight - now)) {
          now = curr;
          currIndex = i;
        }
      }
      currentSlected.add(currIndex);
      return new AW(currentWeight + now, currentSlected);
    }
    else if (numberNeeded >= siz - index) {
      for (int i = index; i < siz; i++) {
        currentSlected.add(i);
        currentWeight = currentWeight + input.get(i);
      }
      return new AW(currentWeight, currentSlected);
    }
    else {
      for (int i = index; i < siz; i++) {
        List<Integer> slected = new ArrayList<>(currentSlected);/*copy(currentSlected);*/
        slected.add(i);
        AW choose = bestCombo(index + 1, numberNeeded - 1,
                currentWeight + input.get(i), slected);
        AW notChoose = bestCombo(index + 1, numberNeeded,
                currentWeight, currentSlected);
        if (Math.abs(average - choose.weight)
                < Math.abs(average - notChoose.weight)) {
          return choose;
        }
        else {
          return notChoose;
        }
      }
    }
    return new AW(currentWeight, currentSlected);
  }

  String outPut(List<Integer> given, int weight) {
    String we = Integer.toString(weight);

    String first = Integer.toString(given.size());
    String wanted = we +" " + first;
    for (int i =0; i < given.size(); i++) {
      String tem = " " + Integer.toString(given.get(i) + 1);
      wanted = wanted + tem;
    }
    return wanted;

  }
}

class AW {
  int weight;
  List<Integer> loindex;
  AW(int weight, List<Integer> loindex) {
    this.weight = weight;
    this.loindex = loindex;
  }
}

