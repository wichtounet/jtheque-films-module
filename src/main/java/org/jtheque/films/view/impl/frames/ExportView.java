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
import org.jtheque.core.managers.view.impl.components.panel.FileChooserPanel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.search.Searcher;
import org.jtheque.films.view.able.IExportView;
import org.jtheque.films.view.impl.actions.file.AcValidateExportView;
import org.jtheque.films.view.impl.panels.search.JPanelFilmSearch;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.GridBagUtils;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * The frame used to define the options of an export.
 *
 * @author Baptiste Wicht
 */
public final class ExportView extends SwingDialogView implements IExportView {
    private static final long serialVersionUID = -1591189533123010866L;

    private FileChooserPanel chooser;
    private JPanelFilmSearch searchPanel;

    /**
     * Construct a new <code>JFrameExport</code>.
     *
     * @param parent The parent frame.
     */
    public ExportView(Frame parent) {
        super(parent);

        build();
    }

    /**
     * Build the view.
     */
    private void build() {
        setTitleKey("export.view.title");
        setContentPane(buildContentPane());

        pack();
        setLocationRelativeTo(getOwner());
    }

    /**
     * Return the content pane initialized.
     *
     * @return The content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new JThequePanelBuilder();

        chooser = new FileChooserPanel();
        chooser.setTextKey("export.view.filePath");
        chooser.setBackground(Color.white);
        builder.add(chooser, builder.gbcSet(0, 0));

        searchPanel = new JPanelFilmSearch();
        searchPanel.setBorder(Borders.createTitledBorder("export.view.search"));
        builder.add(searchPanel, builder.gbcSet(0, 1, GridBagUtils.BOTH));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL),
                new AcValidateExportView(), new CloseViewAction("generic.view.actions.cancel", this));

        return builder.getPanel();
    }

    @Override
    public Searcher<Film> getSearcher() {
        return searchPanel.getSearcher();
    }

    @Override
    public String getFilePath() {
        return chooser.getFilePath();
    }

    @Override
    public void sendMessage(String message, Object value) {
        if ("filter".equals(message)) {
            chooser.setFileFilter((SimpleFilter) value);
        }
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        ValidationUtils.rejectIfEmpty(getFilePath(), "export.view.filePath", errors);
    }
}
