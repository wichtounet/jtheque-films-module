package org.jtheque.films.persistence.od.impl;

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
     * Sets the temporary note. The temporary note is used while importation to link note and film when there are not
     * completely loaded.
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
