/*
 * Copyright (C) 2014 Loris Durand, Guillaume Lesniak, Cristian Sanna,
 *                    Lucie Wiemert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.miage.atlantis.graphics;

/**
 * Fonction étant appelée au bout d'un certain temps ou nombre de frames rendues
 */
public abstract class FutureCallback {

    private float mTimeRemaining;

    /**
     * Constructeur de FutureCallback
     *
     * @param timeSec temps avant l'appel
     */
    public FutureCallback(float timeSec) {
        mTimeRemaining = timeSec;
    }

    /**
     * Fonction appelée par l'engine - décrémente le temps restant avant l'appel
     * de l'événement
     *
     * @param timeSinceLastFrame
     * @return true si l'élément est arrivé
     */
    boolean decreaseTime(float timeSinceLastFrame) {
        mTimeRemaining -= timeSinceLastFrame;

        if (mTimeRemaining <= 0.0f) {
            onFutureHappened();
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    public abstract void onFutureHappened();
}
