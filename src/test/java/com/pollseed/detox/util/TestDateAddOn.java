package com.pollseed.detox.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.ZonedDateTime;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import com.pollseed.detox.util.DateAddOn.DateFormat;

public class TestDateAddOn {

    @Test
    public void test() {
        final Date now = DateAddOn.now();
        final DateTime nowDateTime = DateAddOn.nowDateTime();
        final ZonedDateTime nowZonedDateTime = DateAddOn.nowZonedDateTime();

        assertThat(DateAddOn.format(now, DateFormat.HYPHEN_HMS),
                is(DateAddOn.format(nowDateTime, DateFormat.HYPHEN_HMS)));
        assertThat(DateAddOn.format(now, DateFormat.HYPHEN_HMS),
                is(DateAddOn.format(DateAddOn.toDate(nowZonedDateTime), DateFormat.HYPHEN_HMS)));
        assertThat(DateAddOn.format(nowDateTime, DateFormat.HYPHEN_HMS),
                is(DateAddOn.format(DateAddOn.toDateTime(nowZonedDateTime), DateFormat.HYPHEN_HMS)));
    }

}
