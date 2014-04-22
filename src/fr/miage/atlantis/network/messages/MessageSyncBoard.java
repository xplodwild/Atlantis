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
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Message donnant l'état entier du board
 * (vide)
 */
@Serializable
public class MessageSyncBoard extends AbstractMessage {
    byte[] mData;
    
    public MessageSyncBoard() {
    }

    public void writeBoard(GameBoard board) throws IOException {
        // On fait comme pour la serialisation de GameSaver, sauf qu'au lieu d'écrire dans un
        // fichier, on écrit dans un byte array. De l'autre côté, on lit, et on régénère le board
        // comme si on déserialisait. Easy breezy, copy pasty.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(baos);
        
        
        // Nombre de tiles dans le board
        Map<String, GameTile> tiles = board.getTileSet();
        data.writeInt(tiles.size());
        Iterator<String> keyIt = tiles.keySet().iterator();
        
        // On écrit d'abord le nom de toutes les tiles, puis on les serialize. Ainsi, on peut
        // créer les tiles et les remplir dans le TileSet. De ce fait, lorsqu'on associe les
        // tiles adjacentes, on a les objets remplis, et non null.
        List<GameTile> tilesToSerialize = new ArrayList<GameTile>();
        
        // Pour chaque tile
        while (keyIt.hasNext()) {
            String tileName = keyIt.next();
            GameTile tile = tiles.get(tileName);
            
            // Nom de la tile
            data.writeUTF(tileName);
            
            // Type de la tile
            data.writeInt(tile.getType());
            
            // Serialisation de la tile
            tilesToSerialize.add(tile);
        }
        
        // Ensuite on serialize les tiles seulement
        for (GameTile tile : tilesToSerialize) {
            tile.serializeTo(data);
        }
    }
}
