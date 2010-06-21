package org.jtheque.films.services.impl.utils.web;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.update.UpdateListener;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.INotesService;
import org.jtheque.films.services.impl.utils.EditArguments;
import org.jtheque.films.services.impl.utils.web.analyzers.GenericFilmAnalyzer;
import org.jtheque.films.services.impl.utils.web.analyzers.GenericFilmResultAnalyzer;
import org.jtheque.films.utils.Constants.Site;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.primary.utils.web.analyzers.generic.GenericGenerator;
import org.jtheque.primary.utils.web.analyzers.generic.Page;
import org.jtheque.primary.utils.web.analyzers.generic.Pages;
import org.jtheque.primary.utils.web.analyzers.generic.operation.ScannerPossessor;
import org.jtheque.primary.utils.web.analyzers.generic.transform.Transformer;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

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

/**
 * A generic web getter.
 *
 * @author Baptiste Wicht
 */
public final class GenericWebGetter extends AbstractWebGetter implements ScannerPossessor, UpdateListener {
    @Resource
    private ISimpleDataService typesService;

    @Resource
    private INotesService notesService;

    @Resource
    private ISimpleDataService languagesService;

    @Resource
    private ISimpleDataService kindsService;

    @Resource
    private IFilmsService filmsService;

    private Scanner scanner;

    private Pages pages;

    private final String xmlFile;
    private final Site site;

    /**
     * Construct a new GenericWebGetter.
     *
     * @param xmlFile The xml file path.
     * @param site    The site of the generic web getter.
     */
    public GenericWebGetter(String xmlFile, Site site) {
        super();

        this.xmlFile = xmlFile;
        this.site = site;

        Managers.getManager(IBeansManager.class).inject(this);

        generate();

        GettersUpdatable.getInstance().addUpdateListener(this);
    }

    /**
     * Fill the web getter using a generic generator.
     */
    private void generate() {
        GenericGenerator generator = new GenericGenerator(this);
        generator.generate(new File(Managers.getCore().getFolders().getApplicationFolder(), "/analyzers/" + xmlFile));

        pages = generator.getPages();

        setAnalyzer(new GenericFilmAnalyzer(generator));
        setResultAnalyzer(new GenericFilmResultAnalyzer(generator, site));
    }

    @Override
    public boolean canGetOn(Site site) {
        return this.site == site;
    }

    @Override
    public Film getFilm(FilmResult search, Film filmToModify, EditArguments args) {
        Film film;

        boolean editActors = true;

        if (filmToModify == null) {
            film = filmsService.getEmptyFilm();
        } else {
            film = filmToModify;

            if (args != null) {
                getAnalyzer().configureWithEditArgs(args);
                editActors = args.isEditActors();
            }
        }

        getAnalyzer().setFilm(film);

        film.setTitle(search.getTitre());
        film.setTheLanguage(languagesService.getDefaultSimpleData());
        film.setNote(notesService.getDefaultNote());
        film.setTheType(typesService.getDefaultSimpleData());

        String index = search.getIndex();

        boolean twoPhase = pages.getActorsPage() != null;

        launchFirstPhase(index, twoPhase);
        launchSecondPhaseIfNeeded(editActors, index, twoPhase);

        getAnalyzer().reset();

        completeKinds(film);

        return film;
    }

    /**
     * Complete the kinds of the film.
     *
     * @param film The film to complete.
     */
    private void completeKinds(Film film) {
        if (film.hasKinds()) {
            film.addKind(kindsService.getDefaultSimpleData());
        }
    }

    /**
     * Launch the first phase of the web getting process.
     *
     * @param index    The index url.
     * @param twoPhase Indicate if there is two phase or not.
     */
    private void launchFirstPhase(String index, boolean twoPhase) {
        try {
            Page filmsPage = pages.getFilmsPage();

            String url = filmsPage.getUrl() + index;

            for (Transformer transformer : filmsPage.getTransformers()) {
                url = transformer.transform(url);
            }

            openConnectionToURL(url);

            getAnalyzer().setScanner(scanner);

            if (twoPhase) {
                getAnalyzer().setActors(true);
            }

            while (scanner.hasNextLine() && getAnalyzer().isNotComplete()) {
                String line = scanner.nextLine().trim();

                getAnalyzer().analyzeLine(line);
            }

        } catch (MalformedURLException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        } catch (IOException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Launch the second phase if needed.
     *
     * @param editActors Indicate if we must edit actors.
     * @param index      The index url.
     * @param twoPhase   Indicate if we must make the second phase or not.
     */
    private void launchSecondPhaseIfNeeded(boolean editActors, String index, boolean twoPhase) {
        if (twoPhase && editActors) {
            try {
                Page actorsPage = pages.getActorsPage();

                String url = actorsPage.getUrl() + index;

                for (Transformer transformer : actorsPage.getTransformers()) {
                    url = transformer.transform(url);
                }

                openConnectionToURL(url);

                getAnalyzer().setScanner(scanner);
                getAnalyzer().setActors(false);

                while (scanner.hasNextLine() && getAnalyzer().isNotComplete()) {
                    String line = scanner.nextLine().trim();

                    getAnalyzer().analyzeLine(line);
                }
            } catch (MalformedURLException e) {
                Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
            } catch (IOException e) {
                Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
            } finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }
    }

    @Override
    public Collection<FilmResult> getFilms(String search) {
        try {
            Page resultsPage = pages.getResultsPage();

            String url = resultsPage.getUrl() + search;

            for (Transformer transformer : resultsPage.getTransformers()) {
                url = transformer.transform(url);
            }

            openConnectionToURL(url);

            getResultAnalyzer().setScanner(scanner);

            while (scanner.hasNextLine() && getResultAnalyzer().isNotComplete()) {
                String line = scanner.nextLine().trim();

                getResultAnalyzer().analyzeLine(line);
            }
        } catch (MalformedURLException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        } catch (IOException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        Collection<FilmResult> films = new ArrayList<FilmResult>(getResultAnalyzer().getResults());

        getResultAnalyzer().reset();

        return films;
    }

    /**
     * Open a connection to the URL.
     *
     * @param url The URL to open connection to.
     *
     * @throws IOException If an error occurs during opening the connection to the URL.
     */
    private void openConnectionToURL(String url) throws IOException {
        URL fileUrl = new URL(url);

        URLConnection urlConnection = fileUrl.openConnection();
        urlConnection.setUseCaches(false);
        urlConnection.connect();

        scanner = new Scanner(urlConnection.getInputStream());
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public void updated() {
        generate();
    }
}