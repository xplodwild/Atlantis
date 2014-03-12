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

package fr.miage.atlantis.graphics;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
import fr.miage.atlantis.graphics.models.AbstractTileModel;
import fr.miage.atlantis.graphics.models.AnimatedModel;
import fr.miage.atlantis.graphics.models.BoatModel;
import fr.miage.atlantis.graphics.models.PlayerModel;
import fr.miage.atlantis.graphics.models.SeaSerpentModel;
import fr.miage.atlantis.graphics.models.SharkModel;
import fr.miage.atlantis.graphics.models.WhaleModel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class EntitiesRenderer extends Node {

    private AssetManager mAssetManager;
    private BoardRenderer mBoardRenderer;

    private Map<GameEntity, Node> mEntityToNode;
    private Map<Node, GameEntity> mNodeToEntity;

    public EntitiesRenderer(AssetManager am, BoardRenderer board) {
        mAssetManager = am;
        mBoardRenderer = board;
        mEntityToNode = new HashMap<GameEntity, Node>();
        mNodeToEntity = new HashMap<Node, GameEntity>();
    }

    public AnimatedModel addEntity(GameEntity ent) {
        Node output = null;
        if (ent instanceof Boat) {
            output = addBoat((Boat) ent);
        } else if (ent instanceof PlayerToken) {
            output = addPlayer((PlayerToken) ent);
        } else if (ent instanceof SeaSerpent) {
            output = addSeaSerpent((SeaSerpent) ent);
        } else if (ent instanceof Shark) {
            output = addShark((Shark) ent);
        } else if (ent instanceof Whale) {
            output = addWhale((Whale) ent);
        } else {
            throw new UnsupportedOperationException("Unknown entity type: " + ent.toString());
        }

        mEntityToNode.put(ent, output);
        mNodeToEntity.put(output, ent);

        attachChild(output);

        if (ent.getTile() != null) {
            AbstractTileModel tile = mBoardRenderer.findTileModel(ent.getTile());
            output.setLocalTranslation(tile.getRandomizedTileTopCenter());
        }

        return (AnimatedModel) output;
    }

    public void removeEntity(GameEntity ent) {
        Node node = mEntityToNode.get(ent);
        detachChild(node);
        mEntityToNode.remove(ent);
        mNodeToEntity.remove(node);
    }

    public Node getNodeFromEntity(GameEntity ent) {
        return mEntityToNode.get(ent);
    }

    public GameEntity getEntityFromNode(Node node) {
        return mNodeToEntity.get(node);
    }

    private Node addBoat(Boat b) {
        BoatModel model = new BoatModel(mAssetManager);
        return model;
    }

    private Node addPlayer(PlayerToken p) {
        PlayerModel model = new PlayerModel(mAssetManager,
                PlayerModel.intToColor(p.getPlayer().getNumber()));
        return model;
    }

    private Node addSeaSerpent(SeaSerpent s) {
        SeaSerpentModel model = new SeaSerpentModel(mAssetManager);
        return model;
    }

    private Node addShark(Shark s) {
        SharkModel model = new SharkModel(mAssetManager);
        return model;
    }

    private Node addWhale(Whale w) {
        WhaleModel model = new WhaleModel(mAssetManager);
        return model;
    }

}
