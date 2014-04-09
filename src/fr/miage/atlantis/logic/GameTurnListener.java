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

import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;

/**
 * Interface des actions possible à chaques tours.
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public interface GameTurnListener {

    /**
     * Action à effectuer au debut du tour du joueur p
     * @param p Joueur qui debute son tour
     */
    public void onTurnStart(Player p);

    /**
     * Action à effectuer lorsqu'un pion a été initialement placé
     */
    public void onInitialTokenPut(PlayerToken pt);

    /**
     * Action à effectuer quant un bateau a été initialement placé (début du jeu)
     * @param b Le bateau placé
     */
    public void onInitialBoatPut(Boat b);


    /**
     * Action a effectuer lors du jeu d'un playtile
     *
     * @param tile
     * @param action TileAction joué
     */
    public void onPlayTileAction(GameTile tile, TileAction action);


    /**
     * Action a realiser lors d'un movement d'entité
     *
     * @param ent Entity a deplacer
     * @param dest Tile de destination
     */
    public void onUnitMove(GameEntity ent, GameTile dest);


    /**
     * Action à effectuer lors d'un lancé de dé
     *
     * @param face Face qui est apparue au lancement
     */
    public void onDiceRoll(int face);


    /**
     * Action a effectuer lors du coulage d'un tile
     *
     * @param tile Tile coulé
     */
    public void onSinkTile(GameTile tile);

    /**
     * Action a effectuer lors d'une action d'entité
     *
     * @param source Entity source
     * @param target Entity cible
     * @param action Action a realiser
     */
    public void onEntityAction(GameEntity source, GameEntity target, int action);

    /**
     * Action annulable a effectuer lors d'une action d'entité
     *
     * @param source Entity source
     * @param target Entity cible
     * @param action Action à réaliser
     */
    public void onCancellableEntityAction(GameEntity source, GameEntity target, int action);

    /**
     * Confirme l'annulation d'une action annulable
     */
    public void onCancelAction();

    /**
     * Action a réaliser lors du spawn d'entité
     *
     * @param spawned Entity qui a spawn
     */
    public void onEntitySpawn(GameEntity spawned);

    /**
     * Action a realiser quand un playertoken monte sur un bateau
     *
     * @param player playertoken concerné
     * @param b bateau concerné
     */
    public void onBoardBoat(PlayerToken player, Boat b);

    /**
     * Action à réaliser lorsqu'une unité meurt
     *
     * @param zombie Unité qui meurt
     */
    public void onUnitDie(GameEntity zombie);

    /**
     * Signale au moteur logique qu'on est descendu d'un bateau
     */
    public void onPlayerDismountBoat(PlayerToken player, Boat b);

    /**
     * Action à réaliser losrqu'une tile se fait aspirer dans un tourbillon
     */
    public void onTileWhirl(final GameTile tile);

    /**
     * Action à réaliser lorsqu'on pick une tile volcan
     */
    public void onTileVolcano();
}
