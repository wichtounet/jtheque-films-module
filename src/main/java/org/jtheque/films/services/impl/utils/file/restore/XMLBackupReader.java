package org.jtheque.films.services.impl.utils.file.restore;

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
import org.jtheque.core.managers.file.able.BackupReader;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.core.utils.db.DaoNotes.NoteType;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.services.impl.utils.DataUtils;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.services.able.IPersonService;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.utils.bean.IntDate;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A reader for JTD Backup.
 *
 * @author Baptiste Wicht
 */
public final class XMLBackupReader implements BackupReader {
    private final Collection<SimpleData> languages = new ArrayList<SimpleData>(10);
    private final Collection<SimpleData> countries = new ArrayList<SimpleData>(10);
    private final Collection<SimpleData> kinds = new ArrayList<SimpleData>(10);
    private final Collection<SimpleData> types = new ArrayList<SimpleData>(10);
    private final Collection<Lending> lendings = new ArrayList<Lending>(10);
    private final Collection<Person> borrowers = new ArrayList<Person>(10);
    private final Collection<Person> realizers = new ArrayList<Person>(20);
    private final Collection<Person> actors = new ArrayList<Person>(50);
    private final Collection<Film> films = new ArrayList<Film>(25);

    @Resource
    private ISimpleDataService countriesService;

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

    private static final DaoNotes DAO_NOTES = DaoNotes.getInstance();

    /**
     * Construct a new XMLBackupReader.
     */
    public XMLBackupReader() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void readBackup(Object object) {
        Element root = (Element) object;

        try {
            readLanguages(root);
            readCountries(root);
            readKinds(root);
            readTypes(root);
            readBorrowers(root);
            readRealizers(root);
            readActors(root);
            readFilms(root);
            readLendings(root);

            for (Film film : films) {
                film.setTheLending(DataUtils.getLendingForFilm(film, lendings));
            }
        } catch (JDOMException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }

    /**
     * Read all the languages in the root element.
     *
     * @param root The root element.
     *
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readLanguages(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//languages/language");

        for (Object languageElement : xpa.selectNodes(root)) {
            SimpleData language = languagesService.getEmptySimpleData();
            language.getTemporaryContext().setId(Integer.parseInt(XPath.newInstance("./id").valueOf(languageElement)));
            language.setName(XPath.newInstance("./name").valueOf(languageElement));

            languages.add(language);

            languagesService.create(language);
        }
    }

    /**
     * Read all the countries in the root element.
     *
     * @param root The root element.
     *
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readCountries(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//countries/country");

        for (Object countryElement : xpa.selectNodes(root)) {
            SimpleData country = countriesService.getEmptySimpleData();
            country.getTemporaryContext().setId(Integer.parseInt(XPath.newInstance("./id").valueOf(countryElement)));
            country.setName(XPath.newInstance("./name").valueOf(countryElement));

            countries.add(country);

            countriesService.create(country);
        }
    }

    /**
     * Read all the kinds in the root element.
     *
     * @param root The root element.
     *
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readKinds(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//kinds/kinds");

        for (Object kindElement : xpa.selectNodes(root)) {
            SimpleData kind = kindsService.getEmptySimpleData();
            kind.getTemporaryContext().setId(Integer.parseInt(XPath.newInstance("./id").valueOf(kindElement)));
            kind.setName(XPath.newInstance("./name").valueOf(kindElement));

            kinds.add(kind);

            kindsService.create(kind);
        }
    }

    /**
     * Read all the types in the root element.
     *
     * @param root The root element.
     *
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readTypes(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//types/type");

        for (Object typeElement : xpa.selectNodes(root)) {
            SimpleData type = typesService.getEmptySimpleData();
            type.getTemporaryContext().setId(Integer.parseInt(XPath.newInstance("./id").valueOf(typeElement)));
            type.setName(XPath.newInstance("./id").valueOf(typeElement));

            types.add(type);

            typesService.create(type);
        }
    }

    /**
     * Read all the borrowers in the root element.
     *
     * @param root The root element.
     *
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readBorrowers(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//borrowers/borrower");

        for (Object borrowerElement : xpa.selectNodes(root)) {
            Person borrower = borrowersService.getEmptyPerson();
            borrower.getTemporaryContext().setId(Integer.parseInt(XPath.newInstance("./id").valueOf(borrowerElement)));
            borrower.setName(XPath.newInstance("./name").valueOf(borrowerElement));
            borrower.setFirstName(XPath.newInstance("./firstname").valueOf(borrowerElement));
            borrower.setEmail(XPath.newInstance("./email").valueOf(borrowerElement));

            borrowers.add(borrower);

            borrowersService.create(borrower);
        }
    }

    /**
     * Read all the realizers in the root element.
     *
     * @param root The root element.
     *
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readRealizers(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//realizers/realizer");

        for (Object realizerElement : xpa.selectNodes(root)) {
            Person realizer = realizersService.getEmptyRealizer();
            realizer.getTemporaryContext().setId(Integer.parseInt(XPath.newInstance("./id").valueOf(realizerElement)));
            realizer.setName(XPath.newInstance("./name").valueOf(realizerElement));
            realizer.setFirstName(XPath.newInstance("./firstname").valueOf(realizerElement));
            realizer.setTheCountry(DataUtils.getDataByTemporaryId(
                    Integer.parseInt(XPath.newInstance("./country").valueOf(realizerElement)), countries));
            realizer.setNote(DAO_NOTES.getNote(NoteType.getEnum(Integer.parseInt(XPath.newInstance("./note").valueOf(realizerElement)))));

            realizers.add(realizer);

            realizersService.create(realizer);
        }
    }

    /**
     * Read all the actors in the root element.
     *
     * @param root The root element.
     *
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readActors(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//actors/actor");

        for (Object actorElement : xpa.selectNodes(root)) {
            Person actor = actorService.getEmptyPerson();
            actor.getTemporaryContext().setId(Integer.parseInt(XPath.newInstance("./id").valueOf(actorElement)));
            actor.setName(XPath.newInstance("./name").valueOf(actorElement));
            actor.setFirstName(XPath.newInstance("./firstname").valueOf(actorElement));
            actor.setTheCountry(DataUtils.getDataByTemporaryId(
                    Integer.parseInt(XPath.newInstance("./country").valueOf(actorElement)), countries));
            actor.setNote(DAO_NOTES.getNote(NoteType.getEnum(Integer.parseInt(XPath.newInstance("./note").valueOf(actorElement)))));

            actors.add(actor);

            actorService.create(actor);
        }
    }

    /**
     * Read all the films in the root element.
     *
     * @param root The root element.
     *
     * @throws JDOMException         This exception is throwed when an error occurs during the xml reading process.
     * @throws NumberFormatException This exception is throwed when an error occurs during the parse process.
     */
    private void readFilms(Element root) throws NumberFormatException, JDOMException {
        XPath xpa = XPath.newInstance("//films/film");

        for (Object filmElement : xpa.selectNodes(root)) {
            Film film = filmsService.getEmptyFilm();

            readValuesOfFilm(filmElement, film);
            readActorsOfFilm(filmElement, film);
            readKindsOfFilm(filmElement, film);

            films.add(film);

            filmsService.create(film);
        }
    }

    /**
     * Read the values of the film from the XML.
     *
     * @param filmElement The XML film element.
     * @param film        The film to fill.
     *
     * @throws JDOMException If an error occurs during the XML reading.
     */
    private void readValuesOfFilm(Object filmElement, Film film) throws JDOMException {
        film.getTemporaryContext().setId(Integer.parseInt(XPath.newInstance("./id").valueOf(filmElement)));
        film.setTitle(XPath.newInstance("./title").valueOf(filmElement));
        film.setTheRealizer(DataUtils.getDataByTemporaryId(
                Integer.parseInt(XPath.newInstance("./realizer").valueOf(filmElement)), realizers));
        film.setTheLanguage(DataUtils.getDataByTemporaryId(
                Integer.parseInt(XPath.newInstance("./language").valueOf(filmElement)), languages));
        film.setTheType(DataUtils.getDataByTemporaryId(
                Integer.parseInt(XPath.newInstance("./type").valueOf(filmElement)), types));
        film.setYear(Integer.parseInt(XPath.newInstance("./year").valueOf(filmElement)));
        film.setDuration(Integer.parseInt(XPath.newInstance("./duration").valueOf(filmElement)));
        film.setNote(DAO_NOTES.getNote(NoteType.getEnum(Integer.parseInt(XPath.newInstance("./note").valueOf(filmElement)))));
        film.setResume(XPath.newInstance("./resume").valueOf(filmElement));
        film.setComment(XPath.newInstance("./comment").valueOf(filmElement));
    }

    /**
     * Read the actors of the film from the XML.
     *
     * @param filmElement The XML film element.
     * @param film        The film to fill.
     *
     * @throws JDOMException If an error occurs during the XML reading.
     */
    private void readActorsOfFilm(Object filmElement, Film film) throws JDOMException {
        XPath xpa = XPath.newInstance(".//actors/actor");

        for (Object actorElement : xpa.selectNodes(filmElement)) {
            Person actor = DataUtils.getDataByTemporaryId(
                    Integer.parseInt(XPath.newInstance(".").valueOf(actorElement)), actors);

            film.addActor(actor);
        }
    }

    /**
     * Read the kinds of the film from the XML.
     *
     * @param filmElement The XML film element.
     * @param film        The film to fill.
     *
     * @throws JDOMException If an error occurs during the XML reading.
     */
    private void readKindsOfFilm(Object filmElement, Film film) throws JDOMException {
        XPath xpa = XPath.newInstance(".//kinds/kind");

        for (Object kindElement : xpa.selectNodes(filmElement)) {
            SimpleData kind = DataUtils.getDataByTemporaryId(
                    Integer.parseInt(XPath.newInstance(".").valueOf(kindElement)), kinds);

            film.addKind(kind);
        }
    }

    /**
     * Read all the lendings in the root element.
     *
     * @param root The root element.
     *
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readLendings(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//lendings/lending");

        for (Object lendingElement : xpa.selectNodes(root)) {
            Lending lending = lendingsService.getEmptyLending();
            lending.getTemporaryContext().setId(Integer.parseInt(XPath.newInstance("./id").valueOf(lendingElement)));
            lending.setDate(new IntDate(Integer.parseInt(XPath.newInstance("./date").valueOf(lendingElement))));
            lending.setThePerson(DataUtils.getDataByTemporaryId(
                    Integer.parseInt(XPath.newInstance("./borrower").valueOf(lendingElement)), borrowers));
            lending.setTheOther(DataUtils.getDataByTemporaryId(
                    Integer.parseInt(XPath.newInstance("./film").valueOf(lendingElement)), films).getId());

            lendings.add(lending);

            lendingsService.create(lending);
        }
    }

    @Override
    public void persistTheData() {
    }
}
