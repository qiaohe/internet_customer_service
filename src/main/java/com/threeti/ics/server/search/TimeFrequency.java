package com.threeti.ics.server.search;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 25/09/12
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public enum TimeFrequency {
    FIVEMINUTES(5), THIRTYMINUTES(30), FOURHOURS(240), TWENTYFOURHOURS(12 * 60);

    private final int minutes;

    TimeFrequency(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public Date getDate() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, getMinutes() * -1);
        return cal.getTime();
    }
}
