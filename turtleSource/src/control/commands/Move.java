package control.commands;


import control.TracingTurtleCommand;
import tracingmodel.TracingTurtleModel;

/**
 * Created by blerner on 10/10/16.
 */
public class Move implements TracingTurtleCommand {
  double d;

  public Move(Double d) {
    this.d = d;
  }

  @Override
  public void go(TracingTurtleModel m) {
    m.move(this.d);
  }
}
