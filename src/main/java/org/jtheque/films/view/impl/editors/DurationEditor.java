package org.jtheque.films.view.impl.editors;

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