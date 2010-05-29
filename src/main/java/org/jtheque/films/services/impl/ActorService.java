package org.jtheque.films.services.impl;

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

	public ActorService(){
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