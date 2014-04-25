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
package fr.miage.atlantis.graphics.hud;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant l'animation des éléments du HUD (pas du GUI!)
 */
public class HudAnimator {

    private static final float SPEED_MULT = 3.0f;

    private class State {

        public AbstractDisplay display;
        public int direction;
        public float targetAlpha;
    }
    private List<State> mActiveStates;
    private List<State> mDeletionStates;

    /**
     *
     */
    public HudAnimator() {
        mActiveStates = new ArrayList<State>();
        mDeletionStates = new ArrayList<State>();
    }

    /**
     * Appelé par le renderer, met à jour les animations
     *
     * @param timeDelta Temps en secondes depuis la dernière frame
     */
    public void update(float timeDelta) {
        for (State state : mActiveStates) {
            boolean stop = false;
            float alpha = state.display.getAlpha() + timeDelta * SPEED_MULT * (float) state.direction;
            if (state.direction < 0 && alpha <= state.targetAlpha
                    || state.direction > 0 && alpha >= state.targetAlpha) {
                alpha = state.targetAlpha;
                stop = true;
            }

            state.display.setAlpha(alpha);

            if (stop) {
                mDeletionStates.add(state);
            }
        }

        if (mDeletionStates.size() > 0) {
            for (State state : mDeletionStates) {
                mActiveStates.remove(state);
            }

            mDeletionStates.clear();
        }
    }

    /**
     * Anime l'élément du hud en fade-in ou out, jusqu'à l'alpha target
     *
     * @param disp L'élément à animer
     * @param targetAlpha La transparence finale, entre 0 et 1
     */
    public void animateFade(AbstractDisplay disp, float targetAlpha) {
        State state = new State();
        state.display = disp;
        if (disp.getAlpha() < targetAlpha) {
            state.direction = 1;
        } else {
            state.direction = -1;
        }
        state.targetAlpha = targetAlpha;

        mActiveStates.add(state);
    }
}
