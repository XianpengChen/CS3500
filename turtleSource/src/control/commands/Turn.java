package control.commands;


import control.TracingTurtleCommand;
import tracingmodel.TracingTurtleModel;

/**
 * Created by blerner on 10/10/16.
 */
public class Turn implements TracingTurtleCommand {
  double d;

  public Turn(Double d) {
    this.d = d;
  }

  @Override
  public void go(TracingTurtleModel m) {
    m.turn(this.d);
  }
}
