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

import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.INotesService;
import org.jtheque.films.services.impl.utils.Filmography;
import org.jtheque.films.view.impl.fb.IPersonFormBean;
import org.jtheque.primary.dao.able.IDaoPersons;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.ICountriesService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The implementation of the actor service.
 *
 * @author Baptiste Wicht
 */
public final class ActorService implements IActorService {
    private Person emptyActor;

    @Resource
    private ICountriesService countriesService;

    @Resource
    private INotesService notesService;

    @Resource
    private IDaoPersons daoPersons;

    @Resource
    private IFilmsService filmsService;


    @Override
    @Transactional
    public void edit(Person actor, IPersonFormBean infos) {
        actor.setName(infos.getName());
        actor.setFirstName(infos.getFirstName());
        actor.setNote(infos.getNote());
        actor.setTheCountry(infos.getCountry());

        daoPersons.save(actor);
    }

    @Override
    @Transactional
    public void create(Person actor) {
        actor.setType(PERSON_TYPE);

        daoPersons.create(actor);
    }

    @Override
    @Transactional
    public void save(Person actor) {
        actor.setType(PERSON_TYPE);

        daoPersons.save(actor);
    }

    @Override
    @Transactional
    public boolean delete(Person actor) {
        return daoPersons.delete(actor);
    }

    @Override
    public boolean hasNoActor() {
        return getActors().isEmpty();
    }

    @Override
    public Collection<Person> getActors() {
        return daoPersons.getPersons(PERSON_TYPE);
    }

    @Override
    public boolean exist(Person actor) {
        return daoPersons.exist(actor);
    }

    @Override
    public Person getActor(String firstName, String name) {
        return daoPersons.getPerson(firstName, name, PERSON_TYPE);
    }

    @Override
    public boolean exist(String firstName, String name) {
        return daoPersons.exists(firstName, name, PERSON_TYPE);
    }

    @Override
    public Collection<Person> getDatas() {
        return getActors();
    }

    @Override
    public void addDataListener(DataListener listener) {
        daoPersons.addDataListener(listener);
    }

    @Override
    public Person getDefaultActor() {
        if (emptyActor == null) {
            emptyActor = getEmptyActor();

            emptyActor.setName("");
            emptyActor.setFirstName("");
            emptyActor.setNote(notesService.getDefaultNote());
            emptyActor.setTheCountry(countriesService.getDefaultCountry());
        }

        return emptyActor;
    }

    @Override
    public Person getEmptyActor() {
        Person person = daoPersons.createPerson();

        person.setType(PERSON_TYPE);

        return person;
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

    @Override
    @Transactional
    public void clearAll() {
        daoPersons.clearAll();
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }
}