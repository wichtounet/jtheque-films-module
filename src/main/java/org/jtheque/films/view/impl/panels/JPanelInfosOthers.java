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