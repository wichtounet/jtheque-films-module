package org.jtheque.films.view.impl.panels.config;

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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.films.IFilmsModule;
import org.jtheque.films.services.impl.utils.config.Configuration;
import org.jtheque.films.view.impl.actions.config.AcCheckLendings;
import org.jtheque.films.view.impl.actions.config.AcCheckMail;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.util.Collection;

/**
 * Panel config to configure the lendings options.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigLendings extends JPanel implements ConfigTabComponent {
    private JCheckBox boxControlLendings;
    private JCheckBox boxSendMail;
    private JTextField fieldDays;
    private JTextArea fieldMessage;
    private JCheckBox boxAvertWithDialog;
    private JCheckBox boxAvertWithMail;

    /**
     * Init the panel.
     */
    @PostConstruct
    private void init() {
        build();
        fillAllFields();
    }

    @Override
    public String getTitle() {
        return Managers.getManager(ILanguageManager.class).getMessage("config.view.tab.lendings");
    }

    /**
     * Build the view.
     */
    private void build() {
        I18nPanelBuilder builder = new JThequePanelBuilder(this);

        boxControlLendings = builder.addI18nCheckBox("config.lendings.check", builder.gbcSet(0, 0));
        boxControlLendings.addActionListener(new AcCheckLendings());

        I18nPanelBuilder mailBuilder = builder.addPanel(builder.gbcSet(0, 1));
        mailBuilder.getPanel().setBackground(Color.white);
        mailBuilder.getPanel().setBorder(Borders.createTitledBorder("config.lendings.mail"));

        boxSendMail = mailBuilder.addI18nCheckBox("config.lendings.send", mailBuilder.gbcSet(0, 0));
        boxSendMail.addActionListener(new AcCheckMail());

        mailBuilder.addI18nLabel("config.lendings.days", mailBuilder.gbcSet(0, 1));

        fieldDays = mailBuilder.add(new JTextField(), mailBuilder.gbcSet(1, 1));

        mailBuilder.addI18nLabel("config.lendings.message", mailBuilder.gbcSet(0, 2));

        fieldMessage = new JTextArea();
        fieldMessage.setLineWrap(true);

        mailBuilder.addScrolled(fieldMessage, mailBuilder.gbcSet(0, 3, GridBagUtils.BOTH, 2, 1));

        I18nPanelBuilder notificationsBuilder = builder.addPanel(builder.gbcSet(0, 2));
        notificationsBuilder.getPanel().setBackground(Color.white);
        notificationsBuilder.getPanel().setBorder(Borders.createTitledBorder("config.lendings.notifications"));

        boxAvertWithDialog = notificationsBuilder.addI18nCheckBox("config.lendings.avert.dialog", notificationsBuilder.gbcSet(0, 0));
        boxAvertWithMail = notificationsBuilder.addI18nCheckBox("config.lendings.avert.mail", notificationsBuilder.gbcSet(0, 1));
    }

    /**
     * Fill all the fields with the value of the current configuration.
     */
    private void fillAllFields() {
        boxControlLendings.setSelected(getConfig().mustControlLendingsOnStartup());
        boxSendMail.setSelected(getConfig().areMailSendAutomatically());
        fieldDays.setText(Integer.toString(getConfig().getTimeBeforeAutomaticSend()));
        fieldMessage.setText(getConfig().getAutomaticMail());
        boxAvertWithDialog.setSelected(getConfig().alertWithDialog());
        boxAvertWithMail.setSelected(getConfig().alertWithMail());
    }

    @Override
    public void apply() {
        getConfig().setMustControlLendingsOnStartup(boxControlLendings.isSelected());
        getConfig().setMailSendAutomatically(boxSendMail.isSelected());
        getConfig().setTimeBeforeAutomaticSend(Integer.parseInt(fieldDays.getText()));
        getConfig().setAutomaticMail(fieldMessage.getText());
        getConfig().setAlertWithDialog(boxAvertWithDialog.isSelected());
        getConfig().setAlertWithMail(boxAvertWithMail.isSelected());
    }

    @Override
    public void cancel() {
        fillAllFields();
    }

    /**
     * Return the check box used to set if we must control the lendings or not.
     *
     * @return The check box to configure the lendings.
     */
    public JCheckBox getBoxControlLendings() {
        return boxControlLendings;
    }

    /**
     * Return the checkbox used to set if we must send an email after late lendings.
     *
     * @return The check box used to set if we must send an email after late lendings.
     */
    public JCheckBox getBoxSendMail() {
        return boxSendMail;
    }

    @Override
    public void validate(Collection<JThequeError> errors) {
        ValidationUtils.rejectIfNotNumerical(fieldDays.getText(), "config.lendings.days", errors);
        ValidationUtils.rejectIfEmpty(fieldMessage.getText(), "config.lendings.message", errors);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    /**
     * Set if the lendings configuration is enabled of not.
     *
     * @param selected A boolean tag indicating if the lendings configuration is enabled or not.
     */
    public void setLendingsConfigurationEnabled(boolean selected) {
        boxAvertWithDialog.setEnabled(selected);
        boxAvertWithMail.setEnabled(selected);
        boxSendMail.setEnabled(selected);
        fieldDays.setEnabled(selected);
        fieldMessage.setEnabled(selected);
    }

    /**
     * Set if the mail configuration is enabled of not.
     *
     * @param selected A boolean tag indicating if the mail configuration is enabled or not.
     */
    public void setMailEnabled(boolean selected) {
        fieldDays.setEnabled(selected);
        fieldMessage.setEnabled(selected);
    }

    /**
     * Return the configuration of the films module.
     *
     * @return The configuration of the films module.
     */
    private static Configuration getConfig() {
        return Managers.getManager(IBeansManager.class).<IFilmsModule>getBean("filmsModule").getConfiguration();
    }
}