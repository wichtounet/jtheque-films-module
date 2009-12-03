package org.jtheque.films.controllers.impl.undo;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * An edit for a deletion of film.
 *
 * @author Baptiste Wicht
 */
public final class DeletedFilmEdit extends AbstractUndoableEdit {
    private static final long serialVersionUID = -3037357344759861059L;

    private final Film film;

    @Resource
    private IFilmsService filmsService;

    /**
     * Construct a new DeletedFilmEdit.
     *
     * @param film The deleted film.
     */
    public DeletedFilmEdit(Film film) {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        this.film = film;
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();

        filmsService.create(film);
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();

        filmsService.delete(film);
    }

    @Override
    public String getPresentationName() {
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.delete");
    }
}