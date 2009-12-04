package org.jtheque.films.view.impl.frames;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.components.panel.FileChooserPanel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.able.IVideoFileView;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.Action;
import javax.swing.JComponent;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

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

/**
 * @author Baptiste Wicht
 */
public final class VideoFileView extends SwingDialogView implements IVideoFileView {
    private DataContainerCachedComboBoxModel<Film> model;
    private FileChooserPanel fieldFile;

    private final Action validateAction;
    private final Action closeAction;

    /**
     * Construct a new VideoFileView modal to his parent view.
     *
     * @param parent         The parent frame.
     * @param validateAction The action to validate the view.
     * @param closeAction    The action to close the view.
     */
    public VideoFileView(Frame parent, Action validateAction, Action closeAction) {
        super(parent);

        this.validateAction = validateAction;
        this.closeAction = closeAction;

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
        PanelBuilder builder = new PanelBuilder();

        fieldFile = new FileChooserPanel();
        fieldFile.setTextKey("video.file.view.file");
        SwingUtils.addFieldValidateAction(fieldFile, validateAction);

        builder.add(fieldFile, builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, 2, 1));

        builder.addI18nLabel("video.file.view.film", builder.gbcSet(0, 1));

        model = new DataContainerCachedComboBoxModel<Film>(Managers.getManager(IBeansManager.class).<IFilmsService>getBean("filmsService"));

        JComponent combo = builder.addComboBox(model, builder.gbcSet(1, 1));
        SwingUtils.addFieldValidateAction(combo, validateAction);

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, 2, 1), validateAction, closeAction);

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