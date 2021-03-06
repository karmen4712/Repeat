package globalListener;

import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.simplenativehooks.events.NativeMouseEvent.State;
import org.simplenativehooks.listeners.AbstractGlobalMouseListener;

import utilities.JNativeHookCodeConverter;

/**
 * Implementation using JNativeHook as underlying library.
 */
public class GlobalJNativeHookMouseListener extends AbstractGlobalMouseListener implements NativeMouseInputListener {

	private static final Logger LOGGER = Logger.getLogger(GlobalJNativeHookMouseListener.class.getName());

	protected GlobalJNativeHookMouseListener() {
		super();
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {

	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		if (mousePressed != null) {
			if (!mousePressed.apply(convertEvent(arg0, State.PRESSED))) {
				LOGGER.warning("Mouse pressed event callback failed");
			}
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		if (mouseReleased != null) {
			if (!mouseReleased.apply(convertEvent(arg0, State.RELEASED))) {
				LOGGER.warning("Mouse release event callback failed");
			}
		}
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		if (mouseMoved != null) {
			if (!mouseMoved.apply(convertEvent(arg0))) {
				LOGGER.warning("Mouse move event callback failed");
			}
		}
	}

	private org.simplenativehooks.events.NativeMouseEvent convertEvent(NativeMouseEvent e) {
		return org.simplenativehooks.events.NativeMouseEvent.of(e.getX(), e.getY(), State.UNKNOWN, 0);
	}

	private org.simplenativehooks.events.NativeMouseEvent convertEvent(NativeMouseEvent e, State state) {
		int button = JNativeHookCodeConverter.getMouseButtonCode(e.getButton());
		return org.simplenativehooks.events.NativeMouseEvent.of(e.getX(), e.getY(), state, button);
	}

	@Override
	public boolean startListening() {
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
		return true;
	}

	@Override
	public boolean stopListening() {
		GlobalScreen.removeNativeMouseListener(this);
		GlobalScreen.removeNativeMouseMotionListener(this);
		return true;
	}
}
