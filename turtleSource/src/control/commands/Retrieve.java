package control.commands;


import control.TracingTurtleCommand;
import tracingmodel.TracingTurtleModel;

/**
 * Created by ashesh on 10/28/2016.
 */
public class Retrieve implements TracingTurtleCommand {
  @Override
  public void go(TracingTurtleModel m) {
    m.retrieve();
  }
}
