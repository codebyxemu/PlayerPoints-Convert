package xyz.zeppelin.ppconvert.menu.menus;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.zeppelin.ppconvert.ConvertPlugin;
import xyz.zeppelin.ppconvert.config.MainConfig;
import xyz.zeppelin.ppconvert.menu.Menu;
import xyz.zeppelin.ppconvert.utils.ItemBuilder;
import xyz.zeppelin.ppconvert.utils.NumberUtils;

public class ConvertPointsMenu extends Menu {
    private ConvertPlugin plugin;
    private MainConfig mainConfig;

    public ConvertPointsMenu(ConvertPlugin plugin) {
        super("Convert Points", 5);

        this.plugin = plugin;
        this.mainConfig = plugin.getComponentManager().getComponent(MainConfig.class);
    }

    int currentPoints = 0;

    @Override
    public void setupGui() {
        addBorder(Material.GRAY_STAINED_GLASS_PANE);

        String pointsDef = currentPoints <= 1 ? mainConfig.getSingularPointName() : mainConfig.getMultiplePointName();

        ItemStack add100 = ItemBuilder.of(Material.LIME_DYE)
                .displayName("&aAdd 100 " + mainConfig.getMultiplePointName())
                .build();

        ItemStack add50 = ItemBuilder.of(Material.LIME_DYE)
                .displayName("&aAdd 50 " + mainConfig.getMultiplePointName())
                .build();

        ItemStack add10 = ItemBuilder.of(Material.LIME_DYE)
                .displayName("&aAdd 10 " + mainConfig.getMultiplePointName())
                .build();

        double price = mainConfig.getPricePerPoint() * currentPoints;
        String priceFormatted = NumberUtils.formatNumber((int) price);

        ItemStack display = ItemBuilder.of(Material.HONEYCOMB)
                .displayName("&6&l" + currentPoints + " " + pointsDef)
                .lore("§f" + currentPoints + " §7will cost you", "§f$" + priceFormatted + " §7to convert.", "&7", "&e$" + NumberUtils.formatNumber((int) mainConfig.getPricePerPoint()) + " per point.")
                .build();

        ItemStack remove100 = ItemBuilder.of(Material.REDSTONE)
                .displayName("&cRemove 100 " + mainConfig.getMultiplePointName())
                .build();

        ItemStack remove50 = ItemBuilder.of(Material.REDSTONE)
                .displayName("&cRemove 50 " + mainConfig.getMultiplePointName())
                .build();

        ItemStack remove10 = ItemBuilder.of(Material.REDSTONE)
                .displayName("&cRemove 10 " + mainConfig.getMultiplePointName())
                .build();

        addButton(14, add100, event -> {
            if (currentPoints + 100 >= Integer.MAX_VALUE) return;
            currentPoints = currentPoints + 100;
            reopen(event.getPlayer());
            event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }, null);

        addButton(15, add50, event -> {
            if (currentPoints + 15 >= Integer.MAX_VALUE) return;
            currentPoints = currentPoints + 50;
            reopen(event.getPlayer());
            event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }, null);

        addButton(16, add10, event -> {
            if (currentPoints + 10 >= Integer.MAX_VALUE) return;
            currentPoints = currentPoints + 10;
            reopen(event.getPlayer());
            event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }, null);

        addButton(13, display, event -> {

        }, null);

        addButton(10, remove100, event -> {
            if (currentPoints - 100 <= 0) return;
            currentPoints = currentPoints - 100;
            reopen(event.getPlayer());
            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1, 1);
        }, null);

        addButton(11, remove50, event -> {
            if (currentPoints - 50 <= 0) return;
            currentPoints = currentPoints - 50;
            reopen(event.getPlayer());
            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1, 1);
        }, null);

        addButton(12, remove10, event -> {
            if (currentPoints - 10 <= 0) return;
            currentPoints = currentPoints - 10;
            reopen(event.getPlayer());
            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1, 1);
        }, null);

        ItemStack complete = ItemBuilder.of(Material.GREEN_STAINED_GLASS_PANE)
                .displayName("&a&lConvert " + pointsDef)
                .build();

        addButton(31, complete, event -> {
            new ConvertPointsConfirmMenu(plugin, event.getPlayer(), currentPoints, mainConfig.getPricePerPoint()).openGui(event.getPlayer());
        }, null);
    }

    @Override
    public void onClose(Player player) {

    }

    private void reopen(Player player) {
        //player.closeInventory();
        openGui(player);
    }
}
