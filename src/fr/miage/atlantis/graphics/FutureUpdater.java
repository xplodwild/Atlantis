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

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class FutureUpdater {

    private List<FutureCallback> mFutureCallbacks;

    public FutureUpdater() {
        mFutureCallbacks = new ArrayList<FutureCallback>();
    }

    /**
     * Appelle un FutureCallback après timeFromNow secondes de rendu écoulées
     *
     * @param cb Le callback à appeler
     */
    public void addFutureTimeCallback(FutureCallback cb) {
        // Le rendu survient dans un GLThread à part. Du coup, on verouille la liste quand on
        // la modifie.
        synchronized (this) {
            mFutureCallbacks.add(cb);
        }
    }

    public void update(float tpf) {
        // Traitement de la file de FutureCallbacks. On traite d'abord les callbacks, puis on
        // supprime les callbacks qui sont terminés/survenus.
        synchronized (this) {
            List<FutureCallback> callbacks = new ArrayList<FutureCallback>(mFutureCallbacks);
            for (FutureCallback cb : callbacks) {
                if (cb.decreaseTime(tpf)) {
                    mFutureCallbacks.remove(cb);
                }
            }
        }
    }
}
