package org.jtheque.films.view.impl.models.combo;

import org.jtheque.core.managers.view.impl.components.model.SimpleComboBoxModel;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.films.services.able.ICoverService;
import org.jtheque.films.services.impl.utils.cover.Format;
import org.jtheque.utils.collections.CollectionUtils;

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
 * A combo box model to display a list of format.
 *
 * @author Baptiste Wicht
 */
public final class CoverFormatComboBoxModel extends SimpleComboBoxModel<Format> {
    /**
     * Construct a new CoverFormatComboBoxModel.
     */
    public CoverFormatComboBoxModel() {
        super();

        ICoverService coverService = CoreUtils.getBean("coverService");

        setElements(coverService.getFormats());
    }

    /**
     * Select the first element.
     */
    public void selectFirst() {
        setSelectedItem(CollectionUtils.first(getObjects()));
    }
}
