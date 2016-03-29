package pollseed.util.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * List class Extension (for updating java7 to java8).
 * 
 * @version java8
 */
public class ListEx {

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
        return size(Optional.ofNullable(toNotNullList(list)).map(
                e -> {
                    Optional.ofNullable(excludes).ifPresent(
                            optionalExcludes -> optionalExcludes.forEach(exclude -> e.removeAll(Collections.singleton(exclude))));
                    return toNotNullElementsList(e, isNotNull);
                }));
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
}
