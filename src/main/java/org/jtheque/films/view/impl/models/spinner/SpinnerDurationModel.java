package org.jtheque.films.view.impl.models.spinner;

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
import org.jtheque.core.managers.log.ILoggingManager;

import javax.swing.AbstractSpinnerModel;

import java.util.regex.Pattern;

/**
 * A spinner model for the duration.
 *
 * @author Baptiste Wicht
 */
public final class SpinnerDurationModel extends AbstractSpinnerModel {
    private String value;

    private static final int MINUTES_IN_A_HOUR = 60;

    private static final Pattern SEPARATOR_PATTERN = Pattern.compile(":");

    /**
     * Construct a new SpinnerDurationModel.
     *
     * @param value The default value.
     */
    public SpinnerDurationModel(String value) {
        super();

        this.value = value;
    }

    /**
     * Returns the int representation of the current value.
     *
     * @return The int value of the date
     */
    public int intValue() {
        String[] parts = SEPARATOR_PATTERN.split(value);
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return hours * MINUTES_IN_A_HOUR + minutes;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (String) value;
        fireStateChanged();
    }

    @Override
    public Object getNextValue() {
        String[] parts = SEPARATOR_PATTERN.split(value);
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        String strMinutes;

        ++minutes;

        if (minutes == MINUTES_IN_A_HOUR) {
            ++hours;
            minutes = 0;
        }

        strMinutes = minutes < 10 ? "0" + minutes : Integer.toString(minutes);

        Managers.getManager(ILoggingManager.class).getLogger(getClass()).debug("Next value is {}:{}", hours, minutes);

        return hours + ":" + strMinutes;
    }

    @Override
    public Object getPreviousValue() {
        Managers.getManager(ILoggingManager.class).getLogger(getClass()).debug("Get previous value of {}", value);

        String[] parts = SEPARATOR_PATTERN.split(value);
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        String strMinutes;

        --minutes;

        if (minutes == -1) {
            --hours;
            minutes = MINUTES_IN_A_HOUR - 1;
        }

        strMinutes = minutes < 10 ? "0" + minutes : Integer.toString(minutes);

        Managers.getManager(ILoggingManager.class).getLogger(getClass()).debug("Previous value is {}:{}", hours, minutes);

        return hours + ":" + strMinutes;
    }
}