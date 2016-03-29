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
     * To count the list removed by {@code excludes} list
     * 
     * @param list
     *            {@code List<T>} target list
     * @param excludes
     *            {@code List<T>} Removing excludes list
     * @param isNotNull
     *            {@code true} if NOT NULL (target list)
     * @return size of target list
     * @since v0.1
     */
    public static <T> int countList(
            final List<T> list,
            final List<T> excludes,
            final boolean isNotNull) {
        return Optional.ofNullable(
                Optional.ofNullable(list).isPresent() ? new ArrayList<>(list) : new ArrayList<>()).map(
                e -> {
                    Optional.ofNullable(excludes).ifPresent(
                            optionalExcludes -> optionalExcludes.forEach(exclude -> e.removeAll(Collections.singleton(exclude))));
                    if (isNotNull)
                        e.removeAll(Collections.singleton(null));
                    return e;
                }).get().size();
    }
}
