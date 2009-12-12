package org.jtheque.films.view.impl.actions.auto.add;

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
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IAutoAddController;

import java.awt.event.ActionEvent;

/**
 * Action to auto add a film.
 *
 * @author Baptiste Wicht
 */
public final class AcAutoAddFilm extends JThequeAction {
    /**
     * Construct a new AcAutoAddFilm.
     */
    public AcAutoAddFilm() {
        super("film.view.actions.add.auto");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Managers.getManager(IBeansManager.class).<IAutoAddController>getBean("autoAddController").add();
    }
}