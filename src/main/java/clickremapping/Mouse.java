package clickremapping;

import java.awt.*;
import java.awt.event.InputEvent;

public class Mouse {

    private Robot robot;

    public Mouse() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void press() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void release() {
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

}
