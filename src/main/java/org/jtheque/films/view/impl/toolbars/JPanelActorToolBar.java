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
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagConstraints;

/**
 * A tool bar for the actor view.
 *
 * @author Baptiste Wicht
 */
public final class JPanelActorToolBar extends JPanel implements ToolbarView {
    private static final long serialVersionUID = -688094676844153411L;

    private JButton buttonAdd;
    private JButton buttonEdit;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JButton buttonDelete;
    private JButton buttonFilmo;

    private ViewMode mode = ViewMode.VIEW;

    private final GridBagUtils gbc = new GridBagUtils();

    private Action saveAction;
    private Action cancelAction;
    private Action deleteAction;
    private Action newAction;
    private Action editAction;
    private Action displayAction;

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        setBackground(Color.white);
        setBorder(Borders.DIALOG_BORDER);

        buttonSave = new JButton(saveAction);
        buttonCancel = new JButton(cancelAction);
        buttonDelete = new JButton(deleteAction);
        buttonAdd = new JButton(newAction);
        buttonEdit = new JButton(editAction);
        buttonFilmo = new JButton(displayAction);

        add(Box.createHorizontalGlue(), gbc.gbcSet(0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.BASELINE_LEADING, 1.0, 1.0));

        if (mode == ViewMode.VIEW) {
            addViewModeButtons();
        } else {
            addOtherModesButtons();
        }
    }

    /**
     * Add the buttons of the view mode.
     */
    private void addViewModeButtons() {
        add(buttonAdd, gbc.gbcSet(1, 0));
        add(buttonEdit, gbc.gbcSet(2, 0));
        add(buttonDelete, gbc.gbcSet(3, 0));
        add(buttonFilmo, gbc.gbcSet(4, 0));
    }

    /**
     * Add the button of the other modes.
     */
    private void addOtherModesButtons() {
        add(buttonSave, gbc.gbcSet(1, 0));
        add(buttonCancel, gbc.gbcSet(2, 0));
    }

    /**
     * Set the display mode of the view.
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
            remove(buttonFilmo);
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

    /**
     * Set the action to save the current actor.
     *
     * @param saveAction The action to save the current actor.
     */
    public void setSaveAction(Action saveAction) {
        this.saveAction = saveAction;
    }

    /**
     * Set the action to cancel the current actor.
     *
     * @param cancelAction The action to cancel the current actor.
     */
    public void setCancelAction(Action cancelAction) {
        this.cancelAction = cancelAction;
    }

    /**
     * Set the action to delete the current actor.
     *
     * @param deleteAction The action to delete the current actor.
     */
    public void setDeleteAction(Action deleteAction) {
        this.deleteAction = deleteAction;
    }

    /**
     * Set the action to create a new actor.
     *
     * @param newAction The action to create a new actor.
     */
    public void setNewAction(Action newAction) {
        this.newAction = newAction;
    }

    /**
     * Set the action to edit the current actor.
     *
     * @param editAction The action to edit the current actor.
     */
    public void setEditAction(Action editAction) {
        this.editAction = editAction;
    }

    /**
     * Set the action to display the filmography.
     *
     * @param displayAction The action to display the filmography.
     */
    public void setDisplayAction(Action displayAction) {
        this.displayAction = displayAction;
    }
}