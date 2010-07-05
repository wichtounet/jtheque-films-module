package org.jtheque.films.services.impl.utils.web.analyzers;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.utils.Constants.Site;
import org.jtheque.primary.utils.web.analyzers.generic.GenericGenerator;
import org.jtheque.primary.utils.web.analyzers.generic.field.FieldGetter;
import org.jtheque.primary.utils.web.analyzers.generic.operation.ScannerPossessor;
import org.jtheque.utils.StringUtils;

import java.util.Scanner;
import java.util.regex.Pattern;

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
 * A generic film result analyzer. It seems an objet who read web pages to find the result of a film search.
 *
 * @author Baptitste Wicht
 */
public final class GenericFilmResultAnalyzer extends AbstractFilmResultAnalyzer implements ScannerPossessor {
    private final Site site;

    private final FieldGetter filmsGetter;

    private static final Pattern RESULT_SEPARATOR_PATTERN = Pattern.compile("%%%");
    private static final Pattern FIELD_SEPARATOR_PATTERN = Pattern.compile("---");

    /**
     * Construct a new GenericFilmResultAnalyzer.
     *
     * @param generator The generic generator to use.
     * @param site      The site.
     */
    public GenericFilmResultAnalyzer(GenericGenerator generator, Site site) {
        super();

        filmsGetter = generator.getFieldGetter("films");

        Managers.getManager(IBeansManager.class).inject(this);

        this.site = site;
    }

    @Override
    public Scanner getScanner() {
        return null;
    }

    @Override
    public void findFilms(String line) {
        if (filmsGetter.mustGet(line)) {
            String transformedLine = filmsGetter.performOperations(line, this);

            String value = filmsGetter.getValue(transformedLine);

            if (!StringUtils.isEmpty(value)) {
                String[] results = RESULT_SEPARATOR_PATTERN.split(value);

                for (String r : results) {
                    if (!StringUtils.isEmpty(r)) {
                        String[] parts = FIELD_SEPARATOR_PATTERN.split(r);

                        FilmResult result = new FilmResult();
                        result.setSite(site);
                        result.setIndex(parts[0]);
                        result.setTitre(parts[1]);

                        getResults().add(result);
                    }
                }

                setComplete(true);
            }
        }
    }
}
