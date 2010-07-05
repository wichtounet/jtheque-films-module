package org.jtheque.films.view.impl.panels;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.components.panel.FileChooserPanel;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.utils.Constants.Properties;
import org.jtheque.films.view.able.IInfosOthersView;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import javax.swing.JPanel;

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
 * Panel for the others informations about film.
 *
 * @author Baptiste Wicht
 */
public final class JPanelInfosOthers extends JPanel implements IInfosOthersView {
    private FileChooserPanel fieldFile;

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        PanelBuilder builder = new JThequePanelBuilder(this);

        fieldFile = new FileChooserPanel();
        fieldFile.setTextKey(Properties.Film.FILE_PATH);

        builder.add(fieldFile, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 0, 1.0, 1.0));
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        Film film = (Film) event.getObject();

        fieldFile.setFilePath(film.getFilePath());
    }

    /**
     * Fill the form bean.
     *
     * @param fb The form bean to fill.
     */
    @Override
    public void fillFilm(IFilmFormBean fb) {
        fb.setFilePath(fieldFile.getFilePath());
    }

    @Override
    public void setEnabled(boolean enabled) {
        fieldFile.setEnabled(enabled);

        super.setEnabled(enabled);
    }

    /**
     * Validate the view.
     *
     * @param errors The errors list to fill.
     */
    @Override
    public void validate(Collection<JThequeError> errors) {
        ConstraintManager.validate(Properties.Film.FILE_PATH, fieldFile.getFilePath(), errors);
    }

    @Override
    public JComponent getImpl() {
        return this;
    }
}
