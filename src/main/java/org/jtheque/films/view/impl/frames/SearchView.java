package org.jtheque.films.view.impl.frames;

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