package org.jtheque.films.services.impl.utils.web.analyzers;

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

import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.EditArguments;
import org.jtheque.primary.utils.web.analyzers.generic.Analyzer;

import java.util.Scanner;

/**
 * A film analyser. It analyze a line of the website to get the informations on it.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractFilmAnalyzer implements Analyzer {
    private Film film;
    private Scanner scanner;

    private boolean resume;
    private boolean image;
    private boolean date;
    private boolean genre;
    private boolean duration;
    private boolean realizer;
    private boolean actors;

    /**
     * Return the film on which we work.
     *
     * @return The film
     */
    final Film getFilm() {
        return film;
    }

    /**
     * Set the film on which we work.
     *
     * @param film The film
     */
    public final void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Set the scanner on which we read the website.
     *
     * @param scanner The scanner.
     */
    public final void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Analyze a line of the website and extract all the informations.
     *
     * @param line The line we must analyze.
     */
    public final void analyzeLine(String line) {
        findResume(line);
        findImage(line);
        findDate(line);
        findKind(line);
        findDuration(line);
        findRealizer(line);
        findActors(line);
    }

    /**
     * Indicate if the we have all the informations.
     *
     * @return <code>true</code> if we have extracted all the informations else <code>false</code>.
     */
    public final boolean isNotComplete() {
        return !resume ||
                !image ||
                !date ||
                !genre ||
                !duration ||
                !realizer ||
                !actors;
    }

    /**
     * Reset the analyzer.
     */
    public final void reset() {
        resume = false;
        image = false;
        date = false;
        genre = false;
        duration = false;
        realizer = false;
        actors = false;

        scanner.close();
    }

    /**
     * Find the resume on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findResume(String line);

    /**
     * Find the image of the film on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findImage(String line);

    /**
     * Find the date on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findDate(String line);

    /**
     * Find the kind on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findKind(String line);

    /**
     * Find the duration on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findDuration(String line);

    /**
     * Find the realizer on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findRealizer(String line);

    /**
     * Find the actors of the film on the line.
     *
     * @param line The line on which we search.
     */
    protected abstract void findActors(String line);

    /**
     * Set the state of the resume get.
     *
     * @param resume A boolean value indicate the state of the resume get.
     */
    final void setResume(boolean resume) {
        this.resume = resume;
    }

    /**
     * Indicate if the resume is done or not.
     *
     * @return <code>true</code> if we have found the resume else <code>false</code>.
     */
    final boolean isResumeDo() {
        return resume;
    }

    /**
     * Set the state of the image get.
     *
     * @param image A boolean value indicate the state of the image get.
     */
    final void setImage(boolean image) {
        this.image = image;
    }

    /**
     * Indicate if the image is done or not.
     *
     * @return <code>true</code> if we have found the image else <code>false</code>.
     */
    final boolean isImageDo() {
        return image;
    }

    /**
     * Set the state of the date get.
     *
     * @param date A boolean value indicate the state of the date get.
     */
    final void setDate(boolean date) {
        this.date = date;
    }

    /**
     * Indicate if the date is done or not.
     *
     * @return <code>true</code> if we have found the date else <code>false</code>.
     */
    final boolean isDateDo() {
        return date;
    }

    /**
     * Set the state of the kind get.
     *
     * @param genre A boolean value indicate the state of the kind get.
     */
    final void setKind(boolean genre) {
        this.genre = genre;
    }

    /**
     * Indicate if the kind is done or not.
     *
     * @return <code>true</code> if we have found the kind else <code>false</code>.
     */
    final boolean isKindDo() {
        return genre;
    }

    /**
     * Set the state of the duration get.
     *
     * @param duration A boolean value indicate the state of the duration get.
     */
    final void setDuration(boolean duration) {
        this.duration = duration;
    }

    /**
     * Indicate if the duration is done or not.
     *
     * @return <code>true</code> if we have found the duration else <code>false</code>.
     */
    final boolean isDurationDo() {
        return duration;
    }

    /**
     * Set the realizer of the resume get.
     *
     * @param realizer A boolean value indicate the state of the realizer get.
     */
    final void setRealizer(boolean realizer) {
        this.realizer = realizer;
    }

    /**
     * Indicate if the realizer is done or not.
     *
     * @return <code>true</code> if we have found the realizer else <code>false</code>.
     */
    final boolean isRealizerDo() {
        return realizer;
    }

    /**
     * Set the state of the actors get.
     *
     * @param actors A boolean value indicate the state of the actors get.
     */
    public final void setActors(boolean actors) {
        this.actors = actors;
    }

    /**
     * Indicate if the actors is done or not.
     *
     * @return <code>true</code> if we have found the actors else <code>false</code>.
     */
    final boolean isActorsDo() {
        return actors;
    }

    /**
     * Configure the scanner with the arguments of the edit. When we doesn't edit something we pass the state to
     * <code>true</code> to indicate that we mustn't edit this field.
     *
     * @param args The arguments of the edit
     */
    public final void configureWithEditArgs(EditArguments args) {
        actors = !args.isEditActors();
        date = !args.isEditYear();
        duration = !args.isEditDuration();
        image = !args.isEditImage();
        genre = !args.isEditKind();
        realizer = !args.isEditRealizer();
        resume = !args.isEditResume();
    }
}
