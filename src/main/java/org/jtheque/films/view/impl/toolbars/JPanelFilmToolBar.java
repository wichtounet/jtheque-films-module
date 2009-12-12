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
import org.jtheque.films.view.impl.actions.auto.add.AcAutoAddFilm;
import org.jtheque.films.view.impl.actions.film.AcAutoEdit;
import org.jtheque.films.view.impl.actions.film.AcCancel;
import org.jtheque.films.view.impl.actions.film.AcDelete;
import org.jtheque.films.view.impl.actions.film.AcExportFilm;
import org.jtheque.films.view.impl.actions.film.AcManualEdit;
import org.jtheque.films.view.impl.actions.film.AcNewFilm;
import org.jtheque.films.view.impl.actions.film.AcPrintFilm;
import org.jtheque.films.view.impl.actions.film.AcSaveFilm;
import org.jtheque.films.view.impl.actions.film.AcSendFilmByMail;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.primary.view.impl.components.JDropDownButton;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagConstraints;

/**
 * Tool bar for the film view.
 *
 * @author Baptiste Wicht
 */
public final class JPanelFilmToolBar extends JPanel implements ToolbarView {
    private final JDropDownButton buttonAdd;
    private final JDropDownButton buttonEdit;
    private final JDropDownButton buttonOther;
    
    private final JButton buttonSave;
    private final JButton buttonCancel;
    private final JButton buttonDelete;

    private ViewMode mode = ViewMode.VIEW;

    private final GridBagUtils gbc = new GridBagUtils();

    public JPanelFilmToolBar() {
        super();
        
        setBackground(Color.white);
        setBorder(Borders.DIALOG_BORDER);
        
        buttonSave = new JButton(new AcSaveFilm());
        buttonCancel = new JButton(new AcCancel());
        buttonDelete = new JButton(new AcDelete());

        buttonAdd = new JDropDownButton(new AcNewFilm(), new AcAutoAddFilm());
        buttonEdit = new JDropDownButton(new AcManualEdit(), new AcAutoEdit());
        buttonOther = new JDropDownButton(new AcPrintFilm(), new AcSendFilmByMail(), new AcExportFilm());

        add(Box.createHorizontalGlue(), gbc.gbcSet(0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BASELINE_LEADING, 1.0, 1.0));

        if (mode == ViewMode.VIEW) {
            addViewModeButtons();
        } else {
            addOtherModesButton();
        }
    }
    
    /**
     * Set the display mode.
     *
     * @param mode The display mode.
     */
    void setMode(ViewMode mode) {
        if (this.mode != mode) {

            this.mode = mode;

            remove(buttonSave);
            remove(buttonCancel);
            remove(buttonAdd);
            remove(buttonEdit);
            remove(buttonOther);
            remove(buttonDelete);

            switch (mode) {
                case VIEW:
                    addViewModeButtons();

                    break;
                case NEW:
                case EDIT:
                case AUTO:
                    addOtherModesButton();

                    break;
            }

            Managers.getManager(IViewManager.class).refresh(this);
        }
    }

    /**
     * Add the buttons of the view  mode.
     */
    private void addViewModeButtons() {
        add(buttonAdd, gbc.gbcSet(1, 0));
        add(buttonEdit, gbc.gbcSet(2, 0));
        add(buttonDelete, gbc.gbcSet(3, 0));
        add(buttonOther, gbc.gbcSet(4, 0));
    }

    /**
     * Add the buttons of the other mode.
     */
    private void addOtherModesButton() {
        add(buttonSave, gbc.gbcSet(1, 0));
        add(buttonCancel, gbc.gbcSet(2, 0));
    }

    @Override
    public void setDisplayMode(ViewMode mode) {
        setMode(mode);
    }
}