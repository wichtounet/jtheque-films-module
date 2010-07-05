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

import org.jtheque.films.utils.Constants.Site;
import org.jtheque.films.view.able.IAutoImportView;
import org.jtheque.films.view.impl.actions.auto.imports.AcAddTitle;
import org.jtheque.films.view.impl.actions.auto.imports.AcDeleteTitle;
import org.jtheque.films.view.impl.actions.auto.imports.AcSearchTitles;
import org.jtheque.films.view.impl.actions.auto.imports.AcValidateAutoImportView;
import org.jtheque.films.view.impl.models.able.IAutoImportFilm;

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

    private SimpleComboBoxModel<Site> sitesModel;
    private DefaultListModel modelListTitles;

    private int phase = PHASE_1;

    /**
     * Construct a new JFrameAutoImport.
     *
     * @param parent The parent frame.
     */
    public AutoImportVIew(Frame parent) {
        super(parent);

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
        I18nPanelBuilder builder = new JThequePanelBuilder();

        addDirectoryChooser(builder);
        addFileModeField(builder);

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagConstraints.HORIZONTAL, 2, 1), new AcSearchTitles());

        addTitleList(builder);

        builder.addButtonBar(builder.gbcSet(0, 4, GridBagConstraints.HORIZONTAL, 2, 1),
                new AcDeleteTitle(), new AcAddTitle());

        addWebModeField(builder);
        addSiteField(builder);

        builder.addButtonBar(builder.gbcSet(0, 7, GridBagConstraints.HORIZONTAL, 2, 1),
                new AcValidateAutoImportView(), new CloseViewAction("generic.view.actions.cancel", this));

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
    private void addFileModeField(I18nPanelBuilder builder) {
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
    private void addWebModeField(I18nPanelBuilder builder) {
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
    private void addSiteField(I18nPanelBuilder builder) {
        builder.addI18nLabel("auto.import.web.site", builder.gbcSet(0, 6));

        sitesModel = new SimpleComboBoxModel<Site>(Site.values());

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
        return sitesModel.getSelectedItem();
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
