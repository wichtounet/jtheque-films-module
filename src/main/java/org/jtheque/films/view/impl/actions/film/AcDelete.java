package org.jtheque.films.view.impl.actions.film;

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
            Managers.getManager(IBeansManager.class).<IFilmController>getBean("filmController").deleteCurrent();
        }
    }
}
