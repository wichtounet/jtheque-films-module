package org.jtheque.films.services.impl.utils.search;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
     *
     * @return The results
     */
    Collection<T> search(Collection<T> datas);
}
