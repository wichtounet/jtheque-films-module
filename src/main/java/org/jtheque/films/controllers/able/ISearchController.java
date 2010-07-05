package org.jtheque.films.controllers.able;

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
