package pollseed.util.list;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * List class Extension (for updating java7 to java8).
 * 
 * @version java8
 */
public class ListAddOn {
    private ListAddOn() {
    }

    /**
     * To count the list removed by {@code excludes} list.
     * 
     * @param list
     *            {@code List<T>} target list
     * @param excludes
     *            {@code List<T>} Removing excludes list
     * @param isNotNull
     *            {@code true} if {@code NOT NULL (target list) }
     * @return size of target list
     * @since v0.1
     */
    public static <T> int size(final List<T> list, final List<T> excludes, final boolean isNotNull) {
        return size(exclude(list, excludes, isNotNull));
    }

    private static <T> Optional<List<T>> exclude(
            final List<T> list,
            final List<T> excludes,
            final boolean isNotNull) {
        return Optional.ofNullable(toNotNullList(list)).map(
                e -> {
                    Optional.ofNullable(excludes).ifPresent(
                            optionalExcludes -> optionalExcludes.forEach(exclude -> e.removeAll(Collections.singleton(exclude))));
                    return toNotNullElementsList(e, isNotNull);
                });
    }

    /**
     * To count the list.
     * 
     * @param list
     *            {@code List<T>} target list
     * @param isNotNull
     *            {@code true} if NOT NULL (target list)
     * @return size of target list
     * @since v0.1
     */
    public static <T> int size(final List<T> list, final boolean isNotNull) {
        return size(Optional.ofNullable(toNotNullList(list)).map(
                e -> toNotNullElementsList(e, isNotNull)));
    }

    private static <T> int size(final Optional<List<T>> optional) {
        return optional.isPresent() ? optional.get().size() : 0;
    }

    private static <T> List<T> toNotNullElementsList(final List<T> list, final boolean isNotNull) {
        if (isNotNull) {
            return toNotNullElementsList(list);
        }
        return list;
    }

    /**
     * To get the list remove {@code NULL} elements.
     * 
     * @param list
     *            {@code List<T>} target list
     * @return {@code NULL} elements {@code List<T>}
     * @since v0.1
     */
    public static <T> List<T> toNotNullElementsList(final List<T> list) {
        final List<T> tmp = toNotNullList(list);
        tmp.removeAll(Collections.singleton(null));
        return tmp;
    }

    /**
     * To get the {@code Not Null List<T>}
     * 
     * @param list
     *            {@code List<T>} target list
     * @return {@code Not Null List<T>}
     * @since v0.1
     */
    public static <T> List<T> toNotNullList(final List<T> list) {
        return Optional.ofNullable(list).isPresent() ? new ArrayList<>(list) : new ArrayList<>();
    }

    /**
     * This list, you can exclude the `string` of the specified {@code excludeStringPattern} at the
     * time of `{@code add(Object),add(int, Object)}.`<br>
     * <b>Other than the *1 method does not support. </b><br>
     * <ul>
     * *1
     * <li>{@link ExcludeList#add(Object)}</li>
     * <li>{@link ExcludeList#add(int, Object)}</li>
     * <li>{@link ExcludeList#addAll(java.util.Collection)}</li>
     * <li>{@link ExcludeList#addAll(int, java.util.Collection)}</li>
     * <li>{@link ExcludeList#get(int)}</li>
     * <li>{@link ExcludeList#size()}</li>
     * </ul>
     *
     * @param <E>
     *            specified class
     * @since v0.1
     */
    public static class ExcludeList<E> extends AbstractList<E> implements List<E>, Serializable {
        private static final long serialVersionUID = 6383432581122292657L;
        final List<E> excludeList;
        final Pattern excludeStringPattern;

        /**
         * Set list, pattern used to this constructor
         * 
         * @param list
         *            the list is target
         * @param p
         *            pattern regex
         * @since v0.1
         */
        public ExcludeList(final List<E> list, final Pattern p) {
            excludeList = toNotNullList(list);
            excludeStringPattern = p;
        }

        /**
         * Inserts the specified element (exclude pattern string).
         * 
         * @since v0.1
         */
        @Override
        public boolean add(final E e) {
            if (isExcludeElement(e)) {
                return false;
            }
            return excludeList.add(e);
        }

        /**
         * Inserts the specified element at the specified position in this list (optional operation)
         * (exclude pattern string).
         * 
         * @since v0.1
         */
        @Override
        public void add(final int index, final E e) {
            if (isExcludeElement(e)) {
                return;
            }
            excludeList.add(index, e);
        }

        /**
         * {@link AbstractList#addAll(Collection)} of (exclude pattern string).
         * 
         * @since v0.1
         */
        @Override
        public boolean addAll(final Collection<? extends E> c) {
            final boolean[] modified = new boolean[] { false };
            c.forEach(e -> {
                if (!isExcludeElement(e) && add(e))
                    modified[0] = true;
            });
            return modified[0];
        }

        /**
         * {@link AbstractList#addAll(int, Collection)} of (exclude pattern string).
         * 
         * @since v0.1
         */
        @Override
        public boolean addAll(final int index, final Collection<? extends E> c) {
            rangeCheckForAdd(index);
            final boolean[] modified = new boolean[] { false };
            final int[] indexBase = new int[] { index };
            c.forEach(e -> {
                if (!isExcludeElement(e)) {
                    add(indexBase[0]++, e);
                    modified[0] = true;
                }
            });
            return modified[0];
        }

        private void rangeCheckForAdd(final int index) {
            if (index < 0 || index > size())
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(final int index) {
            return "Index: " + index + ", Size: " + size();
        }

        private boolean isExcludeElement(final E e) {
            return e instanceof String && excludeStringPattern.matcher(e.toString()).find();
        }

        @Override
        public E get(final int index) {
            return excludeList.get(index);
        }

        @Override
        public int size() {
            return excludeList.size();
        }
    }
}
