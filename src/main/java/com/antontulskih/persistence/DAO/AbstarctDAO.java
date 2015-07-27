/**
 * @{NAME}
 *
 * ${DATE}
 *
 * @author Tulskih Anton
 */

package com.antontulskih.persistence.DAO;

import java.util.Set;

public interface AbstarctDAO<T> {
    boolean save(final T... items);
    Set<T> getByIds(final Integer... ids);
    T getById(final Integer id);
    Set<T> getAllSortedById();
    boolean update(final T... items);
    boolean remove(final T... items);
    boolean removeAll();
    boolean removeByIds(final Integer... ids);
}
