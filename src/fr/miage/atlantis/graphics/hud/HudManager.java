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

import com.jme3.asset.AssetManager;
import fr.miage.atlantis.graphics.Game3DRenderer;

/**
 *
 */
public class HudManager {
    private HudAnimator mHudAnimator;
    private GameHud mGameHud;
    private Game3DRenderer mRenderer;

    public HudManager(Game3DRenderer renderer) {
        mHudAnimator = new HudAnimator();
        mRenderer = renderer;
        mGameHud = new GameHud(this);

    }

    public AssetManager getAssetManager() {
        return mRenderer.getAssetManager();
    }

    /**
     * @return L'animateur de HUD
     */
    public HudAnimator getAnimator() {
        return mHudAnimator;
    }

    public Game3DRenderer getRenderer() {
        return mRenderer;
    }

    public GameHud getGameHud() {
        return mGameHud;
    }

    /**
     * Met à jour les éléments du HUD
     * Appelé automatiquement une fois par frame par le Game3DRenderer
     * @param tpf Temps depuis la dernière frame
     */
    public void update(float tpf) {
        mHudAnimator.update(tpf);
    }

    public void removeFromDisplay(AbstractDisplay disp) {
        mRenderer.getGuiNode().detachChild(disp);
    }

    /**
     * Affiche 'disp' à l'endroit spécifié
     * @param disp L'iamge à afficher
     * @param x Position horizontale de l'image, de la gauche
     * @param y Position verticale de l'image, du bas
     */
    public void displayAt(AbstractDisplay disp, int x, int y) {
        disp.setPosition(x, y);
        mRenderer.getGuiNode().attachChild(disp);
    }

    /**
     * Affiche 'disp' au centre de l'écran
     * @param disp L'image à afficher
     */
    public void displayCenter(AbstractDisplay disp) {
        displayAt(disp, mRenderer.getCamera().getWidth() / 2 - disp.getWidth() / 2,
                    mRenderer.getCamera().getHeight() / 2 - disp.getHeight() / 2);
    }

    /**
     * Affiche 'disp' en bas à droite de l'écran
     * @param disp L'image à afficher
     */
    public void displayBottomRight(AbstractDisplay disp) {
        displayAt(disp, mRenderer.getCamera().getWidth() - disp.getWidth() - 25, 25);
    }
    
    public int getScreenWidth() {
        return mRenderer.getCamera().getWidth();
    }
    
    public int getScreenHeight() {
        return mRenderer.getCamera().getHeight();
    }

}
