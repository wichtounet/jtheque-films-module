package org.jtheque.films.view.impl.actions.publication;

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
import org.jtheque.films.controllers.able.IPublicationController;
import org.jtheque.films.services.able.IPublicationService;
import org.jtheque.films.services.impl.utils.file.FTPConnectionInfos;
import org.jtheque.films.view.able.IPublicationView;

import java.awt.event.ActionEvent;

/**
 * An action to validate the saga view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidatePublicationView extends JThequeAction {
    /**
     * Construct a AcValidateSagaView.
     */
    public AcValidatePublicationView() {
        super("publication.view.actions.validate");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IPublicationView view = Managers.getManager(IBeansManager.class).<IPublicationController>getBean("publicationController").getView();

        if (view.validateContent()) {
            FTPConnectionInfos infos = view.getConnectionInfos();

            Managers.getManager(IBeansManager.class).<IPublicationService>getBean("publicationService").uploadListOfFilms(infos);

            view.closeDown();
        }
    }
}