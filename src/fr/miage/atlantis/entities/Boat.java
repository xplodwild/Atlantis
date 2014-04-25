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
package fr.miage.atlantis.entities;

import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.logic.GameLogic;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe generant les bateau
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014
 */
public class Boat extends GameEntity {

    /**
     * Constante maximum de joueurs par bateau
     */
    private static final int MAX_PLAYER_PER_BOAT = 3;
    /**
     * Liste des tokens present sur le bateau
     */
    private List<PlayerToken> mOnboard;

    /**
     * Constructeur du bateau
     *
     * @param tile Tile sur lequel est placé le bateau.
     */
    public Boat() {
        this("Boat", true);
    }

    /**
     * Constructeur du Bateau
     *
     * @param name le nom du bateau
     */
    public Boat(String name) {
        this(name, false);
    }

    /**
     * Constructeur du Bateau
     *
     * @param name
     * @param appendUniqueID
     */
    public Boat(String name, boolean appendUniqueID) {
        super(name, appendUniqueID);
        this.mOnboard = new ArrayList();
    }

    /**
     * Deplace cette entité sur le Tile tile, avec la logique de jeu logic
     *
     * @param logic Logique de jeu à adopter
     * @param tile Tile ou l'on deplace l'entité
     * @return true si une action s'est déroulée via onEntityCross
     */
    @Override
    public boolean moveToTile(GameLogic logic, GameTile tile) {
        // On déplace les entités qui sont sur le bateau aussi. On passe pas la logique en
        // argument pour ne pas déclencher les événements de onEntityCross.
        for (PlayerToken passenger : mOnboard) {
            passenger.moveToTile(null, tile);
        }

        return super.moveToTile(logic, tile);
    }

    /**
     * Ajoute un PlayerToken sur le bateau
     *
     * @param token Token a ajouter
     */
    public void addPlayer(PlayerToken token) {
        if (this.hasRoom()) {
            this.mOnboard.add(token);
        } else {
            throw new IllegalStateException("Trying to add a player to a full boat!");
        }
    }

    /**
     * Enlève un PlayerToken du bateau
     *
     * @param token Token a enlever
     */
    public void removePlayer(PlayerToken token) {
        mOnboard.remove(token);
    }

    /**
     * Retourne le numéro du slot du PlayerToken
     */
    public int getPlayerSlot(PlayerToken token) {
        int pos = mOnboard.indexOf(token);
        if (pos >= 0) {
            return pos;
        } else {
            throw new IllegalArgumentException("This PlayerToken isn't on this boat!");
        }
    }

    /**
     * Test si il y a toujours de la place dans le bateau
     *
     * @return true si il y a de la place, false sinon
     */
    public boolean hasRoom() {
        boolean retour = false;
        if (Boat.MAX_PLAYER_PER_BOAT > this.mOnboard.size()) {
            retour = true;
        }
        return retour;
    }

    /**
     * Renvoie si oui ou non le bateau appartient (= est contrôlé par) au joueur
     * p
     *
     * @param p Le joueur
     * @return true si le joueur passé contrôle ce bateau
     */
    public boolean belongsToPlayer(Player p) {
        // Un bateau "appartient" au joueur si :
        // - Soit le joueur actuel a la majorité de pions sur le bateau
        // - Soit il y a un nombre égal de pions de chaque joueur sur le bateau
        List<PlayerToken> pions = mOnboard;
        if (pions.size() > 0) {
            int tokensBelongToMe = 0;
            for (PlayerToken pt : pions) {
                if (pt.getPlayer() == p) {
                    tokensBelongToMe++;
                }
            }

            // Déjà, si on a aucun pion à nous, on continue pas
            if (tokensBelongToMe > 0) {
                // Un bateau n'ayant que 3 places, si on a plus qu'un pion sur le bateau, il
                // est à nous
                if (tokensBelongToMe > 1) {
                    return true;
                }

                // Sinon on vérifie qu'on a un pion de chacun
                if (pions.size() == 1) {
                    return true;
                } else if (pions.size() == 2) {
                    // Sachant qu'on a exactement un pion et qu'il y a 2 pions en tout,
                    // on a forcément le même nombre, donc c'est bon.
                    return true;
                } else if (pions.size() == 3) {
                    if (pions.get(0).getPlayer() != pions.get(1).getPlayer()
                            && pions.get(0).getPlayer() != pions.get(2).getPlayer()
                            && pions.get(1).getPlayer() != pions.get(2).getPlayer()) {
                        return true;
                    }
                } else {
                    throw new IllegalStateException("More than 3 tokens on a boat");
                }
            } else {
                return false;
            }
        } else {
            // N'importe qui peut contrôler un bateau vide
            return true;
        }

        return false;
    }

    /**
     * Action au croisement d'une entité avec celle-ci
     *
     * @param logic La GameLogic du jeu
     * @param ent L'entité croisée
     * @return true si quelque chose s'est passé (protip le bateau d'attaque
     * personne)
     */
    @Override
    public boolean onEntityCross(GameLogic logic, GameEntity ent) {
        // Pas besoin de traiter la baleine ici, puisque les événements sont traités dans les deux
        // sens dans GameEntity.
        return false;
    }

    //--------------------------------------------------------------------------
    //GETTERS
    //--------------------------------------------------------------------------
    /**
     * Récupère les pions sur le bateau
     *
     * @return Une liste de PlayerTokens
     */
    public List<PlayerToken> getOnboardTokens() {
        return this.mOnboard;
    }
    //--------------------------------------------------------------------------
}
