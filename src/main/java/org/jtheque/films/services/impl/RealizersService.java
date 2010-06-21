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
import org.jtheque.films.services.able.INotesService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.primary.dao.able.IDaoPersons;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.ISimpleDataService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Collection;

/**
 * The implementation of the realizers service.
 *
 * @author Baptiste Wicht
 */
@Service
public final class RealizersService implements IRealizersService {
    @Resource
    private IDaoPersons daoPersons;

    @Resource
    private INotesService notesService;

    @Resource
    private ISimpleDataService countriesService;

    private Person defaultRealizer;

    private static final String DEFAULT_REALIZER = "Unknown";

    @Override
    public Collection<Person> getRealizers() {
        return daoPersons.getPersons(PERSON_TYPE);
    }

    @Override
    @Transactional
    public boolean delete(Person realizer) {
        return daoPersons.delete(realizer);
    }

    @Override
    public boolean exists(String firstName, String name) {
        return daoPersons.exists(firstName, name, PERSON_TYPE);
    }

    @Override
    public Person getRealizer(String firstName, String name) {
        return daoPersons.getPerson(firstName, name, PERSON_TYPE);
    }

    @Override
    @Transactional
    public void create(Person realizer) {
        daoPersons.create(realizer);
    }

    @Override
    public boolean exists(Person realizer) {
        return daoPersons.exist(realizer);
    }

    @Override
    public boolean hasNoRealizers() {
        return getRealizers().isEmpty();
    }

    @Override
    @Transactional
    public void save(Person realizer) {
        daoPersons.save(realizer);
    }

    @Override
    public Collection<Person> getDatas() {
        return daoPersons.getPersons(PERSON_TYPE);
    }

    @Override
    public void addDataListener(DataListener listener) {
        daoPersons.addDataListener(listener);
    }

    @Override
    public Person getEmptyRealizer() {
        return daoPersons.createPerson();
    }

    @Override
    @Transactional
    public Person getDefaultRealizer() {
        if (defaultRealizer == null) {
            defaultRealizer = daoPersons.getPerson(DEFAULT_REALIZER, DEFAULT_REALIZER, PERSON_TYPE);

            if (defaultRealizer == null) {
                defaultRealizer = getEmptyRealizer();

                defaultRealizer.setName(DEFAULT_REALIZER);
                defaultRealizer.setFirstName(DEFAULT_REALIZER);
                defaultRealizer.setNote(notesService.getDefaultNote());
                defaultRealizer.setTheCountry(countriesService.getDefaultSimpleData());
                daoPersons.create(defaultRealizer);
            }
        }

        return defaultRealizer;
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