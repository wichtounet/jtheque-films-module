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
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.films.utils.Constants.Properties.Film;
import org.jtheque.films.view.able.IChoiceFieldsView;
import org.jtheque.films.view.impl.actions.auto.choice.AcValidateChoiceFieldsView;

import javax.swing.JCheckBox;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * The view to choice the fields for the automatic edit of a film.
 *
 * @author Baptiste Wicht
 */
public final class ChoiceFieldsView extends SwingDialogView implements IChoiceFieldsView {
    private static final long serialVersionUID = -1071539900190775950L;

    private JCheckBox boxKind;
    private JCheckBox boxRealizer;
    private JCheckBox boxYear;
    private JCheckBox boxDuration;
    private JCheckBox boxActors;
    private JCheckBox boxImage;
    private JCheckBox boxResume;

    /**
     * Construct a new JFrameChoiceFields.
     *
     * @param parent         The parent frame.
     */
    public ChoiceFieldsView(Frame parent) {
        super(parent);

        setContentPane(buildContentPane());
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build and return the content pane.
     *
     * @return The builded content pane.
     */
    private Container buildContentPane() {
        I18nPanelBuilder builder = new JThequePanelBuilder();

        boxKind = builder.addI18nCheckBox(Film.KIND, builder.gbcSet(0, 0));
        boxRealizer = builder.addI18nCheckBox(Film.REALIZER, builder.gbcSet(0, 1));
        boxYear = builder.addI18nCheckBox(Film.YEAR, builder.gbcSet(0, 2));
        boxDuration = builder.addI18nCheckBox(Film.DURATION, builder.gbcSet(0, 3));
        boxActors = builder.addI18nCheckBox(Film.ACTORS, builder.gbcSet(0, 4));
        boxImage = builder.addI18nCheckBox(Film.IMAGE, builder.gbcSet(0, 5));
        boxResume = builder.addI18nCheckBox(Film.RESUME, builder.gbcSet(0, 6));

        builder.addButtonBar(builder.gbcSet(0, 7), new CloseViewAction("generic.view.actions.cancel", this), new AcValidateChoiceFieldsView());

        return builder.getPanel();
    }

    @Override
    public boolean isBoxKindSelected() {
        return boxKind.isSelected();
    }

    @Override
    public boolean isBoxRealizerSelected() {
        return boxRealizer.isSelected();
    }

    @Override
    public boolean isBoxYearSelected() {
        return boxYear.isSelected();
    }

    @Override
    public boolean isBoxDurationSelected() {
        return boxDuration.isSelected();
    }

    @Override
    public boolean isBoxActorsSelected() {
        return boxActors.isSelected();
    }

    @Override
    public boolean isBoxImageSelected() {
        return boxImage.isSelected();
    }

    @Override
    public boolean isBoxResumeSelected() {
        return boxResume.isSelected();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
    }
}