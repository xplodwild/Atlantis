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
import fr.miage.atlantis.board.TileAction;

/**
 *
 */
public class TileActionDisplay extends AbstractDisplay {

    private TileAction mAction;
    public final static int IMAGE_WIDTH = 256;
    public final static int IMAGE_HEIGHT = 223;

    public TileActionDisplay(final AssetManager assetManager, final TileAction action) {
        super(IMAGE_WIDTH, IMAGE_HEIGHT, "HUD TileAction Display", assetManager);
        mAction = action;
    }

    /**
     * Retourne l'action originale représentée par ce display
     *
     * @return L'action originale (TileAction)
     */
    public TileAction getAction() {
        return mAction;
    }

    public static TileActionDisplay getTileForAction(TileAction action, AssetManager assetManager) {
        TileActionDisplay tad = new TileActionDisplay(assetManager, action);

        switch (action.getAction()) {
            case TileAction.ACTION_CANCEL_ANIMAL:
                tad.displayActionCancel(action.getEntity());
                break;

            case TileAction.ACTION_SPAWN_ENTITY:
                tad.displayActionSpawn(action.getEntity());
                break;

            case TileAction.ACTION_MOVE_ANIMAL:
                tad.displayActionTeleport(action.getEntity());
                break;

            case TileAction.ACTION_BONUS_BOAT:
                tad.displayActionBonusBoat();
                break;

            case TileAction.ACTION_BONUS_SWIM:
                tad.displayActionBonusSwim();
                break;

            case TileAction.ACTION_VOLCANO:
                tad.displayActionVolcano();
                break;

            case TileAction.ACTION_WHIRL:
                tad.displayActionWhirl();
                break;

            default:
                throw new IllegalArgumentException("Invalid TileAction action value");
        }

        tad.setAlpha(0.0f);

        return tad;
    }

    /**
     * Affiche une tile d'annulation d'action à l'écran
     *
     * @param entity L'entité annulée, l'un de TileAction.ENTITY_**
     */
    public void displayActionCancel(int entity) {
        switch (entity) {
            case TileAction.ENTITY_SHARK:
                // Annulation de requin
                showImage("Interface/TileAction_Cancel_Shark.png");
                break;

            case TileAction.ENTITY_WHALE:
                // Annulation de baleine
                showImage("Interface/TileAction_Cancel_Whale.png");
                break;

            default:
                throw new IllegalStateException("Unknown Cancel Action Entity: " + entity);
        }
    }

    /**
     * Affiche une tile de téléportation d'entité à l'écran
     *
     * @param entity L'entité annulée, l'un de TileAction.ENTITY_**
     */
    public void displayActionTeleport(int entity) {
        switch (entity) {
            case TileAction.ENTITY_SHARK:
                // Téléportation de requin
                showImage("Interface/TileAction_Teleport_Shark.png");
                break;

            case TileAction.ENTITY_WHALE:
                // Téléportation de baleine
                showImage("Interface/TileAction_Teleport_Whale.png");
                break;

            case TileAction.ENTITY_SEASERPENT:
                // Téléportation de kraken
                showImage("Interface/TileAction_Teleport_SeaSerpent.png");
                break;

            default:
                throw new IllegalStateException("Unknown Cancel Action Entity: " + entity);
        }
    }

    public void displayActionBonusSwim() {
        showImage("Interface/TileAction_Move_Swimmer.png");
    }

    public void displayActionBonusBoat() {
        showImage("Interface/TileAction_Move_Boat.png");
    }

    public void displayActionSpawn(int entity) {
        switch (entity) {
            case TileAction.ENTITY_BOAT:
                // Spawn d'un bateau
                showImage("Interface/TileAction_Spawn_Boat.png");
                break;

            case TileAction.ENTITY_SHARK:
                // Spawn d'un requin
                showImage("Interface/TileAction_Spawn_Shark.png");
                break;

            case TileAction.ENTITY_WHALE:
                // Spawn d'une baleine
                showImage("Interface/TileAction_Spawn_Whale.png");
                break;

            default:
                throw new IllegalStateException("Unknown Spawn Action Entity: " + entity);
        }
    }

    public void displayActionVolcano() {
        showImage("Interface/TileAction_Volcano.png");
    }

    public void displayActionWhirl() {
        showImage("Interface/TileAction_Whirlpool.png");
    }
}
