package org.jtheque.films.view.impl.models;

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
