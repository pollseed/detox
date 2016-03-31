package com.pollseed.detox.helper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import junit.framework.TestCase;

import org.junit.Test;

import util.HistoryStack;

public class TestHistoryStack extends TestCase {

    @Test
    public void test() {
        final HistoryStack<String> hs = new HistoryStack<>("TOP page");
        // next
        assertThat(hs.next("page1"), is("page1"));
        assertThat(hs.next("page2"), is("page2"));
        assertThat(hs.back(), is("page1"));
        assertThat(hs.back(), is("TOP page"));
        hs.removeAllElements();
        assertThat(hs.empty(), is(true));
        // back
        assertThat(hs.next("page1"), is("page1"));
        assertThat(hs.back(), is("TOP page"));
        assertThat(hs.next("page1"), is("page1"));
        assertThat(hs.home(), is("TOP page"));
        assertThat(hs.back(), is("page1"));
    }
}
