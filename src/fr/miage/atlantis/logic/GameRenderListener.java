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
package fr.miage.atlantis.logic;

import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.PlayerToken;

/**
 * Interface qui écoute les diverses actions du jeu
 */
public interface GameRenderListener {

    /**
     * Le tour a commencé
     */
    public void onTurnStarted();


    /**
     *
     * @param pt
     */
    public void onInitialTokenPutDone(PlayerToken pt);

    /**
     *
     * @param pt
     */
    public void onInitialBoatPutDone(Boat pt);


    /**
     * ACtion de la tile jouée
     */
    public void onPlayedTileAction();

    /**
     * Déplacement de l'entité terminé
     */
    public void onUnitMoveFinished();

    /**
     * Lorsque le lancé dé est fini
     */
    public void onDiceRollFinished();

    /**
     * Lorsque la tile est coulée
     */
    public void onSinkTileFinished();

    /**
     * Action du entité est terminé
     */
    public void onEntityActionFinished();
}
