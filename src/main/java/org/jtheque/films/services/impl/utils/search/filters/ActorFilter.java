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
import org.jtheque.primary.od.able.Person;
import org.jtheque.utils.collections.Filter;

/**
 * A filter for actors.
 *
 * @author Baptiste Wicht
 */
public final class ActorFilter implements Filter<Film> {
    private final Person actor;

    /**
     * Construct a new <code>ActorFilter</code>.
     *
     * @param actor The actor to search for.
     */
    public ActorFilter(Person actor) {
        super();

        this.actor = actor;
    }

    @Override
    public boolean accept(Film film) {
        return film.containsActor(actor);
    }
}