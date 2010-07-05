package org.jtheque.films.view.impl.panels;

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
    public JPanelInfosPerso() {
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
