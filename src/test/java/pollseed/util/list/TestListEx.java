package util.list;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * {@link ListEx} is test class.
 * 
 * @since v0.1
 */
public class TestListEx extends TestCase {
    private static final List<String> LIST = new ArrayList<>(Arrays.asList(
            "hoge",
            "fuga",
            "piyo",
            null,
            ""));

    @Test
    public void test_countList1() {
        assertThat(ListEx.countList(LIST, null, false), is(5));
        assertThat(ListEx.countList(LIST, null, true), is(4));
        assertThat(ListEx.countList(LIST, Arrays.asList("hoge"), false), is(4));
        assertThat(ListEx.countList(LIST, Arrays.asList("hoge", "fuga"), false), is(3));
        assertThat(ListEx.countList(LIST, Arrays.asList("hoge", "fuga", "piyo"), true), is(1));
        assertThat(
                ListEx.countList(LIST, Arrays.asList("hoge", "fuga", "piyo", ""), true),
                is(0));
        assertThat(
                ListEx.countList(LIST, Arrays.asList("hoge", "fuga", "piyo", null), false),
                is(1));
        assertThat(ListEx.countList(LIST, Arrays.asList(""), false), is(4));
        assertThat(ListEx.countList(LIST, Arrays.asList(""), true), is(3));
        assertThat(ListEx.countList(LIST, Arrays.asList("", null), false), is(3));
        assertThat(ListEx.countList(null, null, false), is(0));
    }

    @Test
    public void test_countList2() {
        assertThat(ListEx.countList(LIST, false), is(5));
        assertThat(ListEx.countList(LIST, true), is(4));
    }

    @Test
    public void test_toNotNullElementsList() {
        assertThat(
                ListEx.toNotNullElementsList(LIST),
                is(Arrays.asList("hoge", "fuga", "piyo", "")));
    }

    @Test
    public void test_toNotNullList() {
        assertThat(ListEx.toNotNullList(LIST), is(LIST));
        assertThat(ListEx.toNotNullList(null), isA(List.class));
    }
}