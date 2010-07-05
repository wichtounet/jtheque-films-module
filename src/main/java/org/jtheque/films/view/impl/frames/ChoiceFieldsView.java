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
     * @param parent The parent frame.
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
