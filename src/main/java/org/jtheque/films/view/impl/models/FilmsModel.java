package org.jtheque.films.view.impl.models;

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
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.impl.models.able.IFilmsModel;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.PrincipalDataModel;

import java.util.Collection;

/**
 * A model for the films view.
 *
 * @author Baptiste Wicht
 */
public final class FilmsModel extends PrincipalDataModel<Film> implements IFilmsModel {
    private Film currentFilm;

    /**
     * Init the view.
     */
    public FilmsModel() {
        super();

        getFilmsService().addDataListener(this);
    }

    @Override
    public Collection<Film> getDatas() {
        return getFilmsService().getDatas();
    }

    @Override
    public Film getCurrentFilm() {
        return currentFilm;
    }

    @Override
    public void setCurrentFilm(Film currentFilm) {
        this.currentFilm = currentFilm;

        fireCurrentObjectChanged(new ObjectChangedEvent(this, currentFilm));
    }

    /**
     * Return the service for the films.
     *
     * @return The service for the films.
     */
    private static IFilmsService getFilmsService() {
        return Managers.getManager(IBeansManager.class).getBean("filmsService");
    }
}