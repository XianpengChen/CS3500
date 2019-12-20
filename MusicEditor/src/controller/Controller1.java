package controller;




import model.Beat;
import model.MusicModel;
import model.MusicModel1;
import util.CompositionBuilder;
import util.MusicReader;
import view.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by 28609 on 3/17/2017.
 */
public class Controller1 implements MusicController {
  private Readable rd;
  private Appendable ap;
  private MusicModel mu;

  /**
   * Constructor for our Controller1.
   *
   * @param rd represents the readable
   * @param ap represents the appendable
   */
  public Controller1(Readable rd, Appendable ap) {

    this.rd = rd;
    this.ap = ap;
    this.mu = null;

  }

  /**
   * to get the Music Model.
   *
   * @return Music Mode
   */
  public MusicModel getMu() {
    return this.mu;
  }


  @Override
  public IView buildView() throws IOException, InvalidMidiDataException {

    Scanner scan = new Scanner(this.rd);
    scan.useDelimiter("\\s");
    String filePath = new File("").getAbsolutePath();
    filePath += "\\MusicEditor\\";
    String file = filePath + scan.next();
    String type = scan.next();
    scan.close();
    HashMap<Integer, HashMap<Integer, Beat>> hash = createBlankMap();
    CompositionBuilder<MusicModel> md = new MusicModel1.Builder().hash(hash).volume(30).tempo(300);
    try {
      mu = MusicReader.parseFile(new FileReader(file), md);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }


    switch (type) {
      case "visual":
        return new GuiViewFrame(mu.map(), mu.number());

      case "midi":
        IView midiView = null;
        try {
          midiView = new MidiViewImpl(mu.map(), mu.getTempo(), mu.number(),
                  MidiSystem.getSequencer());
          return midiView;
        } catch (MidiUnavailableException e) {
          e.printStackTrace();
        }

        break;
      case "composite":
        CompositeView com = null;
        try {
          com = new CompositeView(mu, MidiSystem.getSequencer());
        } catch (MidiUnavailableException e) {
          e.printStackTrace();
        }
        return com;
      case "console":
        return new TextualView(mu);
      case "interactive":
        try {
          return new InteractiveView(mu, MidiSystem.getSequencer());
        } catch (MidiUnavailableException e) {
          e.printStackTrace();
        }
        break;
    }
    throw new IllegalArgumentException("no such type view");

  }

  public static HashMap<Integer, HashMap<Integer, Beat>> createBlankMap() {
    HashMap<Integer, HashMap<Integer, Beat>> hash = new HashMap<>();

    for (int i = 0; i < 128; i++) {
      HashMap<Integer, Beat> tem = new HashMap<>();
      hash.put(i, tem);
    }
    return hash;
  }

}
