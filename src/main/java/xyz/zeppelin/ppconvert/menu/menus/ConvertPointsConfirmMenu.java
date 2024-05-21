package xyz.zeppelin.ppconvert.menu.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.zeppelin.ppconvert.ConvertPlugin;
import xyz.zeppelin.ppconvert.config.MainConfig;
import xyz.zeppelin.ppconvert.config.MessagePath;
import xyz.zeppelin.ppconvert.utils.ActionCallback;
import xyz.zeppelin.ppconvert.economy.VaultEconomyWrapper;
import xyz.zeppelin.ppconvert.menu.Menu;
import xyz.zeppelin.ppconvert.utils.ItemBuilder;
import xyz.zeppelin.ppconvert.utils.Message;
import xyz.zeppelin.ppconvert.utils.NumberUtils;

import java.math.BigDecimal;

public class ConvertPointsConfirmMenu extends Menu {
    private final ConvertPlugin plugin;
    private final MainConfig mainConfig;
    private final Player player;
    private final int points;
    private final double price;
    private final VaultEconomyWrapper wrapper;

    public ConvertPointsConfirmMenu(ConvertPlugin plugin, Player player, int points, double price) {
        super("Confirm Conversion", 3);
        this.plugin = plugin;
        this.mainConfig = plugin.getComponentManager().getComponent(MainConfig.class);
        this.player = player;
        this.points = points;
        this.price = price;
        this.wrapper = (VaultEconomyWrapper) plugin.getEconomyWrapper();
    }

    @Override
    public void setupGui() {
        addBorder(Material.GRAY_STAINED_GLASS);

        addButton(11, ItemBuilder.of(Material.LIME_DYE, "&a&lConfirm").build(), event -> {

            if (wrapper.balance(event.getPlayer().getUniqueId()).doubleValue() < price * points) {
                event.closeInventory();
                System.out.println(wrapper.balance(event.getPlayer().getUniqueId()).doubleValue());
                System.out.println(points * price);
                MessagePath.CONVERT_FAILED_INSUFFICIENT_FUNDS.getAsMessage(mainConfig, true)
                        .send(player);
                return;
            }

            ActionCallback callback = wrapper.take(event.getPlayer().getUniqueId(), BigDecimal.valueOf(points * price));

            if (callback == ActionCallback.SUCCESSFUL) {
                plugin.getPointWrapper().give(player.getUniqueId(), BigDecimal.valueOf(points));

                MessagePath.CONVERTED.getAsMessage(mainConfig, true)
                        .placeholder("%CONVERTED_MONEY%", NumberUtils.formatNumber((int) (points * price)))
                        .placeholder("%POINTS%", String.valueOf(points))
                        .send(player);

                player.closeInventory();
            } else {
                MessagePath.CONVERT_FAILED_OTHER.getAsMessage(mainConfig, true)
                        .send(player);

                plugin.getLogger().warning("A player attempted to convert $" + NumberUtils.formatNumber((int) (price * points))
                        + " to " + points + " but failed. Returned Callback: " + callback.name());
            }


        }, null);

        addButton(15, ItemBuilder.of(Material.REDSTONE, "&c&lCancel").build(), event -> {
            MessagePath.CONVERT_CANCELLED.getAsMessage(mainConfig, true)
                    .send(player);
            player.closeInventory();
        }, null);
    }

    @Override
    public void onClose(Player player) {

    }
}
