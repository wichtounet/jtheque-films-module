package org.jtheque.films.view.impl.toolbars;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.films.view.impl.actions.realizer.AcCancelRealizer;
import org.jtheque.films.view.impl.actions.realizer.AcDeleteRealizer;
import org.jtheque.films.view.impl.actions.realizer.AcEditRealizer;
import org.jtheque.films.view.impl.actions.realizer.AcNewRealizer;
import org.jtheque.films.view.impl.actions.realizer.AcSaveRealizer;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridBagConstraints;

/**
 * Tool bar for realizers.
 *
 * @author Baptiste Wicht
 */
public final class JPanelRealizerToolBar extends JPanel implements ToolbarView {
    private static final long serialVersionUID = -688094676844153411L;

    private final JButton buttonAdd;
    private final JButton buttonEdit;
    private final JButton buttonSave;
    private final JButton buttonCancel;
    private final JButton buttonDelete;

    private ViewMode mode = ViewMode.VIEW;

    private final GridBagUtils gbc = new GridBagUtils();

    public JPanelRealizerToolBar() {
        super();

        setBackground(Color.white);
        setBorder(Borders.DIALOG_BORDER);

        buttonSave = new JButton(new AcSaveRealizer());
        buttonCancel = new JButton(new AcCancelRealizer());
        buttonDelete = new JButton(new AcDeleteRealizer());
        buttonAdd = new JButton(new AcNewRealizer());
        buttonEdit = new JButton(new AcEditRealizer());

        add(Box.createHorizontalGlue(), gbc.gbcSet(0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BASELINE_LEADING, 1.0, 1.0));

        if (mode == ViewMode.VIEW) {
            addViewModeButtons();
        } else {
            addOtherModesButtons();
        }
    }

    /**
     * Add the buttons for the view mode.
     */
    private void addViewModeButtons() {
        add(buttonAdd, gbc.gbcSet(1, 0));
        add(buttonEdit, gbc.gbcSet(2, 0));
        add(buttonDelete, gbc.gbcSet(3, 0));
    }

    /**
     * Add the buttons for all the others mode.
     */
    private void addOtherModesButtons() {
        add(buttonSave, gbc.gbcSet(1, 0));
        add(buttonCancel, gbc.gbcSet(2, 0));
    }

    /**
     * Set the current mode of the view.
     *
     * @param mode The new current mode.
     */
    void setMode(ViewMode mode) {
        if (this.mode != mode) {
            this.mode = mode;

            remove(buttonSave);
            remove(buttonCancel);
            remove(buttonAdd);
            remove(buttonEdit);
            remove(buttonDelete);

            switch (mode) {
                case VIEW:
                    addViewModeButtons();

                    break;
                case NEW:
                case EDIT:
                case AUTO:
                    addOtherModesButtons();

                    break;
            }

            Managers.getManager(IViewManager.class).refresh(this);
        }
    }

    @Override
    public void setDisplayMode(ViewMode mode) {
        setMode(mode);
    }
}