package org.jtheque.films.services.impl.utils.file.imports;

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
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.core.utils.db.DaoNotes.NoteType;
import org.jtheque.core.utils.file.XMLException;
import org.jtheque.core.utils.file.XMLOverReader;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.INotesService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import javax.annotation.Resource;

/**
 * Import films from XML file.
 *
 * @author Baptiste Wicht
 */
public final class XMLImporter implements Importer {
    private final ILanguageManager resources = Managers.getManager(ILanguageManager.class);

    @Resource
    private ISimpleDataService countriesService;

    @Resource
    private ISimpleDataService typesService;

    @Resource
    private ISimpleDataService languagesService;

    @Resource
    private IFilmsService filmsService;

    @Resource
    private IActorService actorService;

    @Resource
    private IRealizersService realizersService;

    @Resource
    private INotesService notesService;

    @Resource
    private ISimpleDataService kindsService;

    private final DaoNotes daoNotes = DaoNotes.getInstance();

    /**
     * Construct a new XMLImporter.
     */
    public XMLImporter() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public boolean canImportFrom(FileType fileType) {
        return fileType == FileType.XML;
    }

    @Override
    public void importFrom(String filePath) throws FileException {
        XMLOverReader reader = new XMLOverReader();

        try {
            reader.openFile(filePath);

            int version = threatVersion(reader);

            while (reader.next("//film")) {
                Film film = filmsService.getEmptyFilm();

                importPropertiesOfFilm(reader, film);
                importLanguage(reader, film);
                importType(reader, film);
                importRealizer(reader, film);
                importKinds(reader, version, film);
                importActors(reader, film);

                filmsService.create(film);
            }
        } catch (XMLException e) {
            throw new FileException(resources.getMessage("errors.file.ioexception"), e);
        } finally {
            FileUtils.close(reader);
        }
    }

    /**
     * Import the informations of the film from the XML file.
     *
     * @param reader The XML Reader.
     * @param film   The film to get the informations from.
     *
     * @throws XMLException If an error occurs during the XML reading.
     */
    private void importPropertiesOfFilm(XMLOverReader reader, Film film) throws XMLException {
        film.setTitle(reader.readString("./title"));
        film.setDuration(reader.readInt("./duration"));
        film.setYear(reader.readInt("./year"));
        film.setNote(daoNotes.getNote(NoteType.getEnum(reader.readInt("./note"))));
        film.setComment(reader.readString("./comment"));
        film.setResume(reader.readString("./resume"));
    }

    /**
     * Read the version of the XML file.
     *
     * @param reader The XML reader
     *
     * @return The version.
     *
     * @throws XMLException  If an error occurs during the XML reading.
     * @throws FileException If the version of the
     */
    private int threatVersion(XMLOverReader reader) throws XMLException, FileException {
        int version = reader.readInt("./header/file-version");

        if (version != Constants.Files.Versions.XML.FIRST && version != Constants.Files.Versions.XML.SECOND) {
            throw new FileException(resources.getMessage("errors.file.unsupportedversion"));
        }

        return version;
    }

    /**
     * Import the language of the film.
     *
     * @param reader The XML reader
     * @param film   The film to fill.
     *
     * @throws XMLException If an error occurs during the XML reading.
     */
    private void importLanguage(XMLOverReader reader, Film film) throws XMLException {
        SimpleData language = languagesService.getEmptySimpleData();
        language.setName(reader.readString("./language"));

        if (languagesService.exist(language)) {
            language.setId(languagesService.getSimpleData(language.getName()).getId());
        } else {
            languagesService.create(language);
        }

        film.setTheLanguage(language);
    }

    /**
     * Import the type of the film.
     *
     * @param reader The XML reader
     * @param film   The film to fill.
     *
     * @throws XMLException If an error occurs during the XML reading.
     */
    private void importType(XMLOverReader reader, Film film) throws XMLException {
        SimpleData type = typesService.getEmptySimpleData();
        type.setName(reader.readString("./type"));

        if (typesService.exist(type)) {
            type.setId(typesService.getSimpleData(type.getName()).getId());
        } else {
            typesService.create(type);
        }

        film.setTheType(type);
    }

    /**
     * Import the realizer of the film.
     *
     * @param reader The XML reader
     * @param film   The film to fill.
     *
     * @throws XMLException If an error occurs during the XML reading.
     */
    private void importRealizer(XMLOverReader reader, Film film) throws XMLException {
        if (reader.readNode("./realizer")) {
            Person realizer = realizersService.getEmptyRealizer();
            realizer.setName(reader.readString("./name"));
            realizer.setFirstName(reader.readString("./firstname"));
            realizer.setNote(notesService.getDefaultNote());

            addCountryToPerson(realizer, reader.readString("./country"));

            if (realizersService.exists(realizer)) {
                film.setTheRealizer(realizersService.getRealizer(realizer.getFirstName(), realizer.getName()));
            } else {
                realizersService.create(realizer);
                film.setTheRealizer(realizer);
            }

            reader.switchToParent();
        }
    }

    /**
     * Import the kinds of the film.
     *
     * @param reader  The XML reader
     * @param version The version of the file.
     * @param film    The film to fill.
     *
     * @throws XMLException If an error occurs during the XML reading.
     */
    private void importKinds(XMLOverReader reader, int version, Film film) throws XMLException {
        if (version == Constants.Files.Versions.XML.FIRST) {
            addKindToFilm(film, reader.readString("./kind"));
        } else {
            while (reader.next("//kinds/kind")) {
                addKindToFilm(film, reader.readString("./name"));
            }
        }
    }

    /**
     * Add a kind to the film.
     *
     * @param film     The film to add kind to.
     * @param kindName The name of the kind.
     */
    private void addKindToFilm(Film film, String kindName) {
        if (kindsService.exist(kindName)) {
            film.addKind(kindsService.getSimpleData(kindName));
        } else {
            SimpleData kind = kindsService.getEmptySimpleData();
            kind.setName(kindName);

            kindsService.create(kind);
            film.addKind(kind);
        }
    }

    /**
     * Import the actors of the film.
     *
     * @param reader The XML reader
     * @param film   The film to fill.
     *
     * @throws XMLException If an error occurs during the XML reading.
     */
    private void importActors(XMLOverReader reader, Film film) throws XMLException {
        while (reader.next("//actors/actor")) {
            Person actor = actorService.getEmptyPerson();
            actor.setName(reader.readString("./name"));
            actor.setFirstName(reader.readString("./firstname"));
            actor.setNote(notesService.getDefaultNote());

            addCountryToPerson(actor, reader.readString("./country"));

            if (actorService.exist(actor)) {
                film.addActor(actorService.getPerson(actor.getFirstName(), actor.getName()));
            } else {
                actorService.create(actor);
                film.addActor(actor);
            }
        }
    }

    /**
     * Add a country the person.
     *
     * @param person      The person to add country to.
     * @param countryName The name of the country.
     */
    private void addCountryToPerson(Person person, String countryName) {
        if (countriesService.exist(countryName)) {
            person.setTheCountry(countriesService.getSimpleData(countryName));
        } else {
            SimpleData country = countriesService.getEmptySimpleData();
            country.setName(countryName);

            countriesService.create(country);
            person.setTheCountry(country);
        }
    }
}
