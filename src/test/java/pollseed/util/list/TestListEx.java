package util.list;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.Test;

import util.list.ListEx.ExcludeList;

/**
 * {@link ListEx} is test class.
 * 
 * @since v0.1
 */
public class TestListEx extends TestCase {
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
        assertThat(ListEx.size(newList(), null, false), is(5));
        assertThat(ListEx.size(newList(), null, true), is(4));
        assertThat(ListEx.size(newList(), Arrays.asList("hoge"), false), is(4));
        assertThat(ListEx.size(newList(), Arrays.asList("hoge", "fuga"), false), is(3));
        assertThat(ListEx.size(newList(), Arrays.asList("hoge", "fuga", "piyo"), true), is(1));
        assertThat(
                ListEx.size(newList(), Arrays.asList("hoge", "fuga", "piyo", ""), true),
                is(0));
        assertThat(
                ListEx.size(newList(), Arrays.asList("hoge", "fuga", "piyo", null), false),
                is(1));
        assertThat(ListEx.size(newList(), Arrays.asList(""), false), is(4));
        assertThat(ListEx.size(newList(), Arrays.asList(""), true), is(3));
        assertThat(ListEx.size(newList(), Arrays.asList("", null), false), is(3));
        assertThat(ListEx.size(null, null, false), is(0));
    }

    @Test
    public void test_size() {
        assertThat(ListEx.size(newList(), false), is(5));
        assertThat(ListEx.size(newList(), true), is(4));
    }

    @Test
    public void test_toNotNullElementsList() {
        assertThat(
                ListEx.toNotNullElementsList(newList()),
                is(Arrays.asList("hoge", "fuga", "piyo", "")));
    }

    @Test
    public void test_toNotNullList() {
        assertThat(ListEx.toNotNullList(newList()), is(newList()));
        assertThat(ListEx.toNotNullList(null), isA(List.class));
    }

    @Test
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
        assertThat(list.addAll(0, Arrays.asList("test1")), is(false));
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
