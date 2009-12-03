package org.jtheque.films.services.impl.utils.search;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.utils.collections.Filter;

import java.util.Collection;

/**
 * Represents a Searcher. It's an object who can execute a research on a specific type of data.
 *
 * @author Baptiste Wicht
 * @param <T> The type of Object we search
 */
public interface Searcher<T> {
    /**
     * Add a filter to the searcher.
     *
     * @param filter The filter to add the to the searcher.
     */
    void addFilter(Filter<T> filter);

    /**
     * Search the results of the search.
     *
     * @param datas The datas
     * @return The results
     */
    Collection<T> search(Collection<T> datas);
}