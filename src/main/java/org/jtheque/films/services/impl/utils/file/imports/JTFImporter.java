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
import org.jtheque.core.utils.file.jt.JTFileReader;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.services.impl.utils.DataUtils;
import org.jtheque.films.services.impl.utils.file.jt.JTFFile;
import org.jtheque.films.services.impl.utils.file.jt.reader.JTFFileReader;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.utils.io.FileException;

import javax.annotation.Resource;

/**
 * An importer for JTF file.
 *
 * @author Baptiste Wicht
 */
public final class JTFImporter implements Importer {
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

    private final DaoNotes daoNotes = DaoNotes.getInstance();

    @Resource
    private ISimpleDataService kindsService;

    /**
     * Construct a new JTFImporter.
     */
    public JTFImporter() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public boolean canImportFrom(FileType fileType) {
        return fileType == FileType.JTF;
    }

    @Override
    public void importFrom(String filePath) throws FileException {
        JTFileReader reader = new JTFFileReader();

        importFrom((JTFFile) reader.readFile(filePath));
    }

    /**
     * Import from a JTFFile.
     *
     * @param file The JTF File.
     *
     * @throws FileException If there is a problem during the import.
     */
    public void importFrom(JTFFile file) throws FileException {
        if (file.getHeader().getFileVersion() != Constants.Files.Versions.JTF.FIRST ||
                file.getHeader().getFileVersion() != Constants.Files.Versions.JTF.SECOND) {
            throw new FileException(resources.getMessage("errors.file.unsupportedversion"));
        } else if (file.isValid()) {

            importLanguage(file);
            importCountries(file);
            importKinds(file);
            importType(file);
            importRealizer(file);
            importActors(file);

            file.getFilm().setNote(daoNotes.getNote(NoteType.getEnum(file.getFilm().getTemporaryContext().getIntNote())));

            filmsService.create(file.getFilm());
        } else {
            throw new FileException(resources.getMessage("errors.file.structureerror"));
        }
    }

    /**
     * Import the language from the file.
     *
     * @param file The file to import the language from.
     */
    private void importLanguage(JTFFile file) {
        if (file.getLanguage() != null) {
            if (languagesService.exist(file.getLanguage())) {
                file.setLanguage(languagesService.getSimpleData(file.getLanguage().getName()));
            } else {
                languagesService.create(file.getLanguage());
            }

            file.getFilm().setTheLanguage(file.getLanguage());
        }
    }

    /**
     * Import the countries from the file.
     *
     * @param file The file to import the countries from.
     */
    private void importCountries(JTFFile file) {
        for (SimpleData country : file.getCountries()) {
            if (countriesService.exist(country)) {
                country.setId(countriesService.getSimpleData(country.getName()).getId());
            } else {
                countriesService.create(country);
            }
        }
    }

    /**
     * Import the kinds from the file.
     *
     * @param file The file to import the kinds from.
     */
    private void importKinds(JTFFile file) {
        for (SimpleData kind : file.getKinds()) {
            if (kindsService.exist(kind)) {
                kind.setId(kindsService.getSimpleData(kind.getName()).getId());
            } else {
                kindsService.create(kind);
            }

            file.getFilm().addKind(kind);
        }
    }

    /**
     * Import the type from the file.
     *
     * @param file The file to import the type from.
     */
    private void importType(JTFFile file) {
        if (file.getType() != null) {
            if (typesService.exist(file.getType())) {
                file.getType().setId(typesService.getSimpleData(file.getType().getName()).getId());
            } else {
                typesService.create(file.getType());
            }

            file.getFilm().setTheType(file.getType());
        }
    }

    /**
     * Import the realizer from the file.
     *
     * @param file The file to import the realizer from.
     */
    private void importRealizer(JTFFile file) {
        if (file.getRealizer() != null) {
            file.getRealizer().setNote(daoNotes.getNote(NoteType.getEnum(file.getRealizer().getTemporaryContext().getIntNote())));
            file.getRealizer().setTheCountry(DataUtils.getDataByTemporaryId(
                    file.getRealizer().getTemporaryContext().getCountry(), file.getCountries()));

            if (realizersService.exists(file.getRealizer())) {
                file.getRealizer().setId(realizersService.getRealizer(file.getRealizer().getFirstName(), file.getRealizer().getName()).getId());
            } else {
                realizersService.create(file.getRealizer());
            }

            file.getFilm().setTheRealizer(file.getRealizer());
        }
    }

    /**
     * Import the actors from the file.
     *
     * @param file The file to import the actors from.
     */
    private void importActors(JTFFile file) {
        for (Person actor : file.getActors()) {
            actor.setNote(daoNotes.getNote(NoteType.getEnum(actor.getTemporaryContext().getIntNote())));
            actor.setTheCountry(DataUtils.getDataByTemporaryId(
                    actor.getTemporaryContext().getCountry(), file.getCountries()));

            if (actorService.exist(actor)) {
                actor.setId(actorService.getPerson(actor.getFirstName(), actor.getName()).getId());
            } else {
                actorService.create(actor);
            }

            file.getFilm().addActor(actor);
        }
    }
}
