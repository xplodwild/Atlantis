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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

    public static final int TILE_NULL = 0;
    public static final int TILE_BORDER = 1;
    public static final int TILE_WATER = 2;
    public static final int TILE_BEACH = 3;
    public static final int TILE_FOREST = 4;
    public static final int TILE_MOUNTAIN = 5;
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
     * Charge la tile depuis l'élément serialisé. Les classes enfant DOIVENT
     * appeler readSerialized et l'overrider si nécessaire pour lire les
     * éléments supplémentaires.
     *
     * @param board
     * @param serial
     * @throws IOException
     */
    GameTile(GameBoard board, DataInputStream serial) throws IOException {
        mBoard = board;
    }

    /**
     * Serialize la tile dans le DataOutputStream indiqué
     *
     * @param data La cible de serialisation
     */
    public void serializeTo(DataOutputStream data) throws IOException {
        data.writeUTF(mName);
        data.writeInt(mHeight);
        data.writeBoolean(mIsOnBoard);

        // Vraiment, on aurait dû faire un tableau
        data.writeBoolean(mLeftBottomTile != null);
        if (mLeftBottomTile != null) {
            data.writeUTF(mLeftBottomTile.getName());
        }

        data.writeBoolean(mLeftTile != null);
        if (mLeftTile != null) {
            data.writeUTF(mLeftTile.getName());
        }

        data.writeBoolean(mLeftUpperTile != null);
        if (mLeftUpperTile != null) {
            data.writeUTF(mLeftUpperTile.getName());
        }

        data.writeBoolean(mRightBottomTile != null);
        if (mRightBottomTile != null) {
            data.writeUTF(mRightBottomTile.getName());
        }

        data.writeBoolean(mRightTile != null);
        if (mRightTile != null) {
            data.writeUTF(mRightTile.getName());
        }

        data.writeBoolean(mRightUpperTile != null);
        if (mRightUpperTile != null) {
            data.writeUTF(mRightUpperTile.getName());
        }

        data.writeInt(mEntities.size());
        for (GameEntity ent : mEntities) {
            data.writeUTF(ent.getName());
        }
    }

    /**
     * ressort une BorderTile d'une sauvegarde
     *
     * @param data cible de serialisation
     * @throws IOException
     */
    public void readSerialized(DataInputStream data) throws IOException {
        mName = data.readUTF();
        mHeight = data.readInt();
        mIsOnBoard = data.readBoolean();

        if (data.readBoolean()) {
            mLeftBottomTile = mBoard.getTileSet().get(data.readUTF());
        }

        if (data.readBoolean()) {
            mLeftTile = mBoard.getTileSet().get(data.readUTF());
        }

        if (data.readBoolean()) {
            mLeftUpperTile = mBoard.getTileSet().get(data.readUTF());
        }

        if (data.readBoolean()) {
            mRightBottomTile = mBoard.getTileSet().get(data.readUTF());
        }

        if (data.readBoolean()) {
            mRightTile = mBoard.getTileSet().get(data.readUTF());
        }

        if (data.readBoolean()) {
            mRightUpperTile = mBoard.getTileSet().get(data.readUTF());
        }

        int entCount = data.readInt();
        for (int i = 0; i < entCount; i++) {
            GameEntity ent = mBoard.getEntity(data.readUTF());
            mEntities.add(ent);
            ent.moveToTile(null, this);
        }
    }

    /**
     * Retourne le type de la tile (constantes TILE_*)
     *
     * @return Le type de la tile
     */
    public abstract int getType();

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

    /**
     * Affiche la tile sous forme de phrase pour plus de lisibilité
     *
     * @return la tile en "français"
     */
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
    /**
     * Recupère une liste d'entité
     *
     * @return les entités
     */
    public List<GameEntity> getEntities() {
        return mEntities;
    }

    /**
     * Recupère l'action de la tile
     *
     * @return l'action
     */
    public TileAction getAction() {
        return mAction;
    }

    /**
     * recupère la hauteur de la tile
     *
     * @return la hauteur
     */
    public int getHeight() {
        return mHeight;
    }

    /**
     * recupère la Board
     *
     * @return la board
     */
    public GameBoard getBoard() {
        return mBoard;
    }

    /**
     * Vérifie si la tile est coulé ou non
     *
     * @return un boolean si oui ou non elle est coulé
     */
    public boolean isOnBoard() {
        return mIsOnBoard;
    }

    /**
     * Recupère le nom de la tile
     *
     * @return le nom de la tile
     */
    public String getName() {
        return mName;
    }

    /**
     * recupère la tile à gauche de la tile
     *
     * @return la tile à gauche
     */
    public GameTile getLeftTile() {
        return mLeftTile;
    }

    /**
     * recupère la tile à droite de la tile
     *
     * @return la tile à droite
     */
    public GameTile getRightTile() {
        return mRightTile;
    }

    /**
     * recupère la tile en haut à gauche de la tile
     *
     * @return la tile en haut à gauche
     */
    public GameTile getLeftUpperTile() {
        return mLeftUpperTile;
    }

    /**
     * recupère la tile en bas à gauche de la tile
     *
     * @return la tile en bas à gauche
     */
    public GameTile getLeftBottomTile() {
        return mLeftBottomTile;
    }

    /**
     * recupère la tile en haut à droite de la tile
     *
     * @return la tile en haut à droite
     */
    public GameTile getRightUpperTile() {
        return mRightUpperTile;
    }

    /**
     * recupère la tile en bas à droite
     *
     * @return la tile en bas à droite
     */
    public GameTile getRightBottomTile() {
        return mRightBottomTile;
    }

    //--------------------------------------------------------------------------
    //SETTERS                                                                  |
    //--------------------------------------------------------------------------
    /**
     * Définit le nom de la tile
     *
     * @param mName nom de la tile
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Définit la tile à gauche de la tile
     *
     * @param mLeftTile tile à gauche de la tile
     */
    public void setLeftTile(GameTile mLeftTile) {
        this.mLeftTile = mLeftTile;
    }

    /**
     * Définit la tile à droite de la tile
     *
     * @param mRightTile tile à droite de la tile
     */
    public void setRightTile(GameTile mRightTile) {
        this.mRightTile = mRightTile;
    }

    /**
     * Définit la tile en haut à gauche de la tile
     *
     * @param mLeftUpperTile tile en haut à gauche de la tile
     */
    public void setLeftUpperTile(GameTile mLeftUpperTile) {
        this.mLeftUpperTile = mLeftUpperTile;
    }

    /**
     * Définit la tile en bas à gauche de la tile
     *
     * @param mLeftBottomTile tile en bas à gauche de la tile
     */
    public void setLeftBottomTile(GameTile mLeftBottomTile) {
        this.mLeftBottomTile = mLeftBottomTile;
    }

    /**
     * Définit la tile en haut à droite de la tile
     *
     * @param mRightUpperTile tile en haut à droite de la tile
     */
    public void setRightUpperTile(GameTile mRightUpperTile) {
        this.mRightUpperTile = mRightUpperTile;
    }

    /**
     * Définit la tile en bas à droite de la tile
     *
     * @param mRightBottomTile tile en bas à droite de la tile
     */
    public void setRightBottomTile(GameTile mRightBottomTile) {
        this.mRightBottomTile = mRightBottomTile;
    }

    /**
     * Définit si la tile est coulé ou non
     *
     * @param mIsOnBoard boolean si tile soulé ou non
     */
    public void setIsOnBoard(boolean mIsOnBoard) {
        this.mIsOnBoard = mIsOnBoard;
    }

    /**
     * Définit l'action d'une tile
     *
     * @param action action de la tile
     */
    public void setAction(TileAction action) {
        this.mAction = action;
    }
}
