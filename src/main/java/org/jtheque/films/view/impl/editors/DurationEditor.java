package org.jtheque.films.view.impl.editors;

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

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * An editor for JSpinner. This editor can display an int value for a duration.
 *
 * @author Baptiste Wicht
 */
public final class DurationEditor extends JTextField implements ChangeListener {
    private static final long serialVersionUID = 3650128325330736407L;

    /**
     * Construct a new DurationEditor for a specific spinner.
     *
     * @param spinner The spinner associated to this editor.
     */
    public DurationEditor(JSpinner spinner) {
        super();

        setOpaque(true);

        SpinnerModel model = spinner.getModel();
        setText((String) model.getValue());
        spinner.addChangeListener(this);

        setColumns(5);
        setEnabled(false);
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        JSpinner spinner = (JSpinner) event.getSource();
        SpinnerModel model = spinner.getModel();
        setText((String) model.getValue());
    }
}
