package xyz.zeppelin.ppconvert.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Menu {

    private Inventory inventory;
    private Map<Integer, Consumer<Player>> functions = new HashMap<Integer, Consumer<Player>>();
    private Map<Integer, Consumer<Player>> functionSecondary = new HashMap<Integer, Consumer<Player>>();

    public Menu(String name, int rows) {
        this.inventory = Bukkit.createInventory(new MenuGuiHolder(), rows * 9, name);
    }

    public void clearGui() {
        functions.clear();
        functionSecondary.clear();
        this.inventory.clear();
    }

    public abstract void setupGui();

    public abstract void onClose(Player player);

    public void openGui(Player player) {
        clearGui();
        setupGui();
        player.openInventory(inventory);
    }

    public void addBorder(Material material) {
        int size = inventory.getSize(); // Assuming getSize() returns the size of the inventory

        // Fill top and bottom rows
        for (int x = 0; x < 9; x++) {
            addButton(x, 0, new ItemStack(material), event -> {}); // Top row
            addButton(x, size / 9 - 1, new ItemStack(material), event -> {}); // Bottom row
        }

        // Fill left and right columns
        for (int y = 1; y < size / 9 - 1; y++) {
            addButton(0, y, new ItemStack(material), event -> {}); // Left column
            addButton(8, y, new ItemStack(material), event -> {}); // Right column
        }
    }


    public void addButton(int x, int y, ItemStack item, Consumer<Player> function, Consumer<Player> secondary) {
        addButton(x + y * 9, item, function, secondary);
    }

    public void addButton(int x, int y, ItemStack item, Consumer<Player> function) {
        addButton(x + y * 9, item, function, null);
    }

    public void addButton(int slot, ItemStack item, Consumer<Player> function, Consumer<Player> secondary) {
        functions.put(slot, function);
        if (secondary != null)
            functionSecondary.put(slot, secondary);
        inventory.setItem(slot, item);
    }

    public void onClick(int slot, Player player, InventoryAction action) {
        if (action == InventoryAction.PICKUP_ALL) {
            if (functions.containsKey(slot)) {
                functions.get(slot).accept(player);
            }
        } else {
            if (functionSecondary.containsKey(slot)) {
                functionSecondary.get(slot).accept(player);
            }
        }
    }

    public class MenuGuiHolder implements InventoryHolder {

        public Menu getGui() {
            return Menu.this;
        }

        @Override
        public Inventory getInventory() {
            return inventory;
        }

    }

}