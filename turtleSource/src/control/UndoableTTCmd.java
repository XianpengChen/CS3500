package control;


import tracingmodel.TracingTurtleModel;

/**
 * Created by blerner on 10/10/16.
 */
public interface UndoableTTCmd extends TracingTurtleCommand {
  void undo(TracingTurtleModel m);
}
