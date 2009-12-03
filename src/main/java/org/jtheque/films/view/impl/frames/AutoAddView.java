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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.utils.Constants.Properties.Film;
import org.jtheque.films.utils.Constants.Site;
import org.jtheque.films.view.able.IAutoAddView;
import org.jtheque.films.view.impl.models.able.IAutoAddModel;
import org.jtheque.films.view.impl.models.list.SitesListModel;
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

    private final JThequeSimpleAction chooseSiteAction;
    private final Action closeViewAction;
    private final Action displayInfosAboutSiteAction;
    private final Action validateViewAction;

    /**
     * Construct a new JFrameAutoAdd.
     *
     * @param parent                      The parent frame.
     * @param chooseSiteAction            The action to choose the site.
     * @param closeViewAction             The action to close the view.
     * @param displayInfosAboutSiteAction The action to display informations about site.
     * @param validateViewAction          The action to validate the view.
     */
    public AutoAddView(Frame parent, JThequeSimpleAction chooseSiteAction, Action closeViewAction, Action displayInfosAboutSiteAction,
                       Action validateViewAction) {
        super(parent);

        this.chooseSiteAction = chooseSiteAction;
        this.closeViewAction = closeViewAction;
        this.displayInfosAboutSiteAction = displayInfosAboutSiteAction;
        this.validateViewAction = validateViewAction;
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
        PanelBuilder builder = new PanelBuilder();

        builder.addI18nLabel(Film.TITLE, builder.gbcSet(0, 0));

        fieldTitle = builder.add(new JTextField(), builder.gbcSet(1, 0));
        SwingUtils.addFieldValidateAction(fieldTitle, chooseSiteAction);

        addListsPanel(builder);

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1),
                displayInfosAboutSiteAction, validateViewAction, closeViewAction);

        return builder.getPanel();
    }

    /**
     * Add the list panel.
     *
     * @param parent The parent builder.
     */
    private void addListsPanel(PanelBuilder parent) {
        PanelBuilder builder = parent.addPanel(parent.gbcSet(0, 1, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 2, 1));

        listSites = new JList(new SitesListModel());
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