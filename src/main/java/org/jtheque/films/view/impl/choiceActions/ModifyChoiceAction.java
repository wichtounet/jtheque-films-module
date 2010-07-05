package org.jtheque.films.view.impl.choiceActions;

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

import org.jtheque.primary.view.impl.choice.AbstractModifyChoiceAction;

/**
 * An action to modify the selected item.
 *
 * @author Baptiste Wicht
 */
public final class ModifyChoiceAction extends AbstractModifyChoiceAction {
    @Override
    public boolean canDoAction(String action) {
        return "edit".equals(action);
    }

    @Override
    public void execute() {
        execute(getSelectedItem(), getContent());
    }
}
