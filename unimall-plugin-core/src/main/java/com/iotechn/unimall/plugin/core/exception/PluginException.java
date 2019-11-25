package com.iotechn.unimall.plugin.core.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/10/29
 * Time: 16:45
 */
public class PluginException extends RuntimeException {

    public PluginException() {

    }

    public PluginException(String msg) {
        super(msg);
    }
}
