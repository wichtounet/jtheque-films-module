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

import org.jtheque.core.managers.persistence.context.TemporaryContext;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Temporary context of film.
 *
 * @author Baptiste Wicht
 */
public final class FilmTemporaryContext extends TemporaryContext {
    private int intNote;
    private int lending;
    private int type;
    private int language;
    private int realizer;
    private Collection<Integer> actors;
    private Collection<Integer> kinds;

    /**
     * Return the temporary note of the film.
     *
     * @return The temporary note.
     */
    public int getIntNote() {
        return intNote;
    }

    /**
     * Sets the temporary note. The temporary note is used while importation to link note and
     * film when there are not completely loaded.
     *
     * @param intNote The temporary note.
     */
    public void setTemporaryIntNote(int intNote) {
        this.intNote = intNote;
    }

    /**
     * Return the temporary lending id.
     *
     * @return The temporary lending id.
     */
    public int getLending() {
        return lending;
    }

    /**
     * Sets the temporary lending id.
     *
     * @param lending the temporary lending id
     */
    public void setLending(int lending) {
        this.lending = lending;
    }

    /**
     * Return the temporary actors ids.
     *
     * @return The temporary actors ids.
     */
    public Iterable<Integer> getActors() {
        return actors;
    }

    /**
     * Sets the temporary actors ids.
     *
     * @param actors the temporary actors ids
     */
    public void setActors(Collection<Integer> actors) {
        this.actors = new ArrayList<Integer>(actors);
    }

    /**
     * Return the kinds of the temporary context.
     *
     * @return A Collection containing all the kinds of the temporary context.
     */
    public Iterable<Integer> getKinds() {
        return kinds;
    }

    /**
     * Set the kinds of the temporary context.
     *
     * @param kinds The kinds of the temporary context.
     */
    public void setKinds(Collection<Integer> kinds) {
        this.kinds = new ArrayList<Integer>(kinds);
    }

    /**
     * Return the temporary language id.
     *
     * @return The temporary language id.
     */
    public int getLanguage() {
        return language;
    }

    /**
     * Sets the temporary language id.
     *
     * @param language the temporary language id
     */
    public void setLanguage(int language) {
        this.language = language;
    }

    /**
     * Return the temporary realizer id.
     *
     * @return The temporary realizer id.
     */
    public int getRealizer() {
        return realizer;
    }

    /**
     * Sets the temporary lending id.
     *
     * @param realizer the temporary lending id
     */
    public void setRealizer(int realizer) {
        this.realizer = realizer;
    }

    /**
     * Return the temporary type id.
     *
     * @return The temporary type id.
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the temporary type id.
     *
     * @param type the temporary type id
     */
    public void setType(int type) {
        this.type = type;
    }
}