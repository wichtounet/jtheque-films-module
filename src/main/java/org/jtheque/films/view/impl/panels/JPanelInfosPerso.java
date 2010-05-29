package org.jtheque.films.view.impl.panels;

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
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.utils.Constants.Properties;
import org.jtheque.films.view.able.IInfosPersoView;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.primary.od.able.Notable;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.NotesComboBoxModel;
import org.jtheque.primary.view.impl.renderers.NoteComboRenderer;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import java.util.Collection;

/**
 * Panel to add personal informations on a film.
 *
 * @author Baptiste Wicht
 */
public final class JPanelInfosPerso extends JPanel implements IInfosPersoView {
    private final NotesComboBoxModel modelNote;
    private final JComboBox comboNote;
    private final JTextArea areaComment;

    /**
     * Construct a new JPanelInfosPerso. 
     */
    public JPanelInfosPerso(){
        super();

        I18nPanelBuilder builder = new JThequePanelBuilder(this);

        builder.addI18nLabel(Properties.Film.NOTE, builder.gbcSet(0, 0));

        ListCellRenderer noteRenderer = new NoteComboRenderer(daoNotes);

        modelNote = new NotesComboBoxModel(daoNotes);

        comboNote = builder.addComboBox(modelNote, builder.gbcSet(1, 0));
        comboNote.setEnabled(false);
        comboNote.setRenderer(noteRenderer);

        builder.addI18nLabel(Properties.Film.COMMENT, builder.gbcSet(0, 1));

        areaComment = new JTextArea();
        areaComment.setEnabled(false);

        builder.addScrolled(areaComment, builder.gbcSet(0, 2, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 0, 1.0, 1.0));
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        comboNote.setSelectedItem(((Notable) event.getObject()).getNote());
        areaComment.setText(((Film) event.getObject()).getComment());
    }

    /**
     * Fill the form bean.
     *
     * @param fb The form bean to fill.
     */
    @Override
    public void fillFilm(IFilmFormBean fb) {
        fb.setComment(areaComment.getText());
        fb.setNote(modelNote.getSelectedNote());
    }

    @Override
    public void setEnabled(boolean enabled) {
        areaComment.setEnabled(enabled);
        comboNote.setEnabled(enabled);

        super.setEnabled(enabled);
    }

    /**
     * Validate the view.
     *
     * @param errors The errors list to fill.
     */
    @Override
    public void validate(Collection<JThequeError> errors) {
        ConstraintManager.validate(Properties.Film.COMMENT, areaComment.getText(), errors);
        ConstraintManager.validate(Properties.Film.NOTE, modelNote.getSelectedNote(), errors);
    }

    @Override
    public JComponent getImpl() {
        return this;
    }
}