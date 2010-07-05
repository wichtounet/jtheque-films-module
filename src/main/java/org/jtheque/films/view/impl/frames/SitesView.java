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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.log.IJThequeLogger;
import org.jtheque.core.managers.log.Logger;
import org.jtheque.core.managers.view.impl.components.model.SimpleListModel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.controllers.able.ISitesController;
import org.jtheque.films.utils.Constants.Site;
import org.jtheque.films.utils.FileUtils;
import org.jtheque.films.view.able.ISitesView;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.html.HTMLEditorKit;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.IOException;
import java.util.Collection;

/**
 * User interface to display informations about the site used to automatically import films from internet.
 *
 * @author Baptiste Wicht
 */
public final class SitesView extends SwingDialogView implements ISitesView {
    private static final long serialVersionUID = -600407247345067765L;

    private JList listSites;
    private DefaultListModel sitesModel;
    private JTextPane textSites;

    @Logger
    private IJThequeLogger logger;

    @Resource
    private ISitesController sitesController;

    private static final int MINIMUM_WIDTH = 200;

    /**
     * Construct a new JFrameSites.
     *
     * @param parent The parent frame.
     */
    public SitesView(Frame parent) {
        super(parent);
    }

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        setTitleKey("sites.view.title");
        setContentPane(buildContentPane());
        setMinimumSize(new Dimension(MINIMUM_WIDTH, getMinimumSize().height));
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane.
     *
     * @return The content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new JThequePanelBuilder();

        sitesModel = new SimpleListModel<Site>(Site.values());

        listSites = new JList(sitesModel);
        listSites.setSelectedIndex(0);
        listSites.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listSites.getSelectionModel().addListSelectionListener(sitesController);

        builder.addScrolled(listSites, builder.gbcSet(0, 0));

        textSites = new JTextPane();
        textSites.setContentType("text/html");
        textSites.setEditable(false);
        textSites.setEditorKit(new HTMLEditorKit());

        try {
            textSites.setPage(FileUtils.getLocalisedPage(((Site) sitesModel.getElementAt(0)).value()));
        } catch (IOException e) {
            logger.error(e);
            textSites.setText("");
        }

        builder.addScrolled(textSites, builder.gbcSet(1, 0));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, 2, 1), new CloseViewAction("generic.view.actions.cancel", this));

        return builder.getPanel();
    }

    @Override
    public JTextPane getSitesTextComponent() {
        return textSites;
    }

    @Override
    public String getSelectedSite() {
        return ((Site) sitesModel.getElementAt(listSites.getSelectedIndex())).value();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
    }
}
