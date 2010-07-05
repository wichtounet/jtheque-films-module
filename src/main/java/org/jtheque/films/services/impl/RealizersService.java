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
