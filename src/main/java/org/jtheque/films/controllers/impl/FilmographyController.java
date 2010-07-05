package org.jtheque.films.controllers.impl;

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
