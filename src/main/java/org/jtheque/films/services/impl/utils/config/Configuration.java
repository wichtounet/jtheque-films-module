package org.jtheque.films.services.impl.utils.config;

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

import org.jtheque.core.managers.state.AbstractState;

/**
 * The application's configuration.
 *
 * @author Baptiste Wicht
 */
public abstract class Configuration extends AbstractState {
    /**
     * Indicate if the mail are send automatically.
     *
     * @return <code>true</code> if the mails are send automatically
     */
    public final boolean areMailSendAutomatically() {
        return Boolean.parseBoolean(getProperty("automaticSend", "false"));
    }

    /**
     * Set if the mail are sent automatically or not.
     *
     * @param value The new value to set.
     */
    public final void setMailSendAutomatically(boolean value) {
        setProperty("automaticSend", Boolean.toString(value));
    }

    /**
     * Return the number of days before send of mail to borrower.
     *
     * @return The number of days.
     */
    public final int getTimeBeforeAutomaticSend() {
        return Integer.parseInt(getProperty("timeBeforeAutomaticSend", "25"));
    }

    /**
     * Set the time before automatic send.
     *
     * @param value The new value to set.
     */
    public final void setTimeBeforeAutomaticSend(int value) {
        setProperty("timeBeforeAutomaticSend", Integer.toString(value));
    }

    /**
     * Return the automatic mail for the borrower.
     *
     * @return The automatic mail.
     */
    public final String getAutomaticMail() {
        return getProperty("automaticMail");
    }

    /**
     * Set the automatic mail. This is the mail who's sent to the borrower if there were late.
     *
     * @param value The new value.
     */
    public final void setAutomaticMail(String value) {
        setProperty("automaticMail", value);
    }

    /**
     * Set if we must control or not the lendings at startup.
     *
     * @param value <code>true</code> if we must check the lendings at startup.
     */
    public final void setMustControlLendingsOnStartup(boolean value) {
        setProperty("controlLendings", Boolean.toString(value));
    }

    /**
     * Indicate if we must control or not the lendings at startup.
     *
     * @return <code>true</code> if we must check the lendings at startup.
     */
    public final boolean mustControlLendingsOnStartup() {
        return Boolean.valueOf(getProperty("controlLendings", "false"));
    }

    /**
     * Set if we alert the user with dialog.
     *
     * @param value A boolean value indicated if we alert the user with dialog.
     */
    public final void setAlertWithDialog(boolean value) {
        setProperty("alertWithDialog", Boolean.toString(value));
    }

    /**
     * Indicate if we alert the user with dialog.
     *
     * @return <code>true</code> if we must avert it else false.
     */
    public final boolean alertWithDialog() {
        return Boolean.valueOf(getProperty("alertWithDialog", "false"));
    }

    /**
     * Indicate if we alert the user with mail.
     *
     * @return <code>true</code> if we must avert it else false.
     */
    public final boolean alertWithMail() {
        return Boolean.valueOf(getProperty("alertWithMail", "false"));
    }

    /**
     * Set if we must alert the user with mail or not.
     *
     * @param value A boolean value indicating if we must avert the user with email.
     */
    public final void setAlertWithMail(boolean value) {
        setProperty("alertWithMail", Boolean.toString(value));
    }

    /**
     * Set the maximum of actors to import when me make an automatic add.
     *
     * @param value The maximum number of actors.
     */
    public final void setNumberOfActors(int value) {
        setProperty("numberOfActors", Integer.toString(value));
    }

    /**
     * Return the maximum numbers of actors to import when me make an automatic add.
     *
     * @return The maximum number of actors.
     */
    public final int getNumberOfActors() {
        return Integer.valueOf(getProperty("numberOfActors", "10"));
    }
}