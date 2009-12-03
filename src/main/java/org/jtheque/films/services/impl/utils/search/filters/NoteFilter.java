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

import org.jtheque.core.utils.db.Note;
import org.jtheque.primary.od.able.Notable;
import org.jtheque.utils.collections.Filter;

/**
 * A filter for note.
 *
 * @author Baptiste Wicht
 * @param <T> The type of data to filter.
 */
public final class NoteFilter<T extends Notable> implements Filter<T> {
    private final Note note;

    /**
     * Construct a new NoteFilter.
     *
     * @param note The note to filter for.
     */
    public NoteFilter(Note note) {
        super();

        this.note = note;
    }

    @Override
    public boolean accept(T person) {
        return person.getNote().equals(note);
    }
}