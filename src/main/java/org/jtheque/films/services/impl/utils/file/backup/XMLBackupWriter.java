package org.jtheque.films.services.impl.utils.file.backup;

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
import org.jtheque.core.managers.file.able.BackupWriter;
import org.jtheque.core.utils.file.XMLWriter;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.services.able.IPersonService;
import org.jtheque.primary.services.able.ISimpleDataService;

import javax.annotation.Resource;

/**
 * A XML Backup Writer.
 *
 * @author Baptiste Wicht
 */
public final class XMLBackupWriter implements BackupWriter {
    @Resource
    private ILendingsService lendingsService;

    @Resource
    private ISimpleDataService typesService;

    @Resource
    private ISimpleDataService languagesService;

    @Resource
    private IPersonService borrowersService;

    @Resource
    private IFilmsService filmsService;

    @Resource
    private IActorService actorService;

    @Resource
    private IRealizersService realizersService;

    @Resource
    private ISimpleDataService kindsService;

    /**
     * Construct a new XMLBackupWriter.
     */
    public XMLBackupWriter() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void write(Object object) {
        XMLWriter writer = (XMLWriter) object;

        writeFilms(writer);
        writePersons(writer, "actor", actorService.getPersons());
        writePersons(writer, "realizer", realizersService.getRealizers());
        writeKinds(writer);
        writeTypes(writer);
        writeLanguages(writer);
        writeCountries(writer);
        writeBorrowers(writer);
        writeLendings(writer);
    }

    /**
     * Write all the films in the Document.
     *
     * @param writer The document in which add the header.
     */
    private void writeFilms(XMLWriter writer) {
        writer.add("films");

        for (Film film : filmsService.getFilms()) {
            writer.add("film");

            writer.addOnly("id", Integer.toString(film.getId()));
            writer.addOnly("title", film.getTitle());

            addKindsOfFilm(writer, film);

            writer.addOnly("type", film.hasType() ? film.getTheType().getId() : -2);
            writer.addOnly("realizer", film.hasRealizer() ? film.getTheRealizer().getId() : -2);
            writer.addOnly("language", film.hasLanguage() ? film.getTheLanguage().getId() : -2);

            writer.addOnly("year", film.getYear());
            writer.addOnly("duration", film.getDuration());
            writer.addOnly("note", film.getNote().getValue().intValue());
            writer.addOnly("resume", film.getResume());
            writer.addOnly("comment", film.getComment());

            addActorsOfFilm(writer, film);

            writer.switchToParent();
        }

        writer.switchToParent();
    }

    /**
     * Add the kinds of the film to the XML file.
     *
     * @param writer The XML writer.
     * @param film   The film to get the informations from.
     */
    private static void addKindsOfFilm(XMLWriter writer, Film film) {
        writer.add("kinds");

        for (SimpleData kind : film.getKinds()) {
            writer.addOnly("kind", kind.getId());
        }

        writer.switchToParent();
    }

    /**
     * Add the actors of the film to the XML file.
     *
     * @param writer The XML writer.
     * @param film   The film to get the informations from.
     */
    private static void addActorsOfFilm(XMLWriter writer, Film film) {
        writer.add("actors");

        for (Person actor : film.getActors()) {
            writer.addOnly("actor", actor.getId());
        }

        writer.switchToParent();
    }

    /**
     * Write all the actors in the Document.
     *
     * @param writer   The document in which we write.
     * @param category The category id.
     * @param persons  The persons to write.
     */
    private static void writePersons(XMLWriter writer, String category, Iterable<? extends Person> persons) {
        writer.add(category + 's');

        for (Person person : persons) {
            writer.add(category);

            writer.addOnly("id", person.getId());
            writer.addOnly("name", person.getName());
            writer.addOnly("firstname", person.getFirstName());
            writer.addOnly("country", person.getTheCountry().getId());
            writer.addOnly("note", person.getNote().getValue().intValue());

            writer.switchToParent();
        }

        writer.switchToParent();
    }

    /**
     * Write all the languages to the Document.
     *
     * @param writer The Document in which we write
     */
    private void writeLanguages(XMLWriter writer) {
        writer.add("languages");

        for (SimpleData language : languagesService.getDatas()) {
            writer.add("language");

            writer.addOnly("id", language.getId());
            writer.addOnly("name", language.getName());

            writer.switchToParent();
        }

        writer.switchToParent();
    }

    /**
     * Write all the kinds to the Document.
     *
     * @param writer The Document in which we write
     */
    private void writeKinds(XMLWriter writer) {
        writer.add("kinds");

        for (SimpleData kind : kindsService.getDatas()) {
            writer.add("kind");

            writer.addOnly("id", kind.getId());
            writer.addOnly("name", kind.getName());

            writer.switchToParent();
        }

        writer.switchToParent();
    }

    /**
     * Write all the kinds to the Document.
     *
     * @param writer The Document in which we write
     */
    private void writeTypes(XMLWriter writer) {
        writer.add("types");

        for (SimpleData type : typesService.getDatas()) {
            writer.add("type");

            writer.addOnly("id", type.getId());
            writer.addOnly("name", type.getName());

            writer.switchToParent();
        }

        writer.switchToParent();
    }

    /**
     * Write all the countries to the Document.
     *
     * @param writer The Document in which we write
     */
    private void writeCountries(XMLWriter writer) {
        writer.add("countries");

        for (SimpleData type : typesService.getDatas()) {
            writer.add("country");

            writer.addOnly("id", type.getId());
            writer.addOnly("name", type.getName());

            writer.switchToParent();
        }

        writer.switchToParent();
    }

    /**
     * Write all borrowers in the Document.
     *
     * @param writer The document in which we write.
     */
    private void writeBorrowers(XMLWriter writer) {
        writer.add("borrowers");

        for (Person borrower : borrowersService.getPersons()) {
            writer.add("borrower");

            writer.addOnly("id", borrower.getId());
            writer.addOnly("name", borrower.getName());
            writer.addOnly("firstname", borrower.getFirstName());
            writer.addOnly("email", borrower.getEmail());

            writer.switchToParent();
        }

        writer.switchToParent();
    }

    /**
     * Write all lendings in the Document.
     *
     * @param writer The document in which we write.
     */
    private void writeLendings(XMLWriter writer) {
        writer.add("lendings");

        for (Lending lending : lendingsService.getLendings()) {
            writer.add("lending");

            writer.addOnly("id", lending.getId());
            writer.addOnly("date", lending.getDate().intValue());
            writer.addOnly("borrower", lending.getThePerson().getId());
            writer.addOnly("email", lending.getTheOther());

            writer.switchToParent();
        }

        writer.switchToParent();
    }
}
