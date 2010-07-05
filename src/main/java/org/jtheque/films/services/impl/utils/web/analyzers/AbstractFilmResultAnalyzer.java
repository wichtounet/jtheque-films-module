package org.jtheque.films.services.impl.utils.web.analyzers;

import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.primary.utils.web.analyzers.generic.Analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

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
