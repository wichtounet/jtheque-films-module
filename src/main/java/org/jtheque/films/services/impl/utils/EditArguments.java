package org.jtheque.films.services.impl.utils;

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
 * This class is a bean representing the arguments of an automatic edit. It seems the fields who want to automatically
 * edit.
 *
 * @author Baptiste Wicht
 */
public final class EditArguments {
    private boolean editKind;
    private boolean editRealizer;
    private boolean editYear;
    private boolean editDuration;
    private boolean editActors;
    private boolean editImage;
    private boolean editResume;

    /**
     * Indicate if we have to edit the kind or not.
     *
     * @return <code>true</code> if we have to edit the kind else <code>false</code>.
     */
    public boolean isEditKind() {
        return editKind;
    }

    /**
     * Sets if we have to edit the kind.
     *
     * @param editKind Indicate if we have to edit it.
     */
    public void setEditKind(boolean editKind) {
        this.editKind = editKind;
    }

    /**
     * Indicate if we have to edit the realizer or not.
     *
     * @return <code>true</code> if we have to edit the realizer else <code>false</code>.
     */
    public boolean isEditRealizer() {
        return editRealizer;
    }

    /**
     * Sets if we have to edit the realizer.
     *
     * @param editRealizer Indicate if we have to edit it.
     */
    public void setEditRealizer(boolean editRealizer) {
        this.editRealizer = editRealizer;
    }

    /**
     * Indicate if we have to edit the year or not.
     *
     * @return <code>true</code> if we have to edit the year else <code>false</code>.
     */
    public boolean isEditYear() {
        return editYear;
    }

    /**
     * Sets if we have to edit the year.
     *
     * @param editYear Indicate if we have to edit it.
     */
    public void setEditYear(boolean editYear) {
        this.editYear = editYear;
    }

    /**
     * Indicate if we have to edit the duration or not.
     *
     * @return <code>true</code> if we have to edit the duration else <code>false</code>.
     */
    public boolean isEditDuration() {
        return editDuration;
    }

    /**
     * Sets if we have to edit the duration.
     *
     * @param editDuration Indicate if we have to edit it.
     */
    public void setEditDuration(boolean editDuration) {
        this.editDuration = editDuration;
    }

    /**
     * Indicate if we have to edit the actors or not.
     *
     * @return <code>true</code> if we have to edit the actors else <code>false</code>.
     */
    public boolean isEditActors() {
        return editActors;
    }

    /**
     * Sets if we have to edit the actors.
     *
     * @param editActors Indicate if we have to edit it.
     */
    public void setEditActors(boolean editActors) {
        this.editActors = editActors;
    }

    /**
     * Indicate if we have to edit the image or not.
     *
     * @return <code>true</code> if we have to edit the image else <code>false</code>.
     */
    public boolean isEditImage() {
        return editImage;
    }

    /**
     * Sets if we have to edit the image.
     *
     * @param editImage Indicate if we have to edit it.
     */
    public void setEditImage(boolean editImage) {
        this.editImage = editImage;
    }

    /**
     * Indicate if we have to edit the resume or not.
     *
     * @return <code>true</code> if we have to edit the resume else <code>false</code>.
     */
    public boolean isEditResume() {
        return editResume;
    }

    /**
     * Sets if we have to edit the resume.
     *
     * @param editResume Indicate if we have to edit it.
     */
    public void isEditResume(boolean editResume) {
        this.editResume = editResume;
    }
}
