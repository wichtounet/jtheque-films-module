package org.jtheque.films.controllers.impl.undo;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;

/**
 * An edit for a creation of film.
 *
 * @author Baptiste Wicht
 */
public final class CreatedFilmEdit extends AbstractUndoableEdit {
    private final Film film;

    @Resource
    private IFilmsService filmsService;

    /**
     * Construct a new CreatedFilmEdit.
     *
     * @param film The created film.
     */
    public CreatedFilmEdit(Film film) {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        this.film = film;
    }

    @Override
    public void undo() {
        super.undo();

        filmsService.delete(film);
    }

    @Override
    public void redo() {
        super.redo();

        filmsService.create(film);
    }

    @Override
    public String getPresentationName() {
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.create");
    }
}
