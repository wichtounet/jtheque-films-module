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
import org.jtheque.core.managers.view.impl.components.model.SimpleListModel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.utils.Constants.Properties.Film;
import org.jtheque.films.utils.Constants.Site;
import org.jtheque.films.view.able.IAutoAddView;
import org.jtheque.films.view.impl.actions.auto.add.AcChooseSite;
import org.jtheque.films.view.impl.actions.auto.add.AcCloseAutoAddView;
import org.jtheque.films.view.impl.actions.auto.add.AcValidateAutoAddView;
import org.jtheque.films.view.impl.models.able.IAutoAddModel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * User interface to add a film from internet.
 *
 * @author Baptiste Wicht
 */
public final class AutoAddView extends SwingDialogView implements IAutoAddView {
    private static final long serialVersionUID = 4633039680922071605L;

    private JTextField fieldTitle;
    private JList listSites;
    private JList listFilms;

    private DefaultListModel modelListFilms;

    private int phase = PHASE_1;

    /**
     * Construct a new JFrameAutoAdd.
     *
     * @param parent The parent frame.
     */
    public AutoAddView(Frame parent) {
        super(parent);
    }

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        setTitleKey("auto.add.view.title");
        setContentPane(buildContentPane());
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build and return the content pane.
     *
     * @return The content pane.
     */
    private Container buildContentPane() {
        I18nPanelBuilder builder = new JThequePanelBuilder();

        builder.addI18nLabel(Film.TITLE, builder.gbcSet(0, 0));

        Action chooseSiteAction = new AcChooseSite();

        fieldTitle = builder.add(new JTextField(), builder.gbcSet(1, 0));
        SwingUtils.addFieldValidateAction(fieldTitle, chooseSiteAction);

        addListsPanel(builder, chooseSiteAction);

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1),
                new DisplayBeanViewAction("auto.add.view.actions.infos", "sitesView"),
                new AcValidateAutoAddView(),
                new AcCloseAutoAddView());

        return builder.getPanel();
    }

    /**
     * Add the list panel.
     *
     * @param parent           The parent builder.
     * @param chooseSiteAction The action to choose a site.
     */
    private void addListsPanel(PanelBuilder parent, Action chooseSiteAction) {
        PanelBuilder builder = parent.addPanel(parent.gbcSet(0, 1, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 2, 1));

        listSites = new JList(new SimpleListModel<Site>(Site.values()));
        listSites.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        builder.addScrolled(listSites, builder.gbcSet(0, 0, GridBagUtils.BOTH));

        builder.addButton(chooseSiteAction, builder.gbcSet(1, 0));

        modelListFilms = new DefaultListModel();

        listFilms = new JList(modelListFilms);
        listFilms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        builder.addScrolled(listFilms, builder.gbcSet(2, 0, GridBagUtils.BOTH));
    }

    @Override
    public void sendMessage(String message, Object value) {
        if ("title".equals(message)) {
            fieldTitle.setText((String) value);
        }
    }

    @Override
    public JTextField getFieldTitle() {
        return fieldTitle;
    }

    @Override
    public DefaultListModel getModelListFilms() {
        return modelListFilms;
    }

    @Override
    public Site getSelectedSite() {
        return (Site) listSites.getSelectedValue();
    }

    @Override
    public FilmResult getSelectedFilm() {
        return (FilmResult) listFilms.getSelectedValue();
    }

    @Override
    public IAutoAddModel getModel() {
        return (IAutoAddModel) super.getModel();
    }

    @Override
    public boolean validateContent(int phase) {
        this.phase = phase;

        return validateContent();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        if (PHASE_1 == phase) {
            ValidationUtils.rejectIfEmpty(fieldTitle.getText(), "auto.view.title.film", errors);
            ValidationUtils.rejectIfNothingSelected(listSites, "auto.view.site", errors);
        } else if (PHASE_2 == phase) {
            ValidationUtils.rejectIfNothingSelected(listFilms, "auto.add.view.title.film", errors);
        }
    }
}
