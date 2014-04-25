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
package fr.miage.atlantis.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import fr.miage.atlantis.board.TileAction;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Message indiquant qu'un joueur a joué une tile à déclencher quand c'est pas son tour
 * (vide)
 */
@Serializable
public class MessageRemoteTile extends AbstractMessage {
    byte[] mData;
    int mPlayerNumber;

    public MessageRemoteTile() {
    }

    public MessageRemoteTile(int playerNumber, TileAction tile) {
        // On fait comme pour la serialisation de GameSaver, sauf qu'au lieu d'écrire dans un
        // fichier, on écrit dans un byte array.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(baos);
        try {
            tile.serializeTo(data);
        } catch (IOException ex) {
            Logger.getLogger(MessageRemoteTile.class.getName()).log(Level.SEVERE, null, ex);
        }
        mData = baos.toByteArray();

        mPlayerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return mPlayerNumber;
    }

    public TileAction getTileAction() {
        ByteArrayInputStream bais = new ByteArrayInputStream(mData);
        DataInputStream data = new DataInputStream(bais);
        try {
            return TileAction.Factory.createFromSerialized(data);
        } catch (IOException ex) {
            Logger.getLogger(MessageRemoteTile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
