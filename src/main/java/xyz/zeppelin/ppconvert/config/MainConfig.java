package xyz.zeppelin.ppconvert.config;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.logging.Logger;

public class MainConfig extends BaseConfig {

    public MainConfig(File file, String defaultName, Logger logger) {
        super(file, defaultName, logger);
    }

    public double getPricePerPoint() {
        return configuration.getDouble("price-per-point");
    }

    public String getSingularPointName() {
        return configuration.getString("point-names.singular");
    }

    public String getMultiplePointName() {
        return configuration.getString("point-names.plural");
    }

    public String getPrefixCheck() {
        return configuration.getString("prefixes.PREFIX_CHECK");
    }

    public String getPrefixCross() {
        return configuration.getString("prefixes.PREFIX_CROSS");
    }

    public boolean onFailureDebug() {
        return configuration.getBoolean("on-other-failure.debug");
    }

    public boolean versionWarning() {
        return configuration.getBoolean("version-warning");
    }

    public static MainConfig createDefault(Plugin plugin) {
        return new MainConfig(new File(plugin.getDataFolder(), "config.yml"), "/config/config.yml", plugin.getLogger());
    }

}