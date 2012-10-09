package com.threeti.ics.server.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: johnson
 * Date: 10/4/12
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */
public final class I18NMessages {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.CHINA);

    public static String getMessage(final String key) {
        return bundle.getString(key);
    }

    public static String getMessage(final String key, Object... args) {
        return MessageFormat.format(bundle.getString(key), args);
    }
}
