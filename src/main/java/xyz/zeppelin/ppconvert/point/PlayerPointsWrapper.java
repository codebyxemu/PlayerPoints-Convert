package xyz.zeppelin.ppconvert.point;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import xyz.zeppelin.ppconvert.utils.ActionCallback;

import java.math.BigDecimal;
import java.util.UUID;

public class PlayerPointsWrapper implements PointWrapper<PlayerPointsAPI> {
    @Override
    public PlayerPointsAPI obtain() {
        if (validateServer()) {
            return PlayerPoints.getInstance().getAPI();
        }
        return null;
    }

    @Override
    public boolean validateServer() {
        return Bukkit.getPluginManager().isPluginEnabled("PlayerPoints");
    }

    @Override
    public BigDecimal balance(UUID player) {
        return BigDecimal.valueOf(obtain().look(player));
    }

    @Override
    public ActionCallback take(UUID player, BigDecimal amount) {
        boolean success = obtain().take(player, amount.intValue());

        return success ? ActionCallback.SUCCESSFUL : ActionCallback.FAILED;
    }

    @Override
    public ActionCallback give(UUID player, BigDecimal amount) {
        boolean success = obtain().give(player, amount.intValue());

        return success ? ActionCallback.SUCCESSFUL : ActionCallback.FAILED;
    }
}
