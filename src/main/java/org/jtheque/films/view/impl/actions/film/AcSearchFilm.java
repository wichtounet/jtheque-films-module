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
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.controllers.able.ISearchController;
import org.jtheque.films.utils.Constants;

import java.awt.event.ActionEvent;

/**
 * Action to search films.
 *
 * @author Baptiste Wicht
 */
public final class AcSearchFilm extends JThequeAction {
    /**
     * Construct a new AcSearchFilm.
     */
    public AcSearchFilm() {
        super("jtheque.actions.search.film");

        setIcon(Managers.getManager(IResourceManager.class).getIcon(Constants.IMAGE_BASE_NAME, "search", ImageType.PNG));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ISearchController searchController = Managers.getManager(IBeansManager.class).getBean("filmSearchController");

        searchController.setResearchController(Managers.getManager(IBeansManager.class).<IFilmController>getBean("filmController"));
        searchController.displayView();
    }
}