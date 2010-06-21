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