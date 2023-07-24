package clickremapping;

import net.runelite.client.config.Keybind;
import net.runelite.client.input.KeyListener;

import javax.inject.Inject;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class ClickRemappingListener implements KeyListener {

    @Inject
    private ClickRemappingPlugin plugin;

    @Inject
    private Mouse mouse;

    @Inject
    private ClickRemappingConfig config;

    private final Set<Keybind> heldDown = new HashSet<>();

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isConsumed() || plugin.checkGuard()) {
            return;
        }

        Keybind keybind = config.keybind();
        if (keybind.matches(e) && heldDown.add(keybind)) {
            mouse.press();
            e.consume();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.isConsumed()) {
            return;
        }

        Keybind keybind = config.keybind();
        if (keybind.matches(e) && heldDown.remove(keybind)) {
            mouse.release();
            e.consume();
        }
    }

}
