package xyz.zeppelin.ppconvert.point;

import xyz.zeppelin.ppconvert.utils.ActionCallback;

import java.math.BigDecimal;
import java.util.UUID;

public interface PointWrapper<T> {
    T obtain();

    boolean validateServer();

    BigDecimal balance(UUID player);

    ActionCallback take(UUID player, BigDecimal amount);

    ActionCallback give(UUID player, BigDecimal amount);
}
