package com.threeti.ics.server.common;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 17/09/12
 * Time: 20:19
 * To change this template use File | Settings | File Templates.
 */
public final class Constants {
    public static final String VISITOR_USER = "user";
    public static final String ISWEB = "isWeb";
    public static final String CUSTOMER_SERVICE_USER = "customerServiceUser";
    public static final String CUSTOMER_SERVICE_STATUS_CHANGE_QUEUE = "customerServiceStatusChanged";

    public static String getDateString(final Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    public static Date getDate(final String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}
