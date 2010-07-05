package org.jtheque.films.controllers.able;

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

import org.jtheque.films.view.able.IRealizerView;
import org.jtheque.films.view.impl.models.able.IRealizersModel;
import org.jtheque.primary.controller.able.IPrincipalController;
import org.jtheque.primary.od.able.Person;

/**
 * A realizer controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IRealizerController extends IPrincipalController<Person> {
    /**
     * Save the current realizer.
     */
    void save();

    @Override
    IRealizersModel getViewModel();

    @Override
    IRealizerView getView();
}
