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

import java.util.ArrayList;
import java.util.Collection;

/**
 * A filmography. A filmography contain an actor and a list of films in which the actor has played.
 *
 * @author Baptiste Wicht
 */
public final class Filmography {
    /**
     * The String representation of the actor.
     */
    private String actor;

    /**
     * The films on which the actors has played.
     */
    private Collection<String> films;

    /**
     * Return the films of the filmography.
     *
     * @return A List containing all the films of the filmography.
     */
    public Iterable<String> getFilms() {
        return films;
    }

    /**
     * Set the films of the filmography.
     *
     * @param films The new List of films to set
     */
    public void setFilms(Collection<String> films) {
        this.films = new ArrayList<String>(films);
    }

    /**
     * Set the actor of the filmography.
     *
     * @param actor The new actor to set.
     */
    public void setActor(String actor) {
        this.actor = actor;
    }

    /**
     * Return the actor of the filmography.
     *
     * @return The actor.
     */
    public String getActor() {
        return actor;
    }
}