package xyz.zeppelin.ppconvert.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ActionCallback {

    SUCCESSFUL(""),
    FAILED(""),
    FAILED_FUNDS("");

    private String message;

}
