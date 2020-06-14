package clickremapping;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.VarClientInt;
import net.runelite.api.VarClientStr;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Click Remapping",
	description = "Allows remapping a key to left-click"
)
public class ClickRemappingPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private KeyManager keyManager;

	@Inject
	private ClickRemappingListener inputListener;

	@Override
	protected void startUp() {
		keyManager.registerKeyListener(inputListener);
	}

	@Override
	protected void shutDown() {
		keyManager.unregisterKeyListener(inputListener);
	}

	@Provides
	ClickRemappingConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(ClickRemappingConfig.class);
	}

	public boolean checkGuard() {
		return client.getGameState() == GameState.LOGIN_SCREEN
				|| isDialogOpen()
				|| !chatboxFocused()
				|| isTyping();
	}

	private boolean chatboxFocused() {
		Widget chatboxParent = client.getWidget(WidgetInfo.CHATBOX_PARENT);
		if (chatboxParent == null || chatboxParent.getOnKeyListener() == null) {
			return false;
		}
		Widget worldMapSearch = client.getWidget(WidgetInfo.WORLD_MAP_SEARCH);
		return worldMapSearch == null || client.getVar(VarClientInt.WORLD_MAP_SEARCH_FOCUSED) != 1;
	}

	private boolean isDialogOpen() {
		return isHidden(WidgetInfo.CHATBOX_MESSAGES) || isHidden(WidgetInfo.CHATBOX_TRANSPARENT_LINES);
	}

	private boolean isHidden(WidgetInfo widgetInfo) {
		Widget w = client.getWidget(widgetInfo);
		return w == null || w.isSelfHidden();
	}

	private boolean isTyping() {
		return !client.getVar(VarClientStr.CHATBOX_TYPED_TEXT).isEmpty();
	}

}
