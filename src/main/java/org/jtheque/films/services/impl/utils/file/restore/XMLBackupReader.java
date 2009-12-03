package org.jtheque.films.services.impl.utils.file.restore;

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

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
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
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.od.able.Language;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.Type;
import org.jtheque.primary.services.able.IBorrowersService;
import org.jtheque.primary.services.able.ICountriesService;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.services.able.ILanguagesService;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.services.able.ITypesService;
import org.jtheque.utils.bean.IntDate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A reader for JTD Backup.
 *
 * @author Baptiste Wicht
 */
public final class XMLBackupReader implements BackupReader {
    private final Collection<Language> languages = new ArrayList<Language>(10);
    private final Collection<Country> countries = new ArrayList<Country>(10);
    private final Collection<Kind> kinds = new ArrayList<Kind>(10);
    private final Collection<Type> types = new ArrayList<Type>(10);
    private final Collection<Lending> lendings = new ArrayList<Lending>(10);
    private final Collection<Person> borrowers = new ArrayList<Person>(10);
    private final Collection<Person> realizers = new ArrayList<Person>(20);
    private final Collection<Person> actors = new ArrayList<Person>(50);
    private final Collection<Film> films = new ArrayList<Film>(25);

    @Resource
    private ICountriesService countriesService;

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
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readLanguages(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//languages/language");

        for (Object languageElement : xpa.selectNodes(root)) {
            Language language = languagesService.getEmptyLanguage();
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
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readCountries(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//countries/country");

        for (Object countryElement : xpa.selectNodes(root)) {
            Country country = countriesService.getEmptyCountry();
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
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readKinds(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//kinds/kinds");

        for (Object kindElement : xpa.selectNodes(root)) {
            Kind kind = kindsService.getEmptyKind();
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
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readTypes(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//types/type");

        for (Object typeElement : xpa.selectNodes(root)) {
            Type type = typesService.getEmptyType();
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
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readBorrowers(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//borrowers/borrower");

        for (Object borrowerElement : xpa.selectNodes(root)) {
            Person borrower = borrowersService.getEmptyBorrower();
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
     * @throws JDOMException This exception is throwed when an error occurs during the xml reading process.
     */
    private void readActors(Element root) throws JDOMException {
        XPath xpa = XPath.newInstance("//actors/actor");

        for (Object actorElement : xpa.selectNodes(root)) {
            Person actor = actorService.getEmptyActor();
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
     * @throws JDOMException If an error occurs during the XML reading.
     */
    private void readKindsOfFilm(Object filmElement, Film film) throws JDOMException {
        XPath xpa = XPath.newInstance(".//kinds/kind");

        for (Object kindElement : xpa.selectNodes(filmElement)) {
            Kind kind = DataUtils.getDataByTemporaryId(
                    Integer.parseInt(XPath.newInstance(".").valueOf(kindElement)), kinds);

            film.addKind(kind);
        }
    }

    /**
     * Read all the lendings in the root element.
     *
     * @param root The root element.
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