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

import com.jme3.math.Vector3f;
import fr.miage.atlantis.board.TileAction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GameHud {

    private HudManager mHudManager;
    private AbstractDisplay mRightClickToCancel;
    private List<TileActionDisplay> mPlayerTiles;

    public GameHud(HudManager man) {
        mHudManager = man;
        mPlayerTiles = new ArrayList<TileActionDisplay>();
        setup();
    }

    private void setup() {
        mRightClickToCancel = new AbstractDisplay(52, 75, "RightClick Cancel Hint",mHudManager.getAssetManager());
        mRightClickToCancel.showImage("Interface/HintRightClickCancel.png");
        mRightClickToCancel.setAlpha(0.0f);
        mHudManager.displayBottomRight(mRightClickToCancel);
    }

    /**
     * Met en opacité complète le hint indiquant qu'on peut faire un clic droit pour annuler
     */
    public void showRightClickHint() {
        mHudManager.getAnimator().animateFade(mRightClickToCancel, 1.0f);
    }

    /**
     * "Cache" (rend plus transparent) le hint indiquant qu'on peut faire un clic droit pour annuler
     */
    public void hideRightClickHint() {
        mHudManager.getAnimator().animateFade(mRightClickToCancel, 0.5f);
    }

    /**
     * Affiche les tiles d'action du joueur
     * @param actions Liste des tiles d'action
     */
    public void displayPlayerTiles(List<TileAction> actions) {
        int x = 0;
        final float scale = 0.5f;

        // On enlève les tiles précédentes
        for (TileActionDisplay tad : mPlayerTiles) {
            mHudManager.removeFromDisplay(tad);
        }
        mPlayerTiles.clear();

        // On affiche les nouvelles tiles
        for (TileAction action : actions) {
            TileActionDisplay tad = TileActionDisplay.getTileForAction(action, mHudManager.getAssetManager());
            tad.scale(scale);
            mHudManager.displayAt(tad, x, 0);
            tad.setAlpha(1.0f);
            x += tad.getWidth();

            mPlayerTiles.add(tad);
        }
    }

    /**
     * Cache les tiles d'action du joueur précédemment affichées
     */
    public void hidePlayerTiles() {
        for (TileActionDisplay tad : mPlayerTiles) {
            mHudManager.getAnimator().animateFade(tad, 0.0f);
        }
    }

    /**
     * Retourne la tile pickée aux ooordonnées souris indiquées, ou null si il n'y en a pas
     * @param x Coordonnée X de la souris (de gauche)
     * @param y Coordonnée Y de la souris (du haut)
     * @return Une tileactiondisplay, ou null
     */
    public TileActionDisplay getTileUnderMouse(float x, float y) {
        for (TileActionDisplay tad : mPlayerTiles) {
            // Si la tile est visible
            if (tad.getAlpha() > 0.5f) {
                Vector3f pos = tad.getLocalTranslation();

                // Si la souris est dans la tile
                if (x >= pos.x && x <= pos.x + tad.getWidth()
                        && y >= pos.y && y <= pos.y + tad.getHeight()) {
                    return tad;
                }
            }
        }

        return null;
    }
}
