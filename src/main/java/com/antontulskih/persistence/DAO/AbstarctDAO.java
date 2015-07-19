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
    void showAllById();
    Set<T> getById(final Integer... ids);
    T getById(final Integer id);
    Set<T> getAll();
    boolean update(final T... items);
    boolean remove(final T... items);
    boolean removeById(final Integer... ids);
}
