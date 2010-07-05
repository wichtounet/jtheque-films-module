package org.jtheque.films.services.impl.utils.config;

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

/**
 * This class manage the configuration of the application.
 *
 * @author Baptiste Wicht
 */
public final class ConfigManager extends Configuration {
    /**
     * Init the configuration with the default values.
     */
    public void setDefaults() {
        setAlertWithDialog(false);
        setAlertWithMail(false);
        setAutomaticMail("");
        setMailSendAutomatically(false);
        setMustControlLendingsOnStartup(false);
        setNumberOfActors(8);
        setTimeBeforeAutomaticSend(0);
    }
}
