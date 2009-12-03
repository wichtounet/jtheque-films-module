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
import org.jtheque.primary.view.impl.components.JDropDownButton;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.Action;
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
    private static final long serialVersionUID = -688094676844153411L;

    private JDropDownButton buttonAdd;
    private JDropDownButton buttonEdit;
    private JDropDownButton buttonOther;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JButton buttonDelete;

    private ViewMode mode = ViewMode.VIEW;

    private final GridBagUtils gbc = new GridBagUtils();

    private Action saveAction;
    private Action cancelAction;
    private Action deleteAction;
    private Action newAction;
    private Action autoAddAction;
    private Action manualEditAction;
    private Action autoEditAction;
    private Action printAction;
    private Action sendByMailAction;
    private Action exportAction;

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

        buttonAdd = new JDropDownButton(newAction, autoAddAction);
        buttonEdit = new JDropDownButton(manualEditAction, autoEditAction);
        buttonOther = new JDropDownButton(printAction, sendByMailAction, exportAction);

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

    /**
     * Set the action to save the current film.
     *
     * @param saveAction The action to save the current film.
     */
    public void setSaveAction(Action saveAction) {
        this.saveAction = saveAction;
    }

    /**
     * Set the action to cancel the current film.
     *
     * @param cancelAction The action to cancel the current film.
     */
    public void setCancelAction(Action cancelAction) {
        this.cancelAction = cancelAction;
    }

    /**
     * Set the action to delete the current film.
     *
     * @param deleteAction The action to delete the current film.
     */
    public void setDeleteAction(Action deleteAction) {
        this.deleteAction = deleteAction;
    }

    /**
     * Set the action to delete the current film.
     *
     * @param newAction The action to delete the current film.
     */
    public void setNewAction(Action newAction) {
        this.newAction = newAction;
    }

    /**
     * Set the action to automatically add the current film.
     *
     * @param autoAddAction The action to automatically add the current film.
     */
    public void setAutoAddAction(Action autoAddAction) {
        this.autoAddAction = autoAddAction;
    }

    /**
     * Set the action to manual edit the current film.
     *
     * @param manualEditAction The action to manual edit the current film.
     */
    public void setManualEditAction(Action manualEditAction) {
        this.manualEditAction = manualEditAction;
    }

    /**
     * Set the action to automatically edit the current film.
     *
     * @param autoEditAction The action to automatically edit the current film.
     */
    public void setAutoEditAction(Action autoEditAction) {
        this.autoEditAction = autoEditAction;
    }

    /**
     * Set the action to print the current film.
     *
     * @param printAction The action to print the current film.
     */
    public void setPrintAction(Action printAction) {
        this.printAction = printAction;
    }

    /**
     * Set the action to send by mail the current film.
     *
     * @param sendByMailAction The action to send by mail the current film.
     */
    public void setSendByMailAction(Action sendByMailAction) {
        this.sendByMailAction = sendByMailAction;
    }

    /**
     * Set the action to export the current film.
     *
     * @param exportAction The action to export the current film.
     */
    public void setExportAction(Action exportAction) {
        this.exportAction = exportAction;
    }
}