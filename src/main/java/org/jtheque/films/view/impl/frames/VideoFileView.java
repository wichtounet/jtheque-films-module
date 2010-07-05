package org.jtheque.films.view.impl.frames;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.components.panel.FileChooserPanel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.able.IVideoFileView;
import org.jtheque.films.view.impl.actions.video.file.AcValidateVideoFileView;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.Action;
import javax.swing.JComponent;

import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

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

/**
 * @author Baptiste Wicht
 */
public final class VideoFileView extends SwingDialogView implements IVideoFileView {
    private DataContainerCachedComboBoxModel<Film> model;
    private FileChooserPanel fieldFile;

    /**
     * Construct a new VideoFileView modal to his parent view.
     *
     * @param parent The parent frame.
     */
    public VideoFileView(Frame parent) {
        super(parent);

        setContentPane(buildContentPane());
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane.
     *
     * @return The builded content pane.
     */
    private Container buildContentPane() {
        I18nPanelBuilder builder = new JThequePanelBuilder();

        Action validateAction = new AcValidateVideoFileView();

        fieldFile = new FileChooserPanel();
        fieldFile.setTextKey("video.file.view.file");
        SwingUtils.addFieldValidateAction(fieldFile, validateAction);

        builder.add(fieldFile, builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, 2, 1));

        builder.addI18nLabel("video.file.view.film", builder.gbcSet(0, 1));

        model = new DataContainerCachedComboBoxModel<Film>(Managers.getManager(IBeansManager.class).<IFilmsService>getBean("filmsService"));

        JComponent combo = builder.addComboBox(model, builder.gbcSet(1, 1));
        SwingUtils.addFieldValidateAction(combo, validateAction);

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, 2, 1),
                validateAction, new CloseViewAction("generic.view.actions.cancel", this));

        return builder.getPanel();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
    }

    @Override
    public String getFilePath() {
        return fieldFile.getFilePath();
    }

    @Override
    public Film getFilm() {
        return model.getSelectedData();
    }
}
