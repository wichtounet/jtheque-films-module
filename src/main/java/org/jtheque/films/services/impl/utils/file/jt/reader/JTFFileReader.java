package org.jtheque.films.services.impl.utils.file.jt.reader;

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
import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.utils.file.jt.AbstractJTFileHeader;
import org.jtheque.core.utils.file.jt.JTFileReader;
import org.jtheque.core.utils.file.jt.able.JTNotZippedFile;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.services.impl.utils.file.jt.JTFFile;
import org.jtheque.films.utils.Constants;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import javax.annotation.Resource;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A reader for JTF File.
 *
 * @author Baptiste Wicht
 */
public final class JTFFileReader extends JTFileReader {
    private boolean correctSeparators;

    @Resource
    private ISimpleDataService countriesService;

    @Resource
    private ISimpleDataService languagesService;

    @Resource
    private ISimpleDataService typesService;

    @Resource
    private ISimpleDataService kindsService;

    @Resource
    private IActorService actorsService;

    @Resource
    private IFilmsService filmsService;

    @Resource
    private IRealizersService realizersService;

    @Override
    public JTFFile readFile(DataInputStream stream) throws FileException {
        JTFFile file = new JTFFile();

        correctSeparators = true;

        try {
            readHeader(stream, file);

            if (stream.readLong() != Constants.Files.JT.JTCATEGORYSEPARATOR) {
                correctSeparators = false;
            }

            readFilm(stream, file);

            if (stream.readLong() != Constants.Files.JT.JTCATEGORYSEPARATOR) {
                correctSeparators = false;
            }

            readActors(stream, file);
            readRealizer(stream, file);
            readLanguage(stream, file);
            readKinds(stream, file);
            readType(stream, file);
            readCountries(stream, file);
        } catch (IOException e) {
            throw new FileException(Managers.getManager(ILanguageManager.class).getMessage("errors.file.ioexception"), e);
        } finally {
            FileUtils.close(stream);
        }

        file.setCorrectSeparators(correctSeparators);

        return file;
    }

    /**
     * Read the header of the file.
     *
     * @param stream The stream to read from.
     * @param file   The file to read.
     *
     * @throws IOException Thrown if an exception occurs during the write process.
     */
    private static void readHeader(DataInputStream stream, JTNotZippedFile file) throws IOException {
        AbstractJTFileHeader header = file.getHeader();

        header.setKey(stream.readUTF());
        header.setVersionJTheque(new Version(stream.readUTF()));
        header.setFileVersion(stream.readInt());
        header.setDate(stream.readInt());
    }

    /**
     * Read the film in the file.
     *
     * @param stream The stream to read from.
     * @param file   The file to read.
     *
     * @throws IOException Thrown if an exception occurs during the write process.
     */
    private void readFilm(DataInputStream stream, JTFFile file) throws IOException {
        Film film = filmsService.getEmptyFilm();

        film.setTitle(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
        film.setYear(stream.readInt());
        film.setDuration(stream.readInt());
        film.getTemporaryContext().setTemporaryIntNote(stream.readInt());
        film.setResume(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
        film.setComment(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

        file.setFilm(film);
    }

    /**
     * Read the actors in the file.
     *
     * @param stream The stream to read from.
     * @param file   The file to read.
     *
     * @throws IOException Thrown if an exception occurs during the write process.
     */
    private void readActors(DataInputStream stream, JTFFile file) throws IOException {
        if (stream.readInt() == Constants.Files.JT.CONTENT) {
            boolean endOfActorsList = false;
            Set<Person> actors = new HashSet<Person>(12);

            while (!endOfActorsList) {
                Person actor = actorsService.getEmptyPerson();

                actor.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
                actor.setFirstName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
                actor.getTemporaryContext().setCountry(stream.readInt());
                actor.getTemporaryContext().setIntNote(stream.readInt());

                actors.add(actor);

                long separator = stream.readLong();

                if (separator == Constants.Files.JT.JTCATEGORYSEPARATOR) {
                    endOfActorsList = true;
                } else if (separator != Constants.Files.JT.JTINTERNSEPARATOR) {
                    correctSeparators = false;
                }
            }

            file.setActors(actors);
        } else {
            file.setActors(Collections.<Person>emptySet());

            if (stream.readLong() != Constants.Files.JT.JTCATEGORYSEPARATOR) {
                correctSeparators = false;
            }
        }
    }

    /**
     * Read the realizer in the file.
     *
     * @param stream The stream to read from.
     * @param file   The file to read.
     *
     * @throws IOException Thrown if an exception occurs during the write process.
     */
    private void readRealizer(DataInputStream stream, JTFFile file) throws IOException {
        //Si y a un réalisateur
        if (stream.readInt() == Constants.Files.JT.CONTENT) {
            Person realizer = realizersService.getEmptyRealizer();

            realizer.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
            realizer.setFirstName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
            realizer.getTemporaryContext().setCountry(stream.readInt());
            realizer.getTemporaryContext().setIntNote(stream.readInt());

            file.setRealizer(realizer);
        }

        if (stream.readLong() != Constants.Files.JT.JTCATEGORYSEPARATOR) {
            correctSeparators = false;
        }
    }

    /**
     * Read the language in the file.
     *
     * @param stream The stream to read from.
     * @param file   The file to read.
     *
     * @throws IOException Thrown if an exception occurs during the write process.
     */
    private void readLanguage(DataInputStream stream, JTFFile file) throws IOException {
        if (stream.readInt() == Constants.Files.JT.CONTENT) {
            SimpleData language = languagesService.getEmptySimpleData();

            language.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

            file.setLanguage(language);
        }

        if (stream.readLong() != Constants.Files.JT.JTCATEGORYSEPARATOR) {
            correctSeparators = false;
        }
    }

    /**
     * Read the kinds in the file.
     *
     * @param stream The stream to read from.
     * @param file   The file to read.
     *
     * @throws IOException Thrown if an exception occurs during the write process.
     */
    private void readKinds(DataInputStream stream, JTFFile file) throws IOException {
        if (file.getHeader().getFileVersion() == Constants.Files.Versions.JTF.FIRST) {
            readOneKind(stream, file);
        } else if (file.getHeader().getFileVersion() == Constants.Files.Versions.JTF.SECOND) {
            readMultipleKinds(stream, file);
        }
    }

    /**
     * Read one kind in the file.
     *
     * @param stream The stream to read from.
     * @param file   The file to read.
     *
     * @throws IOException Thrown if an exception occurs during the write process.
     */
    private void readOneKind(DataInputStream stream, JTFFile file) throws IOException {
        if (stream.readInt() == Constants.Files.JT.CONTENT) {
            SimpleData kind = kindsService.getEmptySimpleData();

            kind.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

            Set<SimpleData> kinds = new HashSet<SimpleData>(1);

            kinds.add(kind);

            file.setKinds(kinds);
        }

        if (stream.readLong() != Constants.Files.JT.JTCATEGORYSEPARATOR) {
            correctSeparators = false;
        }
    }

    /**
     * Read some kinds in the file.
     *
     * @param stream The stream to read from.
     * @param file   The file to read.
     *
     * @throws IOException Thrown if an exception occurs during the write process.
     */
    private void readMultipleKinds(DataInputStream stream, JTFFile file) throws IOException {
        if (stream.readInt() == Constants.Files.JT.CONTENT) {
            boolean endOfKindsList = false;
            Set<SimpleData> kinds = new HashSet<SimpleData>(5);

            while (!endOfKindsList) {
                SimpleData kind = kindsService.getEmptySimpleData();

                kind.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

                kinds.add(kind);

                long separator = stream.readLong();

                if (separator == Constants.Files.JT.JTCATEGORYSEPARATOR) {
                    endOfKindsList = true;
                } else if (separator != Constants.Files.JT.JTINTERNSEPARATOR) {
                    correctSeparators = false;
                }
            }

            file.setKinds(kinds);
        } else {
            file.setKinds(Collections.<SimpleData>emptySet());

            if (stream.readLong() != Constants.Files.JT.JTCATEGORYSEPARATOR) {
                correctSeparators = false;
            }
        }
    }

    /**
     * Read the type in the file.
     *
     * @param stream The stream to read from.
     * @param file   The file to read.
     *
     * @throws IOException Thrown if an exception occurs during the write process.
     */
    private void readType(DataInputStream stream, JTFFile file) throws IOException {
        if (stream.readInt() == Constants.Files.JT.CONTENT) {
            SimpleData type = typesService.getEmptySimpleData();

            type.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

            file.setType(type);
        }

        if (stream.readLong() != Constants.Files.JT.JTCATEGORYSEPARATOR) {
            correctSeparators = false;
        }
    }

    /**
     * Read the countries in the file.
     *
     * @param stream The stream to read from.
     * @param file   The file to read.
     *
     * @throws IOException Thrown if an exception occurs during the write process.
     */
    private void readCountries(DataInputStream stream, JTFFile file) throws IOException {
        if (stream.readInt() == Constants.Files.JT.CONTENT) {
            boolean endOfCountries = false;
            Collection<SimpleData> countries = new ArrayList<SimpleData>(12);

            while (!endOfCountries) {
                SimpleData country = countriesService.getEmptySimpleData();

                country.getTemporaryContext().setId(stream.readInt());
                country.setName(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));

                countries.add(country);

                long separator = stream.readLong();

                if (separator == Constants.Files.JT.JTCATEGORYSEPARATOR) {
                    endOfCountries = true;
                } else if (separator != Constants.Files.JT.JTINTERNSEPARATOR) {
                    correctSeparators = false;
                }
            }

            file.setCountries(countries);
        } else {
            file.setCountries(Collections.<SimpleData>emptyList());

            if (stream.readLong() != Constants.Files.JT.JTCATEGORYSEPARATOR) {
                correctSeparators = false;
            }
        }
    }
}
