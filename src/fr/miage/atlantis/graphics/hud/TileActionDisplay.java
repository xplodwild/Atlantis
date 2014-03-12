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

    public TileActionDisplay(final AssetManager assetManager) {
        super("HUD TileAction Display", assetManager);
    }

    /**
     * Affiche une tile d'annulation d'action à l'écran
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

            default:
                throw new IllegalStateException("Unknown Cancel Action Entity: " + entity);
        }
    }
}
