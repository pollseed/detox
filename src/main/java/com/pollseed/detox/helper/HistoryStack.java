package com.pollseed.detox.helper;

import java.io.Serializable;
import java.util.Stack;

/**
 * History management class.
 *
 * @param <E> specific type
 * @author pollseed
 */
public class HistoryStack<E> extends Stack<E> implements Serializable {
    private static final long serialVersionUID = 930474834541339165L;

    private final E defaultPage;

    private E location;

    private E history;

    /**
     * Setting default page.
     *
     * @param defaultPage specific home page
     */
    public HistoryStack(final E defaultPage) {
        this.defaultPage = defaultPage;
        this.location = defaultPage;
    }

    /**
     * Go to the next page.
     *
     * @param item the next page
     * @return the next page
     */
    public E next(final E item) {
        this.history = this.location;
        this.location = push(item);
        return this.location;
    }

    /**
     * Go to the back page.
     *
     * @return the back page
     */
    public E back() {
        if (elementCount > 0) {
            pop();
        }
        this.location = this.history;
        if (elementCount <= 1) {
            this.history = this.defaultPage;
        }
        return this.location;
    }

    /**
     * Go to the home page.
     *
     * @return the home page
     */
    public E home() {
        if (elementCount > 0) {
            this.history = this.location;
        }
        this.location = this.defaultPage;
        return this.location;
    }

    @Deprecated
    @Override
    public E push(final E item) {
        return super.push(item);
    }

    @Deprecated
    @Override
    public E pop() {
        return super.pop();
    }

    @Deprecated
    @Override
    public E peek() {
        return super.peek();
    }
}
