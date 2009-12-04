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

import org.jdesktop.swingx.JXRadioGroup;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.components.panel.FileChooserPanel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.films.utils.Constants.Site;
import org.jtheque.films.view.able.IAutoImportView;
import org.jtheque.films.view.impl.models.able.IAutoImportFilm;
import org.jtheque.films.view.impl.models.combo.SitesComboBoxModel;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.util.Collection;

/**
 * View for the auto-import function.
 *
 * @author Baptiste Wicht
 */
public final class AutoImportVIew extends SwingDialogView implements IAutoImportView {
    private static final long serialVersionUID = 4633039680922071605L;

    private FileChooserPanel directoryChooser;
    private JList listTitles;
    private JXRadioGroup<String> fileMode;
    private JXRadioGroup<String> webMode;

    private SitesComboBoxModel sitesModel;
    private DefaultListModel modelListTitles;

    private int phase = PHASE_1;

    private final Action searchTitlesAction;
    private final Action deleteTitleAction;
    private final Action addTitleAction;
    private final Action validateAction;
    private final Action cancelAction;

    /**
     * Construct a new JFrameAutoImport.
     *
     * @param parent             The parent frame.
     * @param searchTitlesAction The action to search titles.
     * @param deleteTitleAction  The action to delete title.
     * @param addTitleAction     The action to add a title.
     * @param validateAction     The action to validate the view.
     * @param cancelAction       The action to cancel the view.
     */
    public AutoImportVIew(Frame parent, Action searchTitlesAction, Action deleteTitleAction, Action addTitleAction,
                          Action validateAction, Action cancelAction) {
        super(parent);

        this.searchTitlesAction = searchTitlesAction;
        this.deleteTitleAction = deleteTitleAction;
        this.addTitleAction = addTitleAction;
        this.validateAction = validateAction;
        this.cancelAction = cancelAction;

        build();
    }

    /**
     * Init the view.
     */
    private void build() {
        setTitleKey("auto.import.view.title");
        setContentPane(buildContentPane());
        setResizable(false);
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Init the graphics user interface.
     *
     * @return The content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new PanelBuilder();

        addDirectoryChooser(builder);
        addFileModeField(builder);

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagConstraints.HORIZONTAL, 2, 1), searchTitlesAction);

        addTitleList(builder);

        builder.addButtonBar(builder.gbcSet(0, 4, GridBagConstraints.HORIZONTAL, 2, 1),
                deleteTitleAction, addTitleAction);

        addWebModeField(builder);
        addSiteField(builder);

        builder.addButtonBar(builder.gbcSet(0, 7, GridBagConstraints.HORIZONTAL, 2, 1),
                validateAction, cancelAction);

        return builder.getPanel();
    }

    /**
     * Add the directory chooser.
     *
     * @param builder The panel builder.
     */
    private void addDirectoryChooser(PanelBuilder builder) {
        directoryChooser = new FileChooserPanel();
        directoryChooser.setDirectoriesOnly();
        directoryChooser.setTextKey("auto.import.file.folder");

        builder.add(directoryChooser, builder.gbcSet(0, 0, GridBagConstraints.HORIZONTAL, 2, 1));
    }

    /**
     * Add file mode field.
     *
     * @param builder The panel builder.
     */
    private void addFileModeField(PanelBuilder builder) {
        builder.addI18nLabel("auto.import.file.mode", builder.gbcSet(0, 1));

        String[] values = {getMessage("auto.import.file.mode.file"), getMessage("auto.import.file.mode.directory")};

        fileMode = new JXRadioGroup<String>(values);
        fileMode.setBackground(Color.white);
        fileMode.setSelectedValue(values[0]);

        builder.add(fileMode, builder.gbcSet(1, 1));
    }

    /**
     * Add the title list.
     *
     * @param builder The panel builder.
     */
    private void addTitleList(PanelBuilder builder) {
        modelListTitles = new DefaultListModel();

        listTitles = new JList(modelListTitles);
        listTitles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listTitles.setVisibleRowCount(5);

        builder.addScrolled(listTitles, builder.gbcSet(0, 3, GridBagConstraints.HORIZONTAL, 2, 1));
    }

    /**
     * Add the web mode field.
     *
     * @param builder The panel builder.
     */
    private void addWebModeField(PanelBuilder builder) {
        String[] values;
        builder.addI18nLabel("auto.import.web.mode", builder.gbcSet(0, 5));

        values = new String[]{getMessage("auto.import.web.mode.empty"), getMessage("auto.import.web.mode.fill")};

        webMode = new JXRadioGroup<String>(values);
        webMode.setBackground(Color.white);
        webMode.setSelectedValue(values[0]);

        builder.add(webMode, builder.gbcSet(1, 5));
    }

    /**
     * Add site field.
     *
     * @param builder The panel builder.
     */
    private void addSiteField(PanelBuilder builder) {
        builder.addI18nLabel("auto.import.web.site", builder.gbcSet(0, 6));

        sitesModel = new SitesComboBoxModel();

        builder.addComboBox(sitesModel, builder.gbcSet(1, 6));
    }

    @Override
    public String getFolderPath() {
        return directoryChooser.getFilePath();
    }

    @Override
    public String getSelectedTitle() {
        return (String) listTitles.getSelectedValue();
    }

    @Override
    public DefaultListModel getModelTitles() {
        return modelListTitles;
    }

    @Override
    public IAutoImportFilm getModel() {
        return (IAutoImportFilm) super.getModel();
    }

    @Override
    public boolean isFileMode() {
        return fileMode.getSelectedValue().equals(getMessage("auto.import.file.mode.file"));
    }

    @Override
    public boolean isWebMode() {
        return webMode.getSelectedValue().equals(getMessage("auto.import.web.mode.fill"));
    }

    @Override
    public Site getSelectedSite() {
        return sitesModel.getSelectedSite();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        if (PHASE_1 == phase) {
            ValidationUtils.rejectIfEmpty(directoryChooser.getFilePath(), "auto.import.file.folder", errors);
        } else if (PHASE_2 == phase) {
            ValidationUtils.rejectIfEmpty(getModel().getTitles(), "auto.import.view.title", errors);
            ValidationUtils.rejectIfNothingSelected(sitesModel, "auto.import.web.site", errors);
        }
    }

    @Override
    public boolean validateContent(int phase) {
        this.phase = phase;

        return validateContent();
    }
}