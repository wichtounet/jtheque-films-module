package org.jtheque.films.services.impl.utils.web;

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

import org.jtheque.films.services.impl.utils.web.analyzers.AbstractFilmAnalyzer;
import org.jtheque.films.services.impl.utils.web.analyzers.AbstractFilmResultAnalyzer;

/**
 * An abstract web getter.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractWebGetter implements WebGetter {
    private AbstractFilmAnalyzer analyzer;
    private AbstractFilmResultAnalyzer resultAnalyzer;

    @Override
    public final AbstractFilmAnalyzer getAnalyzer() {
        return analyzer;
    }

    @Override
    public final void setAnalyzer(AbstractFilmAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @Override
    public final AbstractFilmResultAnalyzer getResultAnalyzer() {
        return resultAnalyzer;
    }

    @Override
    public final void setResultAnalyzer(AbstractFilmResultAnalyzer resultAnalyzer) {
        this.resultAnalyzer = resultAnalyzer;
    }
}