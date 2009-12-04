package org.jtheque.films.services.impl.utils.file.backup;

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
import org.jtheque.core.managers.file.able.BackupWriter;
import org.jtheque.core.utils.file.XMLWriter;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.od.able.Language;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.Type;
import org.jtheque.primary.services.able.IBorrowersService;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.services.able.ILanguagesService;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.services.able.ITypesService;

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
    private ITypesService typesService;

    @Resource
    private ILanguagesService languagesService;

    @Resource
    private IBorrowersService borrowersService;

    @Resource
    private IFilmsService filmsService;

    @Resource
    private IActorService actorService;

    @Resource
    private IRealizersService realizersService;

    @Resource
    private IKindsService kindsService;

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
        writePersons(writer, "actor", actorService.getActors());
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

        for (Kind kind : film.getKinds()) {
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

        for (Language language : languagesService.getLanguages()) {
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

        for (Kind kind : kindsService.getKinds()) {
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

        for (Type type : typesService.getTypes()) {
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

        for (Type type : typesService.getTypes()) {
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

        for (Person borrower : borrowersService.getBorrowers()) {
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