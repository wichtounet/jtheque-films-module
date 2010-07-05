package org.jtheque.films.services.impl;

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
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.films.persistence.dao.able.IDaoFilms;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.INotesService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.services.impl.utils.VideoFile;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.print.PrintUtils;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The implementation of the films service.
 *
 * @author Baptiste Wicht
 */
public final class FilmsService implements IFilmsService {
    @Resource
    private IDaoFilms daoFilms;

    @Resource
    private INotesService notesService;

    @Resource
    private IRealizersService realizersService;

    @Resource
    private ISimpleDataService kindsService;

    @Resource
    private ISimpleDataService typesService;

    @Resource
    private ISimpleDataService languagesService;

    @Override
    @Transactional
    public void create(Film film) {
        daoFilms.create(film);
    }

    @Override
    @Transactional
    public void save(Film film) {
        daoFilms.save(film);
    }

    @Override
    @Transactional
    public boolean delete(Film film) {
        return daoFilms.delete(film);
    }

    @Override
    @Transactional
    public void createAll(Collection<Film> films) {
        daoFilms.createAll(films);
    }

    @Override
    public boolean isNoFilms() {
        return daoFilms.getFilms().size() <= 0;
    }

    @Override
    public Collection<Film> getFilms() {
        return daoFilms.getFilms();
    }

    @Override
    public void addDataListener(DataListener listener) {
        daoFilms.addDataListener(listener);
    }

    @Override
    public Collection<Film> getDatas() {
        return daoFilms.getFilms();
    }

    @Override
    public Film getDefaultFilm() {
        Film emptyFilm = getEmptyFilm();

        emptyFilm.setTitle(Managers.getManager(ILanguageManager.class).getMessage("generic.view.actions.new"));
        emptyFilm.setNote(notesService.getDefaultNote());
        emptyFilm.setTheRealizer(realizersService.getDefaultRealizer());
        emptyFilm.addKind(kindsService.getDefaultSimpleData());
        emptyFilm.setTheType(typesService.getDefaultSimpleData());
        emptyFilm.setTheLanguage(languagesService.getDefaultSimpleData());

        return emptyFilm;
    }

    @Override
    public void printListOfFilms() {
        Collection<String> list = new ArrayList<String>(25);

        list.add("List of films");

        for (Film f : daoFilms.getFilms()) {
            list.add(f.getDisplayableText());
        }

        PrintUtils.printArrayString(list);
    }

    @Override
    public String generateEmail(Film film) {
        return generateFilmDescription(film);
    }

    /**
     * Create a string description for a film.
     *
     * @param film The film to generate the description for.
     *
     * @return The string description of the film.
     */
    private static String generateFilmDescription(Film film) {
        StringBuilder builder = new StringBuilder(250);

        builder.append("Hi, \n\n i'm sending the informations about a film that can interest you : \n");
        builder.append("Title : ").append(film.getTitle());
        builder.append("\nKind : ");

        for (SimpleData kind : film.getKinds()) {
            builder.append("\n - ").append(kind.getDisplayableText());
        }

        builder.append("\nRealizer : ").append(film.getTheRealizer());
        builder.append("\nYear : ").append(film.getYear());
        builder.append("\nActors : ");

        for (Person actor : film.getActors()) {
            builder.append("\n - ").append(actor.getDisplayableText());
        }

        return builder.toString();
    }

    @Override
    public String generateFilmDescriptionForPrinting(Film film) {
        return generateFilmDescription(film);
    }

    @Override
    public Collection<VideoFile> getVideoFiles() {
        Collection<VideoFile> videoFiles = new ArrayList<VideoFile>(10);

        for (Film film : daoFilms.getFilms()) {
            if (!StringUtils.isEmpty(film.getFilePath())) {
                String path;

                if (film.getFilePath().contains("/")) {
                    path = film.getFilePath().substring(film.getFilePath().lastIndexOf('/') + 1);
                } else if (film.getFilePath().contains("\\")) {
                    path = film.getFilePath().substring(film.getFilePath().lastIndexOf('\\') + 1);
                } else {
                    path = film.getFilePath();
                }

                videoFiles.add(new VideoFile(path, film));
            }
        }

        return videoFiles;
    }

    @Override
    @Transactional
    public void clearAll() {
        daoFilms.clearAll();
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }

    @Override
    public Film getEmptyFilm() {
        return daoFilms.createFilm();
    }
}
