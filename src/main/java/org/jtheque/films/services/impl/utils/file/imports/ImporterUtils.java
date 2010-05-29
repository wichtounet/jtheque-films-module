package org.jtheque.films.services.impl.utils.file.imports;

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

import javax.annotation.Resource;

/**
 * Utils for the importer.
 *
 * @author Baptiste Wicht
 */
public final class ImporterUtils {
    @Resource
    private static ISimpleDataService countriesService;

    @Resource
    private static ILendingsService lendingsService;

    @Resource
    private static ISimpleDataService typesService;

    @Resource
    private static ISimpleDataService languagesService;

    @Resource
    private static IPersonService borrowersService;

    @Resource
    private static IFilmsService filmsService;

    @Resource
    private static IActorService actorService;

    @Resource
    private static IRealizersService realizersService;

    @Resource
    private static ISimpleDataService kindsService;

    private static final DaoNotes DAO_NOTES = DaoNotes.getInstance();

    /**
     * Construct a new ImporterUtils.
     */
    private ImporterUtils() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    /**
     * Persist the data of an import.
     *
     * @param films     The films.
     * @param actors    The actors.
     * @param realizers The realizers.
     * @param kinds     The kinds.
     * @param types     The types.
     * @param languages The languages.
     * @param countries The countries.
     * @param borrowers The borrowers.
     * @param lendings  The lendings.
     */
    public static void persistDataOfImport(Iterable<Film> films, Iterable<Person> actors,
                                           Iterable<Person> realizers, Iterable<SimpleData> kinds,
                                           Iterable<SimpleData> types, Iterable<SimpleData> languages,
                                           Iterable<SimpleData> countries,
                                           Iterable<Person> borrowers, Iterable<Lending> lendings) {

        countriesService.createAll(countries);
        borrowersService.createAll(borrowers);
        languagesService.createAll(languages);
        typesService.createAll(types);
        kindsService.createAll(kinds);

        persistRealizers(realizers, countries);
        persistActors(actors, countries);
        persistFilms(films, actors, realizers, kinds, types, languages);
        persistLendings(lendings, films, borrowers);
    }

    /**
     * Persist the realizers.
     *
     * @param realizers The realizers to persist.
     * @param countries The countries.
     */
    private static void persistRealizers(Iterable<Person> realizers, Iterable<SimpleData> countries) {
        for (Person realizer : realizers) {
            realizer.setTheCountry(DataUtils.getDataByTemporaryId(realizer.getTemporaryContext().getCountry(), countries));
            realizer.setNote(DAO_NOTES.getNote(NoteType.getEnum(realizer.getTemporaryContext().getIntNote())));

            realizersService.create(realizer);
        }
    }

    /**
     * Persist the actors.
     *
     * @param actors    The actors to persist.
     * @param countries The countries.
     */
    private static void persistActors(Iterable<Person> actors, Iterable<SimpleData> countries) {
        for (Person actor : actors) {
            actor.setTheCountry(DataUtils.getDataByTemporaryId(actor.getTemporaryContext().getCountry(), countries));
            actor.setNote(DAO_NOTES.getNote(NoteType.getEnum(actor.getTemporaryContext().getIntNote())));

            actorService.create(actor);
        }
    }

    /**
     * Persist the films.
     *
     * @param films     The films to persist
     * @param actors    The actors.
     * @param realizers The realizers.
     * @param kinds     The kinds.
     * @param types     The types.
     * @param languages The languages.
     */
    private static void persistFilms(Iterable<Film> films, Iterable<Person> actors, Iterable<Person> realizers,
									 Iterable<SimpleData> kinds, Iterable<SimpleData> types, Iterable<SimpleData> languages) {
        for (Film film : films) {
            film.setTheLanguage(DataUtils.getDataByTemporaryId(film.getTemporaryContext().getLanguage(), languages));
            film.setTheType(DataUtils.getDataByTemporaryId(film.getTemporaryContext().getType(), types));
            film.setTheRealizer(DataUtils.getDataByTemporaryId(film.getTemporaryContext().getRealizer(), realizers));

            for (Integer i : film.getTemporaryContext().getActors()) {
                Person actor = DataUtils.getDataByTemporaryId(i, actors);

                if (actor != null) {
                    film.addActor(actor);
                }
            }

            for (Integer i : film.getTemporaryContext().getKinds()) {
                SimpleData kind = DataUtils.getDataByTemporaryId(i, kinds);

                if (kind != null) {
                    film.addKind(kind);
                }
            }

            filmsService.create(film);
        }
    }

    /**
     * Persist the lendings.
     *
     * @param lendings  The lendings to persist.
     * @param films     The films.
     * @param borrowers The borrowers.
     */
    private static void persistLendings(Iterable<Lending> lendings, Iterable<Film> films, Iterable<Person> borrowers) {
        if (lendings != null) {
            for (Lending lending : lendings) {
                lending.setThePerson(DataUtils.getDataByTemporaryId(lending.getTemporaryContext().getBorrower(), borrowers));
                lending.setTheOther(DataUtils.getDataByTemporaryId(lending.getTemporaryContext().getFilm(), films).getId());

                lendingsService.create(lending);
            }
        }
    }
}