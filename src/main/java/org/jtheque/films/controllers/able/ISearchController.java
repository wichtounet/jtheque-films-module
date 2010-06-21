package org.jtheque.films.controllers.able;

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

import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.films.services.impl.utils.search.Searcher;
import org.jtheque.films.view.able.ISearchView;
import org.jtheque.primary.controller.able.IPrincipalController;
import org.jtheque.primary.od.able.Data;

/**
 * An search controller specification.
 *
 * @author Baptiste Wicht
 */
public interface ISearchController extends Controller {
    /**
     * Execute the search. Function of the preview mode, we display the results directly in the principal view or in the
     * search view.
     *
     * @param research The search to execute.
     */
    void search(Searcher<? extends Data> research);

    /**
     * Set the type of research.
     *
     * @param controller The new type to set.
     */
    void setResearchController(IPrincipalController<? extends Data> controller);

    @Override
    ISearchView getView();
}
