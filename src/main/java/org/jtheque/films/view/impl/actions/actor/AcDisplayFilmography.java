package org.jtheque.films.view.impl.actions.actor;

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
import org.jtheque.films.controllers.able.IFilmographyController;
import org.jtheque.films.view.able.IActorView;
import org.jtheque.films.view.impl.models.able.IActorsModel;

import java.awt.event.ActionEvent;

/**
 * An action to display the filmography of the current actor.
 *
 * @author Baptiste Wicht
 */
public final class AcDisplayFilmography extends JThequeAction {
    /**
     * Construct a new AcDisplayFilmography.
     */
    public AcDisplayFilmography() {
        super("actor.view.actions.filmography");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        IActorsModel model = Managers.getManager(IBeansManager.class).<IActorView>getBean("actorView").getModel();

        Managers.getManager(IBeansManager.class).<IFilmographyController>getBean("filmographyController").displayFilmographyForActor(model.getCurrentActor());
    }
}