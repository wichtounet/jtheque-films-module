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
import org.jtheque.utils.collections.Filter;

/**
 * A filter of Duration.
 *
 * @author Baptiste Wicht
 */
public final class DurationFilter implements Filter<Film> {
    private final int from;
    private final int to;

    /**
     * Create a new DurationFilter.
     *
     * @param from The from duration.
     * @param to   The to duration.
     */
    public DurationFilter(int from, int to) {
        super();

        this.from = from;
        this.to = to;
    }

    @Override
    public boolean accept(Film person) {
        return person.getDuration() >= from && person.getDuration() <= to;
    }
}