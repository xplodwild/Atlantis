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
package fr.miage.atlantis.board;

import fr.miage.atlantis.entities.GameEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant les tiles que l'on place sur le plateau de jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 28/02/2014
 */
public abstract class GameTile {

    /**
     * mName : nom du tile
     */
    private String mName;
    /**
     * mLeftTile : Tile adjacent coté gauche
     */
    private GameTile mLeftTile;
    /**
     * mLeftTile : Tile adjacent coté droit
     */
    private GameTile mRightTile;
    /**
     * mLeftTile : Tile adjacent coté gauche direction haute
     */
    private GameTile mLeftUpperTile;
    /**
     * mLeftTile : Tile adjacent coté gauche direction basse
     */
    private GameTile mLeftBottomTile;
    /**
     * mLeftTile : Tile adjacent coté droit direction haute
     */
    private GameTile mRightUpperTile;
    /**
     * mLeftTile : Tile adjacent coté droit direction basse
     */
    private GameTile mRightBottomTile;
    /**
     * mHeight : Coordonnée hauteur du tile
     */
    private int mHeight;
    /**
     * Entitées de jeu présente sur le tile courant (Animaux/Pions/Bateau...)
     */
    private List<GameEntity> mEntities;
    /**
     * mBoard : Board a laquelle appartient le tile courant
     */
    private GameBoard mBoard;
    /**
     * mIsOnBoard : Indique si la tile est sur le plateau ou retirée
     */
    private boolean mIsOnBoard;
    /**
     * Action a effectuer une fois le tile coulé
     */
    private TileAction mAction;

    /**
     * Constructeur de GameTile
     *
     * @param board Plateau auquel appartient le tile
     * @param name Nom du tile (placement selon le schema établi (de la forme A1
     * B1 B2 ...)
     * @param height hauteur du tile
     */
    GameTile(GameBoard board, String name, int height) {
        this.mBoard = board;
        this.mHeight = height;
        this.mLeftBottomTile = null;
        this.mLeftTile = null;
        this.mLeftUpperTile = null;
        this.mRightBottomTile = null;
        this.mRightTile = null;
        this.mRightUpperTile = null;
        this.mName = name;
        this.mIsOnBoard = true;
        this.mEntities = new ArrayList<GameEntity>();
    }

    /**
     * Constructeur de GameTile
     *
     * @param board Plateau auquel appartient le tile
     * @param hg Tile en haut a gauche du tile courant
     * @param hd Tile en haut a droite du tile courant
     * @param g Tile a gauche du tile courant
     * @param d Tile a droite du tile courant
     * @param bd Tile en bas a droite du tile courant
     * @param bg Tile en bas a gauche du tile courant
     * @param name Nom du tile (placement selon le schema établi (de la forme A1
     * B1 B2 ...)
     * @param height hauteur du tile
     */
    GameTile(GameBoard board, GameTile hg, GameTile hd, GameTile g, GameTile d, GameTile bd, GameTile bg, String name, int height) {
        this.mBoard = board;
        this.mHeight = height;
        this.mLeftBottomTile = bg;
        this.mLeftTile = g;
        this.mLeftUpperTile = hg;
        this.mRightBottomTile = bd;
        this.mRightTile = d;
        this.mRightUpperTile = hd;
        this.mName = name;
        this.mIsOnBoard = true;
        this.mEntities = new ArrayList<GameEntity>();
    }

    /**
     * Ajoute une entité sur le tile courant
     *
     * @param gE Entité de jeu à ajouter sur le tile
     */
    public void addEntity(GameEntity gE) {
        this.mEntities.add(gE);
    }

    /**
     * Enleve une entité sur le tile courant
     *
     * @param gE Entité de jeu a supprimer du tile
     */
    public void removeEntity(GameEntity gE) {
        this.mEntities.remove(gE);
    }

    /**
     * Indique que cette tile a été supprimée du plateau
     */
    public void removeFromBoard() {
        mIsOnBoard = false;
    }

    /**
     * Retourne une tile border avec le flag escape adjacente, ou null si il n'y
     * en a pas
     *
     * @return une tile border avec le flag escape adjacente, ou null si il n'y
     * en a pas
     */
    public BorderTile findEscapeBorder() {
        if (mLeftBottomTile instanceof BorderTile
                && ((BorderTile) mLeftBottomTile).isEscapeBorder()) {
            return (BorderTile) mLeftBottomTile;
        } else if (mLeftTile instanceof BorderTile
                && ((BorderTile) mLeftTile).isEscapeBorder()) {
            return (BorderTile) mLeftTile;
        } else if (mLeftUpperTile instanceof BorderTile
                && ((BorderTile) mLeftUpperTile).isEscapeBorder()) {
            return (BorderTile) mLeftUpperTile;
        } else if (mRightBottomTile instanceof BorderTile
                && ((BorderTile) mRightBottomTile).isEscapeBorder()) {
            return (BorderTile) mRightBottomTile;
        } else if (mRightTile instanceof BorderTile
                && ((BorderTile) mRightTile).isEscapeBorder()) {
            return (BorderTile) mRightTile;
        } else if (mRightUpperTile instanceof BorderTile
                && ((BorderTile) mRightUpperTile).isEscapeBorder()) {
            return (BorderTile) mRightUpperTile;
        }

        return null;
    }

    @Override
    public String toString() {
        String retour = "_____________________________\n";
        retour += "Nom de tile : " + this.mName + "\n";
        retour += "Hauteur : " + this.mHeight + "\n\n";

        if (this.mLeftBottomTile != null) {
            retour += "Tile bas-gauche : " + this.mLeftBottomTile.getName() + "\n";
        } else {
            retour += "Tile bas-gauche : Vide\n";
        }

        if (this.mLeftTile != null) {
            retour += "Tile gauche : " + this.mLeftTile.getName() + "\n";
        } else {
            retour += "Tile gauche : Vide\n";
        }
        if (this.mLeftUpperTile != null) {
            retour += "Tile haut-gauche : " + this.mLeftUpperTile.getName() + "\n";
        } else {
            retour += "Tile haut-gauche : Vide\n";
        }
        if (this.mRightBottomTile != null) {
            retour += "Tile bas-droit : " + this.mRightBottomTile.getName() + "\n";
        } else {
            retour += "Tile bas-droit : Vide\n";
        }
        if (this.mRightTile != null) {
            retour += "Tile droit : " + this.mRightTile.getName() + "\n";
        } else {
            retour += "Tile droit : Vide\n";
        }
        if (this.mRightUpperTile != null) {
            retour += "Tile haut-droit : " + this.mRightUpperTile.getName() + "\n";
        } else {
            retour += "Tile haut-droit : Vide\n";
        }
        retour += "_____________________________\n\n";

        return retour;
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    public List<GameEntity> getEntities() {
        return mEntities;
    }

    public TileAction getAction() {
        return mAction;
    }

    public int getHeight() {
        return mHeight;
    }

    public GameBoard getBoard() {
        return mBoard;
    }

    public boolean isOnBoard() {
        return mIsOnBoard;
    }

    public String getName() {
        return mName;
    }

    public GameTile getLeftTile() {
        return mLeftTile;
    }

    public GameTile getRightTile() {
        return mRightTile;
    }

    public GameTile getLeftUpperTile() {
        return mLeftUpperTile;
    }

    public GameTile getLeftBottomTile() {
        return mLeftBottomTile;
    }

    public GameTile getRightUpperTile() {
        return mRightUpperTile;
    }

    public GameTile getRightBottomTile() {
        return mRightBottomTile;
    }
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    //SETTERS                                                                  |
    //--------------------------------------------------------------------------
    public void setName(String mName) {
        this.mName = mName;
    }

    public void setLeftTile(GameTile mLeftTile) {
        this.mLeftTile = mLeftTile;
    }

    public void setRightTile(GameTile mRightTile) {
        this.mRightTile = mRightTile;
    }

    public void setLeftUpperTile(GameTile mLeftUpperTile) {
        this.mLeftUpperTile = mLeftUpperTile;
    }

    public void setLeftBottomTile(GameTile mLeftBottomTile) {
        this.mLeftBottomTile = mLeftBottomTile;
    }

    public void setRightUpperTile(GameTile mRightUpperTile) {
        this.mRightUpperTile = mRightUpperTile;
    }

    public void setRightBottomTile(GameTile mRightBottomTile) {
        this.mRightBottomTile = mRightBottomTile;
    }

    public void setIsOnBoard(boolean mIsOnBoard) {
        this.mIsOnBoard = mIsOnBoard;
    }

    public void setAction(TileAction action) {
        this.mAction = action;
    }
    //--------------------------------------------------------------------------
}
