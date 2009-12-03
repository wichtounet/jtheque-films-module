package org.jtheque.films.controllers.impl;

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