package org.jtheque.films.services.impl.utils.web.analyzers;

import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.primary.utils.web.analyzers.generic.Analyzer;

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
 * An abstract film result analyzer.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractFilmResultAnalyzer implements Analyzer {
    private Scanner scanner;

    private boolean complete;

    private final Collection<FilmResult> results = new ArrayList<FilmResult>(10);

    /**
     * Return all the results.
     *
     * @return The results of the research.
     */
    public final Collection<FilmResult> getResults() {
        return results;
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
        findFilms(line);
    }

    /**
     * Indicate if the we have all the informations.
     *
     * @return <code>true</code> if we have extracted all the informations else <code>false</code>.
     */
    public final boolean isNotComplete() {
        return !complete;
    }

    /**
     * Reset the analyzer.
     */
    public final void reset() {
        complete = false;

        results.clear();

        scanner.close();
    }

    /**
     * Set the complete flag.
     *
     * @param complete true if the process is finish else false.
     */
    final void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * Find films in the line.
     *
     * @param line The line to analyze.
     */
    abstract void findFilms(String line);
}