package org.jtheque.films.services.impl.utils;

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