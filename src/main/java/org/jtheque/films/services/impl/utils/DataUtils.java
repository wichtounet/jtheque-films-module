package org.jtheque.films.services.impl.utils;

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

import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Lending;

/**
 * An utility class for Data.
 *
 * @author Baptiste Wicht
 */
public final class DataUtils {
    /**
     * Construct a new DataUtils. This class isn't instanciable.
     */
    private DataUtils() {
        super();
    }

    /**
     * Return the data with the searched temporary id in the collection.
     *
     * @param id         The id searched.
     * @param collection The collection in which we must search.
     * @param <T>        The specialized type of Data.
     *
     * @return The good data or <code>null</code> if we doesn't find it.
     */
    public static <T extends Data> T getDataByTemporaryId(int id, Iterable<T> collection) {
        for (T data : collection) {
            if (data.getTemporaryContext().getId() == id) {
                return data;
            }
        }

        return null;
    }

    /**
     * Return the lending for the specified film.
     *
     * @param film       The film for which we want the lending.
     * @param collection The collection of lendings.
     *
     * @return The good lending or <code>null</code> if we don't find one.
     */
    public static Lending getLendingForFilm(Film film, Iterable<Lending> collection) {
        for (Lending lending : collection) {
            if (lending.getTheOther() == film.getId()) {
                return lending;
            }
        }

        return null;
    }
}
