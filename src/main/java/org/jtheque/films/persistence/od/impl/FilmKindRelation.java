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
 * A relation between a film and an kind.
 *
 * @author Baptiste Wicht
 */
public final class FilmKindRelation {
    private int theKind;
    private int theFilm;

    /**
     * Return the kind of the relation.
     *
     * @return The kind.
     */
    public int getTheKind() {
        return theKind;
    }

    /**
     * Set the kind of the relation.
     *
     * @param theKind The kind.
     */
    public void setTheKind(int theKind) {
        this.theKind = theKind;
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