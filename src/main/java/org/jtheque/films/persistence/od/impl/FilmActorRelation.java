package org.jtheque.films.persistence.od.impl;

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
 * A relation between a film and an actor.
 *
 * @author Baptiste Wicht
 */
public final class FilmActorRelation {
    private int theActor;
    private int theFilm;

    /**
     * Return the actor of the relation.
     *
     * @return The actor.
     */
    public int getTheActor() {
        return theActor;
    }

    /**
     * Set the actor of the relation.
     *
     * @param theActor The actor.
     */
    public void setTheActor(int theActor) {
        this.theActor = theActor;
    }

    /**
     * Return the film of the relation.
     *
     * @return The film.
     */
    public int getTheFilm() {
        return theFilm;
    }

    /**
     * Set the film of the relation.
     *
     * @param theFilm The film.
     */
    public void setTheFilm(int theFilm) {
        this.theFilm = theFilm;
    }
}