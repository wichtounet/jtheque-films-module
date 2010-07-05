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
import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.file.able.BackupWriter;
import org.jtheque.core.managers.log.ILoggingManager;
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

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A JTD Backup Writer.
 *
 * @author Baptiste Wicht
 */
public final class JTDBackupWriter implements BackupWriter {
    @Resource
    private IFilmsService filmsService;

    @Resource
    private ISimpleDataService kindsService;

    @Resource
    private ISimpleDataService typesService;

    @Resource
    private ISimpleDataService languagesService;

    @Resource
    private IActorService actorService;

    @Resource
    private IRealizersService realizersService;

    @Resource
    private ILendingsService lendingsService;

    @Resource
    private IPersonService borrowersService;

    @Resource
    private ISimpleDataService countriesService;

    /**
     * Construct a new JTDBackupWriter.
     */
    public JTDBackupWriter() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void write(Object object) {
        DataOutputStream stream = (DataOutputStream) object;

        try {
            writeFilms(stream);
            writeActors(stream);
            writeRealizers(stream);
            writeLanguages(stream);
            writeKinds(stream);
            writeTypes(stream);
            writeCountries(stream);
            writeLendings(stream);
            writeBorrowers(stream);
        } catch (IOException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }

    /**
     * Write the borrowers to the stream.
     *
     * @param stream The stream to write in.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private void writeBorrowers(DataOutputStream stream) throws IOException {
        if (borrowersService.hasNoPerson()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstBorrower = true;
            for (Person borrower : borrowersService.getPersons()) {
                if (firstBorrower) {
                    firstBorrower = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                stream.writeInt(borrower.getId());
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(borrower.getName()));
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(borrower.getFirstName()));
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(borrower.getEmail()));
            }
        }

        stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
    }

    /**
     * Write the lendings to the stream.
     *
     * @param stream The stream to write in.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private void writeLendings(DataOutputStream stream) throws IOException {
        if (lendingsService.hasNoLendings()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstLending = true;
            for (Lending lending : lendingsService.getLendings()) {
                if (firstLending) {
                    firstLending = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                stream.writeInt(lending.getId());
                stream.writeInt(lending.getDate().intValue());
                stream.writeInt(lending.getThePerson().getId());
                stream.writeInt(lending.getTheOther());
            }
        }

        stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
    }

    /**
     * Write the countries to the stream.
     *
     * @param stream The stream to write in.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private void writeCountries(DataOutputStream stream) throws IOException {
        if (countriesService.getDatas() == null || countriesService.getDatas().isEmpty()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstCountry = true;
            for (SimpleData country : countriesService.getDatas()) {
                if (firstCountry) {
                    firstCountry = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                stream.writeInt(country.getId());
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(country.getName()));
            }
        }

        stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
    }

    /**
     * Write the types to the stream.
     *
     * @param stream The stream to write in.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private void writeTypes(DataOutputStream stream) throws IOException {
        if (typesService.hasNoDatas()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstType = true;
            for (SimpleData type : typesService.getDatas()) {
                if (firstType) {
                    firstType = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                stream.writeInt(type.getId());
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(type.getName()));
            }
        }

        stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
    }

    /**
     * Write the kinds to the stream.
     *
     * @param stream The stream to write in.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private void writeKinds(DataOutputStream stream) throws IOException {
        if (kindsService.hasNoDatas()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstKind = true;
            for (SimpleData kind : kindsService.getDatas()) {
                if (firstKind) {
                    firstKind = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                stream.writeInt(kind.getId());
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(kind.getName()));
            }
        }

        stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
    }

    /**
     * Write the languages to the stream.
     *
     * @param stream The stream to write in.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private void writeLanguages(DataOutputStream stream) throws IOException {
        if (languagesService.getDatas() == null || languagesService.getDatas().isEmpty()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstLanguage = true;
            for (SimpleData language : languagesService.getDatas()) {
                if (firstLanguage) {
                    firstLanguage = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                stream.writeInt(language.getId());
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(language.getName()));
            }
        }

        stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
    }

    /**
     * Write the realizers to the stream.
     *
     * @param stream The stream to write in.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private void writeRealizers(DataOutputStream stream) throws IOException {
        if (realizersService.hasNoRealizers()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstRealizer = true;
            for (Person realizer : realizersService.getRealizers()) {
                if (firstRealizer) {
                    firstRealizer = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                stream.writeInt(realizer.getId());
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(realizer.getName()));
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(realizer.getFirstName()));
                stream.writeInt(realizer.getTheCountry().getId());
                stream.writeInt(realizer.getNote().getValue().intValue());
            }
        }

        stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
    }

    /**
     * Write the actors to the stream.
     *
     * @param stream The stream to write in.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private void writeActors(DataOutputStream stream) throws IOException {
        if (actorService.hasNoPerson()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstActor = true;
            for (Person actor : actorService.getPersons()) {
                if (firstActor) {
                    firstActor = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                stream.writeInt(actor.getId());
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(actor.getName()));
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(actor.getFirstName()));
                stream.writeInt(actor.getTheCountry().getId());
                stream.writeInt(actor.getNote().getValue().intValue());
            }
        }

        stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
    }

    /**
     * Write the films to the stream.
     *
     * @param stream The stream to write in.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private void writeFilms(DataOutputStream stream) throws IOException {
        if (filmsService.isNoFilms()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstFilm = true;
            for (Film film : filmsService.getFilms()) {
                if (firstFilm) {
                    firstFilm = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                writeFilm(stream, film);
            }
        }

        stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
    }

    /**
     * Write the film.
     *
     * @param stream The stream to write to.
     * @param film   The film to write.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private static void writeFilm(DataOutputStream stream, Film film) throws IOException {
        stream.writeInt(film.getId());
        stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(film.getTitle()));

        writeKindsOfFilm(stream, film);

        stream.writeInt(film.hasType() ? film.getTheType().getId() : -1);
        stream.writeInt(film.hasRealizer() ? film.getTheRealizer().getId() : -1);
        stream.writeInt(film.hasLanguage() ? film.getTheLanguage().getId() : -1);

        stream.writeInt(film.getYear());
        stream.writeInt(film.getDuration());
        stream.writeInt(film.getNote().getValue().intValue());
        stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(film.getResume()));
        stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(film.getComment()));

        writeActorsOfFilm(stream, film);
    }

    /**
     * Write the kinds of the film.
     *
     * @param stream The stream to write to.
     * @param film   The film to write.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private static void writeKindsOfFilm(DataOutputStream stream, Film film) throws IOException {
        if (film.hasKinds()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstKind = true;
            for (SimpleData kind : film.getKinds()) {
                if (firstKind) {
                    firstKind = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                stream.writeInt(kind.getId());
            }

            stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
        }
    }

    /**
     * Write the actors of the film.
     *
     * @param stream The stream to write to.
     * @param film   The film to write.
     *
     * @throws IOException Thrown when an exception occurs during the writing of data.
     */
    private static void writeActorsOfFilm(DataOutputStream stream, Film film) throws IOException {
        if (film.hasActors()) {
            stream.writeInt(IFileManager.NO_CONTENT);
        } else {
            stream.writeInt(IFileManager.CONTENT);

            boolean firstActor = true;
            for (Person actor : film.getActors()) {
                if (firstActor) {
                    firstActor = false;
                } else {
                    stream.writeLong(IFileManager.JT_INTERN_SEPARATOR);
                }

                stream.writeInt(actor.getId());
            }

            stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
        }
    }
}
