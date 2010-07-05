package org.jtheque.films.controllers.impl;

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

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.ISearchController;
import org.jtheque.films.services.impl.utils.search.Searcher;
import org.jtheque.films.view.able.ISearchView;
import org.jtheque.primary.controller.able.IPrincipalController;
import org.jtheque.primary.od.able.Data;

import javax.annotation.Resource;

/**
 * A search controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class SearchController extends AbstractController implements ISearchController {
    private IPrincipalController<? extends Data> actualController;

    @Resource
    private ISearchView searchView;

    @Override
    @SuppressWarnings("unchecked")
    public void search(Searcher searcher) {
        actualController.getViewModel().updateDisplayList(searcher.search(actualController.getDisplayList()));

        actualController.displayView();

        closeView();
    }

    @Override
    public void setResearchController(IPrincipalController<? extends Data> controller) {
        actualController = controller;
        searchView.setContentController(controller);
    }

    @Override
    public ISearchView getView() {
        return searchView;
    }
}
