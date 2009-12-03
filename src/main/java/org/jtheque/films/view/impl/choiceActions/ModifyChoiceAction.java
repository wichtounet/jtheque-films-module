package org.jtheque.films.view.impl.choiceActions;

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

import org.jtheque.films.utils.Constants;
import org.jtheque.primary.controller.able.IBorrowerController;
import org.jtheque.primary.controller.able.ICountryController;
import org.jtheque.primary.controller.able.IKindController;
import org.jtheque.primary.controller.able.ILanguageController;
import org.jtheque.primary.controller.able.ISagaController;
import org.jtheque.primary.controller.able.ITypeController;
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.od.able.Language;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.Saga;
import org.jtheque.primary.od.able.Type;
import org.jtheque.primary.services.able.ICountriesService;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.services.able.ILanguagesService;
import org.jtheque.primary.services.able.ISagasService;
import org.jtheque.primary.services.able.ITypesService;
import org.jtheque.primary.view.impl.choice.AbstractChoiceAction;

import javax.annotation.Resource;

/**
 * An action to modify the selected item.
 *
 * @author Baptiste Wicht
 */
public final class ModifyChoiceAction extends AbstractChoiceAction {
    @Resource
    private IKindController kindController;

    @Resource
    private ITypeController typeController;

    @Resource
    private ILanguageController languageController;

    @Resource
    private ICountryController countryController;

    @Resource
    private IBorrowerController borrowerController;

    @Resource
    private ISagaController sagaController;

    @Override
    public boolean canDoAction(String action) {
        return "edit".equals(action);
    }

    @Override
    public void execute() {
        if (IKindsService.DATA_TYPE.equals(getContent())) {
            kindController.editKind((Kind) getSelectedItem());
        } else if (ITypesService.DATA_TYPE.equals(getContent())) {
            typeController.editType((Type) getSelectedItem());
        } else if (ILanguagesService.DATA_TYPE.equals(getContent())) {
            languageController.editLanguage((Language) getSelectedItem());
        } else if (ICountriesService.DATA_TYPE.equals(getContent())) {
            countryController.editCountry((Country) getSelectedItem());
        } else if (Constants.BORROWERS.equals(getContent())) {
            borrowerController.editBorrower((Person) getSelectedItem());
        } else if (ISagasService.DATA_TYPE.equals(getContent())) {
            sagaController.editSaga((Saga) getSelectedItem());
        }
    }
}