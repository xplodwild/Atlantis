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
package fr.miage.atlantis;

import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.entities.PlayerToken;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe représentant un joueur
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014
 */
public class Player {

    /**
     * Pseudo du joueur
     */
    private String mName;
    /**
     * Liste des pions du joueur
     */
    private List<PlayerToken> mTokens;
    /**
     * Liste des Tiles dans la main du joueur
     */
    private List<TileAction> mActionTiles;
    /**
     * Numéro du joueur
     */
    private int mNumber;

    /**
     * Constructeur de Joueur
     *
     * @param name Pseudo
     * @param number Numéro
     * @param createTokens Créer automatiquement 10 tokens
     */
    public Player(final String name, final int number, final boolean createTokens) {
        mName = name;
        mTokens = new ArrayList<PlayerToken>();
        mActionTiles = new ArrayList<TileAction>();
        mNumber = number;

        if (createTokens) {
            createTokens();
        }
    }

    /**
     * Créée les pions associés au joueur
     */
    public final void createTokens() {
        // On génère les 10 tokens par joueur
        mTokens.add(new PlayerToken(this, 6));
        mTokens.add(new PlayerToken(this, 5));
        mTokens.add(new PlayerToken(this, 4));
        mTokens.add(new PlayerToken(this, 3));
        mTokens.add(new PlayerToken(this, 3));
        mTokens.add(new PlayerToken(this, 2));
        mTokens.add(new PlayerToken(this, 2));
        mTokens.add(new PlayerToken(this, 1));
        mTokens.add(new PlayerToken(this, 1));
        mTokens.add(new PlayerToken(this, 1));
    }

    /**
     * Renvoie si oui ou non il existe un nageur appartenant à ce joueur
     *
     * @return true si il y a au moins un nageur
     */
    public boolean hasSwimmer() {
        for (PlayerToken t : mTokens) {
            if (t.getState() == PlayerToken.STATE_SWIMMING) {
                return true;
            }
        }

        return false;
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    public int getNumber() {
        return mNumber;
    }

    public String getName() {
        return mName;
    }

    public List<PlayerToken> getTokens() {
        return mTokens;
    }

    public List<TileAction> getActionTiles() {
        return mActionTiles;
    }

    public void addActionTile(TileAction action) {
        mActionTiles.add(action);
    }

    public void removeActionTile(TileAction action) {
        mActionTiles.remove(action);
    }

    public int getScore() {
        Iterator it = mTokens.iterator();
        int score = 0;
        while (it.hasNext()) {
            PlayerToken ptk = (PlayerToken) it.next();
            if (ptk.getState() == PlayerToken.STATE_SAFE) {
                score += ptk.getPoints();
            }
        }
        return score;
    }
    //--------------------------------------------------------------------------
}
