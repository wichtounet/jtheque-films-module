package org.jtheque.films.services.impl.utils.search;

import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.collections.Filter;

import java.util.ArrayList;
import java.util.Collection;

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

/**
 * A data searcher. It search on a selected Collection using some filters.
 *
 * @author Baptiste Wicht
 * @param <T> The type of data to search.
 */
public final class DataSearcher<T> implements Searcher<T> {
    private final Collection<Filter<T>> filters = new ArrayList<Filter<T>>(10);

    @Override
    public void addFilter(Filter<T> filter) {
        filters.add(filter);
    }

    @Override
    public Collection<T> search(Collection<T> datas) {
        Collection<T> filteredCopy = new ArrayList<T>(datas);

        for (Filter<T> filter : filters) {
            CollectionUtils.filter(filteredCopy, filter);
        }

        return filteredCopy;
    }
}