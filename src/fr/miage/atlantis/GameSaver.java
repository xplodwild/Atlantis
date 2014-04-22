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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package fr.miage.atlantis;

import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
import fr.miage.atlantis.logic.GameLogic;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe s'occupant de sauver et charger une partie en cours du jeu
 * 
 * Les données sont enregistrées via des Data[In/out]stream, et on gère manuellement l'ordre
 * et les données à enregistrer.
 * 
 * Le board est enregistré en entier. Les tiles peuvent être dans n'importe quel ordre, donc les
 * tiles "en relation" ne sont pas toujours chargées dans le bon ordre. Il faut donc stocker les
 * tiles pas encore présentes au chargement. Bien qu'il est possible de recharger tout le board
 * depuis la sauvegarde, on peut réutiliser la génération automatique faite par GameBoard et juste
 * remplacer les tiles du milieu, en faisant attention de restaurer les entités partout par contre.
 * 
 * Format de la sauvegarde:
 *  - Nombre de joueur
 *  -- Pour chaque joueur :
 *  -- - Nom du joueur
 *  -- - Nombre de tiles d'action
 *  -- -- Pour chaque tile d'action :
 *  -- -- - Serialisation de TileAction
 *  - Numéro du joueur qui est en train de jouer
 *  - Nombre d'entités 
 *  -- Pour chaque entité :
 *  -- - Type d'entité
 *  -- - Nom de la tile d'appartenance
 *  -- - Si PlayerToken :
 *  -- -- - Numéro du joueur d'appartenance
 *  -- -- - Etat (bateau, nage, sauf)
 *  - Nombre de tiles dans le board
 *  -- Pour chaque tile :
 *  -- - Type de la tile
 *  -- - Serialisation de la tile
 *  - Serialisation de GameTurn
 */
public class GameSaver {
    
    public GameSaver() {
        
    }
    
    public void saveToFile(final String path, final GameLogic logic) throws IOException {
        DataOutputStream data = new DataOutputStream(new FileOutputStream(path));
        
        // Nombre de joueurs
        Player[] players = logic.getPlayers();
        
        // Noms des joueurs
        data.writeInt(players.length);
        for (Player p : players) {
            // Nom du joueur
            data.writeUTF(p.getName());
            
            // Nombre de tiles d'action
            List<TileAction> actionTiles = p.getActionTiles();
            data.writeInt(actionTiles.size());
            
            // Serialization de la tile
            for (TileAction tile : actionTiles) {
                tile.serializeTo(data);
            }
        }
        
        // Numéro du joueur en train de jouer
        data.writeInt(logic.getCurrentTurn().getPlayer().getNumber());
        
        // Nombre d'entités
        Set<String> entNames = logic.getBoard().getAllEntities();
        data.writeInt(entNames.size());
        
        // Pour chaque entité
        for (String entName : entNames) {
            int entType = GameEntity.TYPE_NULL;
            GameEntity ent = logic.getBoard().getEntity(entName);
            if (ent instanceof PlayerToken) {
                entType = GameEntity.TYPE_PLAYERTOKEN;
            } else if (ent instanceof Boat) {
                entType = GameEntity.TYPE_BOAT;
            } else if (ent instanceof Shark) {
                entType = GameEntity.TYPE_SHARK;
            } else if (ent instanceof SeaSerpent) {
                entType = GameEntity.TYPE_SEASERPENT;
            } else if (ent instanceof Whale) {
                entType = GameEntity.TYPE_WHALE;
            } else {
                throw new IllegalStateException("Unknown entity type on board!");
            }
            
            // Type d'entité
            data.writeInt(entType);
            
            // Nom de la tile d'appartenance
            data.writeUTF(ent.getTile().getName());
            
            // Si c'est un PlayerToken
            if (entType == GameEntity.TYPE_PLAYERTOKEN) {
                // Numéro du joueur d'appartenance
                PlayerToken pt = (PlayerToken) ent;
                data.writeInt(pt.getPlayer().getNumber());
                
                // Etat
                data.writeInt(pt.getState());
            }
            
            
        }
        
        // Nombre de tiles dans le board
        Map<String, GameTile> tiles = logic.getBoard().getTileSet();
        data.writeInt(tiles.size());
        Iterator<String> keyIt = tiles.keySet().iterator();
        
        // Pour chaque tile
        while (keyIt.hasNext()) {
            String tileName = keyIt.next();
            GameTile tile = tiles.get(tileName);
            
            // Type de la tile
            data.writeInt(tile.getType());
            
            // Serialisation de la tile
            tile.serializeTo(data);
        }

        // Tour actuel
        logic.getCurrentTurn().serializeTo(data);
        
    }
    
    public void loadFromFile(final String path) {
        
    }
    
}
