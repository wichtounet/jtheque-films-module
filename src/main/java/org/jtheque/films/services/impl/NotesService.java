package org.jtheque.films.services.impl;

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
