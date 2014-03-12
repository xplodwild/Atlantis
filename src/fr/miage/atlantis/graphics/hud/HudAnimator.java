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

    private class State {
        public AbstractDisplay display;
        public int direction;
    }

    private List<State> mActiveStates;
    private List<State> mDeletionStates;

    public HudAnimator() {
        mActiveStates = new ArrayList<State>();
        mDeletionStates = new ArrayList<State>();
    }

    /**
     * Appelé par le renderer, met à jour les animations
     * @param timeDelta Temps en secondes depuis la dernière frame
     */
    public void update(float timeDelta) {
        for (State state : mActiveStates) {
            boolean stop = false;
            float alpha = state.display.getAlpha() + timeDelta * (float) state.direction;
            if (alpha >= 1.0f) {
                alpha = 1.0f;
                stop = true;
            } else if (alpha <= 0.0f) {
                alpha = 0.0f;
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
     * Anime l'élément du hud en fade-in, sur une seconde
     * @param disp L'élément à animer
     */
    public void animateFadeIn(AbstractDisplay disp) {
        disp.setAlpha(0.0f);

        State state = new State();
        state.display = disp;
        state.direction = 1;

        mActiveStates.add(state);
    }

    /**
     * Anime l'élément du hud en fade-out, sur une seconde
     * @param disp L'élément à animer
     */
    public void animateFadeOut(AbstractDisplay disp) {
        disp.setAlpha(1.0f);

        State state = new State();
        state.display = disp;
        state.direction = -1;

        mActiveStates.add(state);
    }

}
