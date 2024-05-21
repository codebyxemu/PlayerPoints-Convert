package xyz.zeppelin.ppconvert.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@NoArgsConstructor
@Getter
public class Message {
    public String PREFIX_CHECK;
    public String PREFIX_CROSS;

    public void setPrefixValues(String PREFIX_CHECK, String PREFIX_CROSS) {
        this.PREFIX_CHECK = PREFIX_CHECK;
        this.PREFIX_CROSS = PREFIX_CHECK;
    }

    @Setter
    private String message;

    public Message(String message) {
        this.message = message;
    }

    public Message color() {
        this.message = ChatColor.translateAlternateColorCodes('&', message);
        return this;
    }

    public Message messageLine(int length, ChatColor color, String character) {
        StringBuilder line = new StringBuilder();

        for (int i = 0; i < length; i++) {
            line.append(character);
        }

        this.message = color + line.toString();

        return this;
    }

    public Message placeholder(String placeholder, String replacement) {
        this.message = message.replaceAll(placeholder, replacement);
        return this;
    }

    public void defaults() {
        placeholder("%PREFIX_CHECK%", "&8[&a✔&8]");
        placeholder("%PREFIX_CROSS%", "&8[&cX&8]");
        color();
    }

    public void send(Player player) {
        defaults();
        player.sendMessage(getMessage());
    }

}