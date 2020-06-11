package clickremapping;

import net.runelite.client.input.KeyListener;

import javax.inject.Inject;
import java.awt.event.KeyEvent;

public class ClickRemappingListener implements KeyListener {

    @Inject
    private ClickRemappingPlugin plugin;

    @Inject
    private ClickRemappingConfig config;

    private boolean heldDown = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (plugin.checkGuard()) {
            return;
        }

        if (config.keybind().matches(e) && !heldDown) {
            plugin.click();
            heldDown = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        heldDown = false;
    }

}
