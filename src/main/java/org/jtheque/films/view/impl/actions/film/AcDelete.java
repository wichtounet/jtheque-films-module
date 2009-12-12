package org.jtheque.films.view.impl.actions.film;

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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IFilmController;

import java.awt.event.ActionEvent;

/**
 * Action to delete a film.
 *
 * @author Baptiste Wicht
 */
public final class AcDelete extends JThequeAction {
    /**
     * Construct a new AcDelete.
     */
    public AcDelete() {
        super("generic.view.actions.delete");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final boolean yes = Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("film.dialogs.confirmDelete",
                        Managers.getManager(IBeansManager.class).<IFilmController>getBean("filmController").getViewModel().getCurrentFilm().getDisplayableText()),
                Managers.getManager(ILanguageManager.class).getMessage("film.dialogs.confirmDelete.title"));

        if (yes) {
            Managers.getManager(IBeansManager.class).<IFilmController>getBean("filmController").deleteCurrentFilm();
        }
    }
}