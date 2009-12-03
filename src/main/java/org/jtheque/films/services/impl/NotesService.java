package org.jtheque.films.services.impl;

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

import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.core.utils.db.DaoNotes.NoteType;
import org.jtheque.core.utils.db.Note;
import org.jtheque.films.services.able.INotesService;

/**
 * The implementation of the notes service.
 *
 * @author Baptiste Wicht
 */
public final class NotesService implements INotesService {
    private Note defaultNote;

    private final DaoNotes daoNotes = DaoNotes.getInstance();

    @Override
    public Note getDefaultNote() {
        if (defaultNote == null) {
            defaultNote = daoNotes.getNote(NoteType.UNDEFINED);
        }

        return defaultNote;
    }
}