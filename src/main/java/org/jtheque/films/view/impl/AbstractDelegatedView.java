package org.jtheque.films.view.impl;

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
import org.jtheque.core.managers.view.able.IView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.edt.SimpleTask;

/**
 * An abstract delegated view. It seems a view who delegates all the view's operations to an other view.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractDelegatedView implements IView {
    /**
     * Return the implementation of the view.
     *
     * @return The implementation of the view.
     */
    public abstract IView getImplementationView();

    /**
     * Build the delegated view in EDT. This method only call the buildDelegatedView() method in the EDT.
     */
    public final void buildInEDT() {
        Managers.getManager(IViewManager.class).execute(new SimpleTask() {
            @Override
            public void run() {
                buildDelegatedView();
            }
        });
    }

    /**
     * Build the delegated view. This method must only be called from the buildInEDT() method. With that contract
     * respected, this method is always called in the EDT.
     */
    protected abstract void buildDelegatedView();

    @Override
    public final void display() {
        getImplementationView().display();
    }

    @Override
    public final Object getImpl() {
        return getImplementationView().getImpl();
    }

    @Override
    public final void closeDown() {
        getImplementationView().closeDown();
    }

    @Override
    public final void toFirstPlan() {
        getImplementationView().toFirstPlan();
    }

    @Override
    public final void setEnabled(boolean enabled) {
        getImplementationView().setEnabled(enabled);
    }

    @Override
    public final boolean isEnabled() {
        return getImplementationView().isEnabled();
    }

    @Override
    public final void sendMessage(String message, Object value) {
        getImplementationView().sendMessage(message, value);
    }

    @Override
    public final void refresh() {
        getImplementationView().refresh();
    }

    @Override
    public IModel getModel() {
        return getImplementationView().getModel();
    }

    @Override
    public final boolean validateContent() {
        return getImplementationView().validateContent();
    }
}