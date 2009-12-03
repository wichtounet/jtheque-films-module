package org.jtheque.films.controllers.impl;

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

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.IFilmographyController;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.impl.utils.Filmography;
import org.jtheque.films.view.able.IFilmographyView;
import org.jtheque.primary.od.able.Person;

import javax.annotation.Resource;

/**
 * A filmography controller.
 *
 * @author Baptiste Wicht
 */
public final class FilmographyController extends AbstractController implements IFilmographyController {
    @Resource
    private IActorService actorService;

    @Resource
    private IFilmographyView filmographyView;

    @Override
    public void displayFilmographyForActor(Person actor) {
        setFilmography(actorService.getFilmography(actor));
        displayView();
    }

    @Override
    public IFilmographyView getView() {
        return filmographyView;
    }

    /**
     * Set the filmography.
     *
     * @param filmo The new filmography to set.
     */
    private void setFilmography(Filmography filmo) {
        filmographyView.sendMessage("filmo", filmo);
    }
}