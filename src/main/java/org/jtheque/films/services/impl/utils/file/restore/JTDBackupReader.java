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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.file.able.BackupReader;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.utils.file.jt.able.JTNotZippedFile;
import org.jtheque.core.utils.file.jt.impl.JTDFile;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.services.impl.utils.file.imports.ImporterUtils;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.services.able.IPersonService;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.utils.bean.IntDate;

import javax.annotation.Resource;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A reader for JTD Backup.
 *
 * @author Baptiste Wicht
 */
public final class JTDBackupReader implements BackupReader {
    private Collection<Film> films;
    private Collection<Person> actors;
    private Collection<Person> realizers;
    private Collection<SimpleData> kinds;
    private Collection<SimpleData> types;
    private Collection<SimpleData> languages;
    private Collection<SimpleData> countries;
    private Collection<Person> borrowers;
    private Collection<Lending> lendings;

    @Resource
    private IPersonService borrowersService;

    @Resource
    private ISimpleDataService countriesService;

    @Resource
    private ILendingsService lendingsService;

    @Resource
    private ISimpleDataService languagesService;

    @Resource
    private ISimpleDataService typesService;

    @Resource
    private ISimpleDataService kindsService;

    @Resource
    private IActorService actorService;

    @Resource
    private IFilmsService filmsService;

    @Resource
    private IRealizersService realizersService;

    @Override
    public void readBackup(Object object) {
        JTDFile file = (JTDFile) object;
        DataInputStream stream = file.getStream();

        try {
            readFilms(stream, file);
            readActors(stream, file);
            readRealizers(stream, file);
            readLanguages(stream, file);
            readKinds(stream, file);
            readTypes(stream, file);
            readCountries(stream, file);
            readLendings(stream, file);
            readBorrowers(stream, file);
        } catch (IOException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }

    @Override
    public void persistTheData() {
        ImporterUtils.persistDataOfImport(films, actors,
                realizers, kinds, types,
                languages, countries,
                borrowers, lendings);
    }

    /**
     * Read the borrowers.
     *
     * @param stream The input stream.
     * @param file   The file.
     *
     * @throws IOException Thrown when an error occurs during the reading process.
     */
    private void readBorrowers(DataInputStream stream, JTNotZippedFile file) throws IOException {
        if (stream.readInt() == IFileManager.CONTENT) {
            borrowers = new ArrayList<Person>(10);

            boolean endOfBorrowersList = false;
            while (!endOfBorrowersList) {
                Person borrower = borrowersService.getEmptyPerson();

                borrower.getTemporaryContext().setId(stream.readInt());
                borrower.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
                borrower.setFirstName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
                borrower.setEmail(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

                borrowers.add(borrower);

                long separator = stream.readLong();

                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfBorrowersList = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }
            }
        } else if (stream.readLong() != IFileManager.JT_CATEGORY_SEPARATOR) {
            file.setCorrectSeparators(false);
        }
    }

    /**
     * Read the lendings.
     *
     * @param stream The input stream.
     * @param file   The file.
     *
     * @throws IOException Thrown when an error occurs during the reading process.
     */
    private void readLendings(DataInputStream stream, JTNotZippedFile file) throws IOException {
        if (stream.readInt() == IFileManager.CONTENT) {
            boolean endOfLendingsList = false;
            lendings = new ArrayList<Lending>(10);

            while (!endOfLendingsList) {
                Lending lending = lendingsService.getEmptyLending();

                lending.getTemporaryContext().setId(stream.readInt());
                lending.setDate(new IntDate(stream.readInt()));
                lending.getTemporaryContext().setBorrower(stream.readInt());
                lending.getTemporaryContext().setFilm(stream.readInt());

                lendings.add(lending);

                long separator = stream.readLong();

                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfLendingsList = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }
            }
        } else if (stream.readLong() != IFileManager.JT_CATEGORY_SEPARATOR) {
            file.setCorrectSeparators(false);
        }
    }

    /**
     * Read the countries.
     *
     * @param stream The input stream.
     * @param file   The file.
     *
     * @throws IOException Thrown when an error occurs during the reading process.
     */
    private void readCountries(DataInputStream stream, JTNotZippedFile file) throws IOException {
        if (stream.readInt() == IFileManager.CONTENT) {
            boolean endOfCountries = false;
            countries = new ArrayList<SimpleData>(20);

            while (!endOfCountries) {
                SimpleData country = countriesService.getEmptySimpleData();

                country.getTemporaryContext().setId(stream.readInt());
                country.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

                countries.add(country);

                long separator = stream.readLong();

                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfCountries = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }
            }
        } else if (stream.readLong() != IFileManager.JT_CATEGORY_SEPARATOR) {
            file.setCorrectSeparators(false);
        }
    }

    /**
     * Read the types.
     *
     * @param stream The input stream.
     * @param file   The file.
     *
     * @throws IOException Thrown when an error occurs during the reading process.
     */
    private void readTypes(DataInputStream stream, JTNotZippedFile file) throws IOException {
        if (stream.readInt() == IFileManager.CONTENT) {
            boolean endOfKindsList = false;
            types = new ArrayList<SimpleData>(10);

            while (!endOfKindsList) {
                SimpleData type = typesService.getEmptySimpleData();

                type.getTemporaryContext().setId(stream.readInt());
                type.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

                types.add(type);

                long separator = stream.readLong();

                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfKindsList = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }
            }
        } else if (stream.readLong() != IFileManager.JT_CATEGORY_SEPARATOR) {
            file.setCorrectSeparators(false);
        }
    }

    /**
     * Read the kinds.
     *
     * @param stream The input stream.
     * @param file   The file.
     *
     * @throws IOException Thrown when an error occurs during the reading process.
     */
    private void readKinds(DataInputStream stream, JTNotZippedFile file) throws IOException {
        //Si y a des genres
        if (stream.readInt() == IFileManager.CONTENT) {
            boolean endOfKindsList = false;
            kinds = new ArrayList<SimpleData>(10);

            while (!endOfKindsList) {
                SimpleData genre = kindsService.getEmptySimpleData();

                genre.getTemporaryContext().setId(stream.readInt());
                genre.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

                kinds.add(genre);

                long separator = stream.readLong();

                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfKindsList = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }
            }
        } else if (stream.readLong() != IFileManager.JT_CATEGORY_SEPARATOR) {
            file.setCorrectSeparators(false);
        }
    }

    /**
     * Read the languages.
     *
     * @param stream The input stream.
     * @param file   The file.
     *
     * @throws IOException Thrown when an error occurs during the reading process.
     */
    private void readLanguages(DataInputStream stream, JTNotZippedFile file) throws IOException {
        if (stream.readInt() == IFileManager.CONTENT) {
            boolean endOfLanguagesList = false;
            languages = new ArrayList<SimpleData>(10);

            while (!endOfLanguagesList) {
                SimpleData language = languagesService.getEmptySimpleData();

                language.getTemporaryContext().setId(stream.readInt());
                language.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

                languages.add(language);

                long separator = stream.readLong();

                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfLanguagesList = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }
            }
        } else if (stream.readLong() != IFileManager.JT_CATEGORY_SEPARATOR) {
            file.setCorrectSeparators(false);
        }
    }

    /**
     * Read the realizers.
     *
     * @param stream The input stream.
     * @param file   The file.
     *
     * @throws IOException Thrown when an error occurs during the reading process.
     */
    private void readRealizers(DataInputStream stream, JTNotZippedFile file) throws IOException {
        if (stream.readInt() == IFileManager.CONTENT) {
            boolean endOfRealizersList = false;
            realizers = new ArrayList<Person>(20);

            while (!endOfRealizersList) {
                Person realizer = realizersService.getEmptyRealizer();

                realizer.getTemporaryContext().setId(stream.readInt());
                realizer.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
                realizer.setFirstName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
                realizer.getTemporaryContext().setCountry(stream.readInt());
                realizer.getTemporaryContext().setIntNote(stream.readInt());

                realizers.add(realizer);

                long separator = stream.readLong();

                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfRealizersList = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }
            }
        } else if (stream.readLong() != IFileManager.JT_CATEGORY_SEPARATOR) {
            file.setCorrectSeparators(false);
        }
    }

    /**
     * Read the actors.
     *
     * @param stream The input stream.
     * @param file   The file.
     *
     * @throws IOException Thrown when an error occurs during the reading process.
     */
    private void readActors(DataInputStream stream, JTNotZippedFile file) throws IOException {
        if (stream.readInt() == IFileManager.CONTENT) {
            boolean endOfActorsList = false;
            actors = new ArrayList<Person>(50);

            while (!endOfActorsList) {
                Person actor = actorService.getEmptyPerson();

                actor.getTemporaryContext().setId(stream.readInt());
                actor.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
                actor.setFirstName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
                actor.getTemporaryContext().setCountry(stream.readInt());
                actor.getTemporaryContext().setIntNote(stream.readInt());

                actors.add(actor);

                long separator = stream.readLong();
                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfActorsList = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }
            }
        } else if (stream.readLong() != IFileManager.JT_CATEGORY_SEPARATOR) {
            file.setCorrectSeparators(false);
        }
    }

    /**
     * Read the films.
     *
     * @param stream The input stream.
     * @param file   The file.
     *
     * @throws IOException Thrown when an error occurs during the reading process.
     */
    private void readFilms(DataInputStream stream, JTNotZippedFile file) throws IOException {
        if (stream.readInt() == IFileManager.CONTENT) {
            boolean endOfFilmsList = false;
            films = new ArrayList<Film>(25);

            while (!endOfFilmsList) {
                Film film = filmsService.getEmptyFilm();

                film.getTemporaryContext().setId(stream.readInt());
                film.setTitle(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

                readKinds(stream, file, film);
                readFilm(stream, film);
                readActors(stream, file, film);

                long separator = stream.readLong();

                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfFilmsList = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }

                films.add(film);
            }
        } else if (stream.readLong() != IFileManager.JT_CATEGORY_SEPARATOR) {
            file.setCorrectSeparators(false);
        }
    }

    /**
     * Read the kinds from the input stream.
     *
     * @param stream The stream to read.
     * @param file   The file to read from.
     * @param film   The film to fill.
     *
     * @throws IOException If an error occurs during the stream reading.
     */
    private static void readKinds(DataInputStream stream, JTNotZippedFile file, Film film) throws IOException {
        if (stream.readInt() == IFileManager.CONTENT) {
            boolean endOfKindsList = false;
            Collection<Integer> kindsFilm = new ArrayList<Integer>(12);

            while (!endOfKindsList) {
                kindsFilm.add(stream.readInt());

                long separator = stream.readLong();

                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfKindsList = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }
            }

            film.getTemporaryContext().setKinds(kindsFilm);
        } else {
            film.getTemporaryContext().setKinds(Collections.<Integer>emptyList());
        }
    }

    /**
     * Fill the film object with the data of the stream.
     *
     * @param stream The stream to read from.
     * @param film   The film to fill.
     *
     * @throws IOException Thrown when an error occurs during the reading process.
     */
    private static void readFilm(DataInputStream stream, Film film) throws IOException {
        film.getTemporaryContext().setType(stream.readInt());
        film.getTemporaryContext().setRealizer(stream.readInt());
        film.getTemporaryContext().setLanguage(stream.readInt());
        film.setYear(stream.readInt());
        film.setDuration(stream.readInt());
        film.getTemporaryContext().setLending(stream.readInt());
        film.setResume(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
        film.setComment(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
    }

    /**
     * Read the actors from the input stream.
     *
     * @param stream The stream to read.
     * @param file   The file to read from.
     * @param film   The film to fill.
     *
     * @throws IOException If an error occurs during the stream reading.
     */
    private static void readActors(DataInputStream stream, JTNotZippedFile file, Film film) throws IOException {
        if (stream.readInt() == IFileManager.CONTENT) {
            boolean endOfActorsList = false;
            Collection<Integer> actors = new ArrayList<Integer>(25);

            while (!endOfActorsList) {
                actors.add(stream.readInt());

                long separator = stream.readLong();

                if (separator == IFileManager.JT_CATEGORY_SEPARATOR) {
                    endOfActorsList = true;
                } else if (separator != IFileManager.JT_INTERN_SEPARATOR) {
                    file.setCorrectSeparators(false);
                }
            }

            film.getTemporaryContext().setActors(actors);
        } else {
            film.getTemporaryContext().setActors(new ArrayList<Integer>(0));
        }
    }
}