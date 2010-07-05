package org.jtheque.films.view.impl.models.spinner;

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
