package org.jtheque.films.services.impl;

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

import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.impl.utils.Filmography;
import org.jtheque.primary.dao.able.IDaoPersons;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.impl.PersonService;
import org.jtheque.primary.view.able.fb.IPersonFormBean;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The implementation of the actor service.
 *
 * @author Baptiste Wicht
 */
public final class ActorService extends PersonService implements IActorService {
    @Resource
    private IDaoPersons daoPersons;

    @Resource
    private IFilmsService filmsService;

    public ActorService() {
        super(PERSON_TYPE, DATA_TYPE);
    }

    @Override
    @Transactional
    public void edit(Person actor, IPersonFormBean infos) {
        infos.fillPerson(actor);

        daoPersons.save(actor);
    }

    @Override
    public Filmography getFilmography(Person actor) {
        Filmography filmo = new Filmography();

        filmo.setActor(actor.getDisplayableText());

        Collection<String> films = new ArrayList<String>(15);

        for (Film film : filmsService.getFilms()) {
            if (film.containsActor(actor)) {
                films.add(film.getTitle());
            }
        }

        filmo.setFilms(films);

        return filmo;
    }
}
