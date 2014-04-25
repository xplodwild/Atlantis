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

import fr.miage.atlantis.board.BeachTile;
import fr.miage.atlantis.board.BorderTile;
import fr.miage.atlantis.board.ForestTile;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.MountainTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
import fr.miage.atlantis.logic.GameLogic;
import fr.miage.atlantis.logic.GameTurn;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

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
 *  -- - Nom de l'entité
 *  -- - Type d'entité
 *  -- - Nom de la tile d'appartenance
 *  -- - Si PlayerToken :
 *  -- -- - Numéro du joueur d'appartenance
 *  -- -- - Etat (bateau, nage, sauf)
 *  -- -- - Points
 *  -- -- - On est sur un bateau?
 *  -- -- - Nom du bateau si oui ^
 *  - Nombre de tiles dans le board
 *  -- Pour chaque tile :
 *  -- - Type de la tile
 *  -- - Serialisation de la tile
 *  - Serialisation de GameTurn
 *  - Serialisation GameLogic (données essentielles)
 */
public class GameSaver {
    
    private static class PlayerBoatAffectation {
        public PlayerToken player;
        public String boatName;
        public PlayerBoatAffectation(PlayerToken p, String s) {
            player = p;
            boatName = s;
        }
    }
    
    /**
     *
     */
    public GameSaver() {
        
    }
    
    /**
     *
     * @param path
     * @param logic
     * @throws IOException
     */
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
            
            // Nom de l'entité
            data.writeUTF(entName);
            
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
                
                // Points
                data.writeInt(pt.getPoints());
                
                // Si on a un bateau
                data.writeBoolean(pt.getBoat() != null);
                if (pt.getBoat() != null) data.writeUTF(pt.getBoat().getName());
            }
        }
        
        // Nombre de tiles dans le board
        Map<String, GameTile> tiles = logic.getBoard().getTileSet();
        Logger.getGlobal().severe("Tiles count: " + tiles.size());
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

        // Tour actuel
        logic.getCurrentTurn().serializeTo(data);
        
        // GameLogic
        logic.serializeEssentialData(data);
     
        data.close();
    }
    
    /**
     *
     * @param logic
     * @param path
     * @throws IOException
     */
    public void loadFromFile(final GameLogic logic, final String path) throws IOException {
        DataInputStream data = new DataInputStream(new FileInputStream(path));
        
        // Nombre de joueurs
        int playersCount = data.readInt();
        String[] playerNames = new String[playersCount];
        List<TileAction> playerTileAction[] = new ArrayList[playersCount];
        
        // Pour chaque joueur
        for (int i = 0; i < playersCount; i++) {
            // Nom du joueur
            playerNames[i] = data.readUTF();
            playerTileAction[i] = new ArrayList<TileAction>();
            
            // Nombre de tiles d'action
            int tileActionCount = data.readInt();
            
            // Pour chaque tile d'action
            for (int j = 0; j < tileActionCount; j++) {
                TileAction ta = TileAction.Factory.createFromSerialized(data);
                playerTileAction[i].add(ta);
            }
        }
        
        // On prépare le jeu avec nos joueurs mais sans board
        logic.prepareGame(playerNames, false);
        
        // On ré-assigne les TileAction aux joueurs
        Player[] players = logic.getPlayers();
        for (int i = 0; i < playersCount; i++) {
            for (TileAction ta : playerTileAction[i]) {
                players[i].addActionTile(ta);
            }
        }
        
        // Numéro du joueur en train de jouer
        int currentPlayerNumber = data.readInt();
        
        // Nombre d'entités
        int entitiesCount = data.readInt();
        
        // On a besoin de pouvoir stocker les appartenance des joueurs aux bateaux, au cas où
        // les joueurs sont régénérés avant les bateaux
        List<PlayerBoatAffectation> playerBoatAffectations = new ArrayList<PlayerBoatAffectation>();
        List<GameEntity> restoredEntities = new ArrayList<GameEntity>();
        
        // Pour chaque entité
        for (int i = 0; i < entitiesCount; i++) {
            GameEntity entity;
            
            // Nom de l'entité
            String entityName = data.readUTF();
            
            Logger.getGlobal().severe("GETTING ENTITY: " + entityName);
            
            // Type de l'entité
            int entityType = data.readInt();
            
            // Nom de la tile d'appartenance
            String belongTileName = data.readUTF();
            
            // Si PlayerToken
            if (entityType == GameEntity.TYPE_PLAYERTOKEN) {
                // Numéro du joueur d'appartenance
                int belongPlayer = data.readInt();
                
                // Etat
                int state = data.readInt();
                
                // Nombre de points
                int points = data.readInt();
                
                PlayerToken pt = new PlayerToken(entityName, players[belongPlayer - 1], points);
                pt.setState(state);
                
                // A un bateau?
                boolean hasBoat = data.readBoolean();
                if (hasBoat) {
                    String boatName = data.readUTF();
                    Boat boat = (Boat) logic.getBoard().getEntity(boatName);
                    Logger.getGlobal().severe("THIS GUY HAZ A BOAT: " + boatName);
                    if (boat != null) {
                        pt.setBoat(boat);
                        boat.addPlayer(pt);
                    } else {
                        PlayerBoatAffectation pba = new PlayerBoatAffectation(pt, boatName);
                        playerBoatAffectations.add(pba);
                    }
                }
                
                // On le rassigne comme token du player
                players[belongPlayer - 1].getTokens().add(pt);
                
                entity = pt;
            } else if (entityType == GameEntity.TYPE_BOAT) {
                entity = new Boat(entityName);
            } else if (entityType == GameEntity.TYPE_SEASERPENT) {
                entity = new SeaSerpent(entityName);
            } else if (entityType == GameEntity.TYPE_SHARK) {
                entity = new Shark(entityName);
            } else if (entityType == GameEntity.TYPE_WHALE) {
                entity = new Whale(entityName);
            } else {
                throw new IllegalStateException("Unknown entityType read: " + entityType);
            }
            
            logic.getBoard().putEntity(entity);
            restoredEntities.add(entity);
        }
        
        // On restaure les bateaux qui manquaient à la première itération
        for (PlayerBoatAffectation pba : playerBoatAffectations) {
            Boat boat = (Boat) logic.getBoard().getEntity(pba.boatName);
            if (boat != null) {
                pba.player.setBoat(boat);
            } else {
                Logger.getGlobal().severe("We still haven't found your boat when restoring: " + pba.boatName);
            }
        }
        
        // Nombre de tiles dans le board
        int boardTilesCount = data.readInt();
        Logger.getGlobal().severe("We have this amount of tiles: " + boardTilesCount);
        
        // On créé les tiles juste avec leur nom pour remplir le board et avoir les objets
        List<GameTile> tilesToDeserialize = new ArrayList<GameTile>();
        for (int i = 0; i < boardTilesCount; i++) {
            String tileName = data.readUTF();
            int tileType = data.readInt();
            GameTile tile;
            
            switch (tileType) {
                case GameTile.TILE_BORDER:
                    tile = new BorderTile(logic.getBoard(), tileName);
                    break;
                case GameTile.TILE_BEACH:
                    tile = new BeachTile(logic.getBoard(), tileName);
                    break;
                case GameTile.TILE_FOREST:
                    tile = new ForestTile(logic.getBoard(), tileName);
                    break;
                case GameTile.TILE_MOUNTAIN:
                    tile = new MountainTile(logic.getBoard(), tileName);
                    break;
                case GameTile.TILE_WATER:
                    tile = new WaterTile(logic.getBoard(), tileName);
                    break;
                default:
                    throw new IllegalStateException("Unknown tile type: " + tileType);
            }
            
            logic.getBoard().forcePutTile(tile);
            tilesToDeserialize.add(tile);
        }
        
        // Ensuite on déserialize
        for (GameTile tile : tilesToDeserialize) {
            tile.readSerialized(data);
        }
        
        // On déserialize ensuite le tour en cours
        GameTurn currentTurn = new GameTurn(logic, players[currentPlayerNumber - 1]);
        currentTurn.readSerialized(data);
        
        // On demande au jeu 3D de faire le rendu du board
        if (logic instanceof Game3DLogic) {
            Game3DLogic logic3d = (Game3DLogic) logic;
            logic3d.getRenderer().getBoardRenderer().renderBoard(logic.getBoard());
            
            Logger.getGlobal().severe("Number of 3D entities to restore: " + restoredEntities.size());
            for (GameEntity ent : restoredEntities) {
                logic3d.getRenderer().getEntitiesRenderer().addEntity(ent);
            }
        }
        
        // Deserialisation des infos essentielles de GameLogic
        logic.deserializeData(data);
        
        // TODO: Relancer le dernier picking (serialiser le
        // TilePickRequest/EntityPickRequest/mLastPickedEntity?)
        logic.restoreTurn(currentTurn);
    }
    
}
