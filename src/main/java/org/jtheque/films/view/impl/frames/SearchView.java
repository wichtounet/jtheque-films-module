package org.jtheque.films.view.impl.frames;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.controllers.able.IActorController;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.controllers.able.IRealizerController;
import org.jtheque.films.services.impl.utils.search.Searcher;
import org.jtheque.films.view.able.ISearchView;
import org.jtheque.films.view.impl.actions.search.AcValidateSearchView;
import org.jtheque.films.view.impl.panels.search.JPanelSearch;
import org.jtheque.primary.od.able.Data;

import javax.annotation.PostConstruct;

import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * The GUI of search. From this view, we can search datas in JTheque.
 *
 * @author Baptiste Wicht
 */
public final class SearchView extends SwingDialogView implements ISearchView {
    private static final long serialVersionUID = -8315962232919880990L;

    private JPanelSearch filmSearchPanel;
    private JPanelSearch actorSearchPanel;
    private JPanelSearch realizerSearchPanel;

    private JPanelSearch currentPanel;

    /**
     * Construct a new SearchView.
     *
     * @param parent The parent frame.
     */
    public SearchView(Frame parent) {
        super(parent);
    }

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        currentPanel = filmSearchPanel;

        reload();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Reload the GUI.
     */
    private void reload() {
        setTitle(currentPanel.getTitle());
        setContentPane(buildContentPane());

        pack();
    }

    /**
     * Build the content pane.
     *
     * @return the content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new JThequePanelBuilder();

        builder.add(currentPanel, builder.gbcSet(0, 0));

        builder.addButtonBar(builder.gbcSet(0, 1), new AcValidateSearchView(), new CloseViewAction("generic.view.actions.cancel", this));

        return builder.getPanel();
    }

    @Override
    public Searcher<? extends Data> buildSearcher() {
        return currentPanel.getSearcher();
    }

    @Override
    public void setContentController(Controller content) {
        if (IFilmController.class.isAssignableFrom(content.getClass())) {
            currentPanel = filmSearchPanel;
        }

        if (IActorController.class.isAssignableFrom(content.getClass())) {
            currentPanel = actorSearchPanel;
        }

        if (IRealizerController.class.isAssignableFrom(content.getClass())) {
            currentPanel = realizerSearchPanel;
        }

        reload();

        Managers.getManager(IViewManager.class).refresh(this);
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
    }

    /**
     * Set the panel to search films.
     *
     * @param filmSearchPanel The panel to search films.
     */
    public void setFilmSearchPanel(JPanelSearch filmSearchPanel) {
        this.filmSearchPanel = filmSearchPanel;
    }

    /**
     * Set the panel to search actors.
     *
     * @param actorSearchPanel The panel to search actors.
     */
    public void setActorSearchPanel(JPanelSearch actorSearchPanel) {
        this.actorSearchPanel = actorSearchPanel;
    }

    /**
     * Set the panel to search realizers.
     *
     * @param realizerSearchPanel The panel to search realizers.
     */
    public void setRealizerSearchPanel(JPanelSearch realizerSearchPanel) {
        this.realizerSearchPanel = realizerSearchPanel;
    }
}
