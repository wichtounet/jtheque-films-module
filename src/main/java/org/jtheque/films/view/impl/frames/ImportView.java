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
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.films.view.able.IImportView;
import org.jtheque.utils.io.SimpleFilter;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * An import view.
 *
 * @author Baptiste Wicht
 */
public final class ImportView extends SwingDialogView implements IImportView {
    private static final long serialVersionUID = 4572891526592518739L;

    private FileChooserPanel chooser;

    private final Action validateAction;
    private final Action closeAction;

    /**
     * Construct a new <code>JFrameImport</code>.
     *
     * @param parent         The parent frame.
     * @param validateAction The action to validate the view.
     * @param closeAction    The action to close the view.
     */
    public ImportView(Frame parent, Action validateAction, Action closeAction) {
        super(parent);

        this.validateAction = validateAction;
        this.closeAction = closeAction;
    }

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        setResizable(false);
        setTitleKey("import.view.title");
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
        PanelBuilder builder = new PanelBuilder();

        chooser = new FileChooserPanel();
        chooser.setTextKey("import.view.filePath");
        chooser.setBackground(Color.white);
        builder.add(chooser, builder.gbcSet(0, 0));

        builder.addButtonBar(builder.gbcSet(0, 1), validateAction, closeAction);

        return builder.getPanel();
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
    }
}