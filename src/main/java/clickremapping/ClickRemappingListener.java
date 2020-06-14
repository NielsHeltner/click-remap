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

    private Set<Keybind> heldDown = new HashSet<>();

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (plugin.checkGuard()) {
            return;
        }

        if (config.keybind().matches(e) && !heldDown.contains(config.keybind())) {
            mouse.press();
            heldDown.add(config.keybind());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (plugin.checkGuard()) {
            return;
        }

        if (config.keybind().matches(e) && heldDown.contains(config.keybind())) {
            mouse.release();
            heldDown.remove(config.keybind());
        }
    }

}
