package xyz.zeppelin.ppconvert.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicesManager;
import xyz.zeppelin.ppconvert.ConvertPlugin;
import xyz.zeppelin.ppconvert.config.MainConfig;
import xyz.zeppelin.ppconvert.utils.ActionCallback;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class VaultEconomyWrapper implements EconomyWrapper<Economy> {
    private ConvertPlugin plugin;

    public VaultEconomyWrapper(ConvertPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Economy obtain() {
        ServicesManager servicesManager = plugin.getServer().getServicesManager();
        return Objects.requireNonNull(servicesManager.getRegistration(Economy.class)).getProvider();
    }

    @Override
    public boolean validateServer() {
        return plugin.getServer().getPluginManager().isPluginEnabled("Vault");
    }

    @Override
    public BigDecimal balance(UUID player) {
        return BigDecimal.valueOf(obtain().getBalance(plugin.getServer().getOfflinePlayer(player)));
    }

    @Override
    public ActionCallback take(UUID player, BigDecimal amount) {
        EconomyResponse response = obtain().withdrawPlayer(plugin.getServer().getOfflinePlayer(player), amount.doubleValue());

        if (response.transactionSuccess()) {
            return ActionCallback.SUCCESSFUL;
        } else {
            if (plugin.getComponentManager().getComponent(MainConfig.class).onFailureDebug()) {
                plugin.getLogger().warning("A error occured - transaction failed for " + Bukkit.getPlayer(player).getName() + ": \n" + response.errorMessage);
            }
            return ActionCallback.FAILED;
        }
    }

    @Override
    public ActionCallback give(UUID player, BigDecimal amount) {
        EconomyResponse response = obtain().depositPlayer(plugin.getServer().getOfflinePlayer(player), amount.doubleValue());

        if (response.transactionSuccess()) {
            return ActionCallback.SUCCESSFUL;
        } else {
            return ActionCallback.FAILED;
        }
    }
}
