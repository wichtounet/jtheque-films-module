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
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.view.able.IImportView;
import org.jtheque.films.view.impl.actions.file.AcValidateImportView;
import org.jtheque.utils.io.SimpleFilter;

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

    /**
     * Construct a new <code>JFrameImport</code>.
     *
     * @param parent The parent frame.
     */
    public ImportView(Frame parent) {
        super(parent);

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
        PanelBuilder builder = new JThequePanelBuilder();

        chooser = new FileChooserPanel();
        chooser.setTextKey("import.view.filePath");
        chooser.setBackground(Color.white);
        builder.add(chooser, builder.gbcSet(0, 0));

        builder.addButtonBar(builder.gbcSet(0, 1), new AcValidateImportView(), new CloseViewAction("generic.view.actions.cancel", this));

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
