package org.jtheque.films.services.impl.utils.search.filters;

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
import org.jtheque.primary.od.able.Type;
import org.jtheque.utils.collections.Filter;

/**
 * A filter for type.
 *
 * @author Baptiste Wicht
 */
public final class TypeFilter implements Filter<Film> {
    private final Type type;

    /**
     * Construct a new TypeFilter.
     *
     * @param type The type to filter for.
     */
    public TypeFilter(Type type) {
        super();

        this.type = type;
    }

    @Override
    public boolean accept(Film person) {
        return type.equals(person.getTheType());
    }
}