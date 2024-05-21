package xyz.zeppelin.ppconvert;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.zeppelin.ppconvert.command.BaseCommand;
import xyz.zeppelin.ppconvert.command.commands.ConvertPointsCommand;
import xyz.zeppelin.ppconvert.component.ComponentManager;
import xyz.zeppelin.ppconvert.config.MainConfig;
import xyz.zeppelin.ppconvert.economy.EconomyWrapper;
import xyz.zeppelin.ppconvert.economy.VaultEconomyWrapper;
import xyz.zeppelin.ppconvert.menu.MenuListener;
import xyz.zeppelin.ppconvert.point.PlayerPointsWrapper;
import xyz.zeppelin.ppconvert.point.PointWrapper;
import xyz.zeppelin.ppconvert.utils.Message;
import xyz.zeppelin.ppconvert.utils.PrepareCommandAPIStage;

import java.util.List;

@Getter
public class ConvertPlugin extends JavaPlugin {

    private final ComponentManager componentManager = ComponentManager.register(this, List.of(
            MainConfig.createDefault(this)
    ));

    private final List<BaseCommand> baseCommands = List.of(
            new ConvertPointsCommand(this)
    );

    private EconomyWrapper economyWrapper;
    private PointWrapper pointWrapper;

    @Override
    public void onLoad() {
        prepareCmdApi(PrepareCommandAPIStage.LOAD);
        componentManager.loadComponents();
    }

    @Override
    public void onEnable() {
        prepareCmdApi(PrepareCommandAPIStage.ENABLE);
        componentManager.enableComponents();

        enableCommands();
        enableListeners();

        this.economyWrapper = new VaultEconomyWrapper(this);
        this.pointWrapper = new PlayerPointsWrapper();

        if (!economyWrapper.validateServer() || !pointWrapper.validateServer()) {
            getLogger().warning("Missing one or more of REQUIRED dependencies:");
            getLogger().warning("-> https://www.spigotmc.org/resources/vault.34315/");
            getLogger().warning("-> https://www.spigotmc.org/resources/playerpoints.80745/");

            getLogger().warning("Plugin will not function and plugin is being disabled...");
            getServer().getPluginManager().disablePlugin(this);
        }

        setPrefixes();
    }

    @Override
    public void onDisable() {
        prepareCmdApi(PrepareCommandAPIStage.DISABLE);
        componentManager.disableComponents();
    }

    private void enableCommands() {
        baseCommands.forEach(BaseCommand::register);
    }

    private void enableListeners() {
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    private void setPrefixes() {
        String PREFIX_CHECK = componentManager.getComponent(MainConfig.class).getPrefixCheck();
        String PREFIX_CROSS = componentManager.getComponent(MainConfig.class).getPrefixCross();

        new Message().setPrefixValues(
            PREFIX_CHECK, PREFIX_CROSS
        );
    }

    private void prepareCmdApi(PrepareCommandAPIStage stage) {
        switch (stage) {
            case LOAD -> CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
            case ENABLE -> CommandAPI.onEnable();
            case DISABLE -> CommandAPI.onDisable();
        }
    }
}
