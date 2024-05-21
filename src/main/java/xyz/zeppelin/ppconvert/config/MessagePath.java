package xyz.zeppelin.ppconvert.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.zeppelin.ppconvert.utils.Message;

@AllArgsConstructor
@Getter
public enum MessagePath {

    CONVERTED("point-messages.converted"),
    CONVERT_FAILED_INSUFFICIENT_FUNDS("point-messages.convert-failed.lack-of-funds"),
    CONVERT_FAILED_OTHER("point-messages.convert-failed.other"),
    CONVERT_CANCELLED("point-messages.convert-cancelled");

    final String path;

    public Message getAsMessage(MainConfig config, boolean defaults) {
        Message message = new Message(
                config.configuration.getString(getPath())
        );

        if (defaults) {
            message.defaults();
        }

        return message;
    }

}
