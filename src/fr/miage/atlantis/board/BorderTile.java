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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Tile de type plage
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 28/02/2014
 */
public class BorderTile extends GameTile {

    /**
     * Attribut discriminant si oui ou non le tile frontiere est
     */
    private boolean mIsEscapeBorder;

    /**
     * Constructeur de BorderTile
     *
     * @param board Plateau de jeu auquel appartient le tiles de type frontiere
     * @param name Nom du tile
     */
    public BorderTile(GameBoard board, String name) {
        super(board, name, -99);
        this.mIsEscapeBorder = false;
    }

    /**
     * Constructeur de BorderTile
     *
     * @param board Plateau de jeu auquel appartient le tiles de type frontiere
     * @param name Nom du tile
     * @param isEscapeBorder true si la frontiere represente les echapatoires
     * pour les pions dans les 4 coins du board
     */
    public BorderTile(GameBoard board, String name, boolean isEscapeBorder) {
        super(board, name, -99);
        this.mIsEscapeBorder = isEscapeBorder;
    }
    
    public BorderTile(GameBoard board, DataInputStream stream) throws IOException {
        super(board, stream);
        readSerialized(stream);
    }

    @Override
    public final void readSerialized(DataInputStream data) throws IOException {
        super.readSerialized(data);
        mIsEscapeBorder = data.readBoolean();
    }

    @Override
    public void serializeTo(DataOutputStream data) throws IOException {
        super.serializeTo(data);
        data.writeBoolean(mIsEscapeBorder);
    }
    
    @Override
    public int getType() {
        return TILE_BORDER;
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    public boolean isEscapeBorder() {
        return mIsEscapeBorder;
    }
    //--------------------------------------------------------------------------  
}
