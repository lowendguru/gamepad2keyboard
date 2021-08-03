package gamepad2keyboard;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.studiohartman.jamepad.ControllerAxis;
import com.studiohartman.jamepad.ControllerIndex;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerUnpluggedException;

public class Main {

	public static void main(String[] args) throws AWTException {

		ControllerManager controllers = new ControllerManager();
		controllers.initSDLGamepad();

		// Print a message when the "A" button is pressed. Exit if the "B" button is
		// pressed
		// or the controller disconnects.
		ControllerIndex currController = controllers.getControllerIndex(0);

		Robot r = new Robot();
		int delay = 50;
		int w = KeyEvent.VK_W;
		int a = KeyEvent.VK_A;
		int s = KeyEvent.VK_S;
		int d = KeyEvent.VK_D;
		while (true) {
			controllers.update(); // If using ControllerIndex, you should call update() to check if a new
									// controller
									// was plugged in or unplugged at this index.

			try {

				System.out.println(currController.getAxisState(ControllerAxis.LEFTX));

				if (currController.getAxisState(ControllerAxis.LEFTX) != 0) {
					// moving
					if (currController.getAxisState(ControllerAxis.LEFTX) < 0) {
						// moving left
						r.keyPress(a);
						long durationPressA = (long) (delay * (currController.getAxisState(ControllerAxis.LEFTX) * -1));
						System.out.println("PRESS A: " + durationPressA);
						Thread.sleep(durationPressA);
						if (currController.getAxisState(ControllerAxis.LEFTX) <= -1) {
							r.keyRelease(a);
							long durationReleaseA = (long) (delay
									* (1 - (currController.getAxisState(ControllerAxis.LEFTX) * -1)));
							System.out.println("RELEASE A: " + durationReleaseA);
							Thread.sleep(durationReleaseA);
						}
					} else {
						// moving right
						r.keyPress(d);
						long durationPressA = (long) (delay * (currController.getAxisState(ControllerAxis.LEFTX) * 1));
						System.out.println("PRESS D: " + durationPressA);
						Thread.sleep(durationPressA);
						if (currController.getAxisState(ControllerAxis.LEFTX) >= 1) {
							r.keyRelease(d);
							long durationReleaseA = (long) (delay
									* (1 - (currController.getAxisState(ControllerAxis.LEFTX) * 1)));
							System.out.println("RELEASE D: " + durationReleaseA);
							Thread.sleep(durationReleaseA);
						}
					}

				} else {
					// not moving X
					r.keyRelease(a);
					r.keyRelease(d);
				}

//				System.out.println(currController.getAxisState(ControllerAxis.LEFTY));
//
//				System.out.println(currController.getAxisState(ControllerAxis.RIGHTX));
//
//				System.out.println(currController.getAxisState(ControllerAxis.RIGHTY));

				Thread.sleep(5);

			} catch (ControllerUnpluggedException e) {
				e.printStackTrace();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		controllers.quitSDLGamepad();

	}

}
