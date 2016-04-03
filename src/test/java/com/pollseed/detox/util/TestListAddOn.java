package com.pollseed.detox.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import com.pollseed.detox.util.ListAddOn.ExcludeList;

/**
 * {@link ListAddOn} is test class.
 *
 * @since v0.1
 */
public class TestListAddOn {
    private static List<String> newList() {
        return new ArrayList<>(Arrays.asList(
                "hoge",
                "fuga",
                "piyo",
                null,
                ""));
    }

    @Test
    public void test_size_excludes() {
        assertThat(ListAddOn.size(newList(), null, false), is(5));
        assertThat(ListAddOn.size(newList(), null, true), is(4));
        assertThat(ListAddOn.size(newList(), Collections.singletonList("hoge"), false), is(4));
        assertThat(ListAddOn.size(newList(), Arrays.asList("hoge", "fuga"), false), is(3));
        assertThat(ListAddOn.size(newList(), Arrays.asList("hoge", "fuga", "piyo"), true), is(1));
        assertThat(
                ListAddOn.size(newList(), Arrays.asList("hoge", "fuga", "piyo", ""), true),
                is(0));
        assertThat(
                ListAddOn.size(newList(), Arrays.asList("hoge", "fuga", "piyo", null), false),
                is(1));
        assertThat(ListAddOn.size(newList(), Collections.singletonList(""), false), is(4));
        assertThat(ListAddOn.size(newList(), Collections.singletonList(""), true), is(3));
        assertThat(ListAddOn.size(newList(), Arrays.asList("", null), false), is(3));
        assertThat(ListAddOn.size(null, null, false), is(0));
    }

    @Test
    public void test_size() {
        assertThat(ListAddOn.size(newList(), false), is(5));
        assertThat(ListAddOn.size(newList(), true), is(4));
    }

    @Test
    public void test_toNotNullElementsList() {
        assertThat(
                ListAddOn.toNotNullElementsList(newList()),
                is(Arrays.asList("hoge", "fuga", "piyo", "")));
    }

    @Test
    public void test_toNotNullList() {
        assertThat(ListAddOn.toNotNullList(newList()), is(newList()));
        assertThat(ListAddOn.toNotNullList(null), isA(List.class));
    }

    //    @Test
    public void test_ExcludeList() {
        final ExcludeList<String> list =
                new ExcludeList<>(newList(), Pattern.compile("^(test1|test2)$"));
        assertThat(list.add("test"), is(true));
        assertThat(list, is(Arrays.asList("hoge", "fuga", "piyo", null, "", "test")));
        assertThat(list.add("test2"), is(false));
        assertThat(list, is(Arrays.asList("hoge", "fuga", "piyo", null, "", "test")));
        assertThat(list.add("test3"), is(true));
        assertThat(list, is(Arrays.asList("hoge", "fuga", "piyo", null, "", "test", "test3")));
        assertThat(list.addAll(newList()), is(true));
        assertThat(list, is(Arrays.asList(
                "hoge",
                "fuga",
                "piyo",
                null,
                "",
                "test",
                "test3",
                "hoge",
                "fuga",
                "piyo",
                null,
                "")));
        assertThat(list.addAll(0, Collections.singletonList("test1")), is(false));
        assertThat(list, is(Arrays.asList(
                "hoge",
                "fuga",
                "piyo",
                null,
                "",
                "test",
                "test3",
                "hoge",
                "fuga",
                "piyo",
                null,
                "")));
    }
}
