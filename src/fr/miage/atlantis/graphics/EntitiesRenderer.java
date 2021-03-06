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
import java.util.Set;

/**
 * Rendu graphique des entités
 */
public class EntitiesRenderer extends Node {

    private AssetManager mAssetManager;
    private BoardRenderer mBoardRenderer;
    private Map<GameEntity, Node> mEntityToNode;
    private Map<Node, GameEntity> mNodeToEntity;

    /**
     * Constructeur de EntitiesRenderer
     *
     * @param am AssetManager qui permet d'accéder aux assets
     * @param board Renderer de la board
     */
    public EntitiesRenderer(AssetManager am, BoardRenderer board) {
        mAssetManager = am;
        mBoardRenderer = board;
        mEntityToNode = new HashMap<GameEntity, Node>();
        mNodeToEntity = new HashMap<Node, GameEntity>();
    }

    /**
     * Ajout d'une entité dans le renderer
     *
     * @param ent entité a ajouter
     * @return le modèle animé de l'entité
     */
    public AnimatedModel addEntity(GameEntity ent) {
        if (mEntityToNode.containsKey(ent)) {
            return null;
        }

        AnimatedModel output = null;
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

        output.playAnimation(AnimationBrain.getIdleAnimation(ent));
        return output;
    }

    /**
     * Supprime toutes les entitées présentes sur le plateau
     */
    public void clearEntities() {
        Set<Node> nodes = mNodeToEntity.keySet();
        for (Node node : nodes) {
            detachChild(node);
        }

        mEntityToNode.clear();
        mNodeToEntity.clear();
    }

    /**
     * Supprime du plateau l'entité indiquée
     *
     * @param ent L'entité à supprimer
     */
    public void removeEntity(GameEntity ent) {
        Node node = mEntityToNode.get(ent);
        detachChild(node);
        mEntityToNode.remove(ent);
        mNodeToEntity.remove(node);
    }

    /**
     * Retourne le noeud ou ets attaché l'entité
     *
     * @param ent entité en question
     * @return un noeud
     */
    public Node getNodeFromEntity(GameEntity ent) {
        return mEntityToNode.get(ent);
    }

    /**
     * Retourne l'entité placée sur un noeud
     *
     * @param node noeud concerné
     * @return entité sur le noeud
     */
    public GameEntity getEntityFromNode(Node node) {
        return mNodeToEntity.get(node);
    }

    /**
     * Renvoie le modèle animé d'un bateau
     *
     * @param b le bateau
     * @return le modele animé du bateau
     */
    private AnimatedModel addBoat(Boat b) {
        BoatModel model = new BoatModel(mAssetManager);
        return model;
    }

    /**
     * Renvoie le modèle animé d'un pion
     *
     * @param b le pion
     * @return le modele animé du pion
     */
    private AnimatedModel addPlayer(PlayerToken p) {
        PlayerModel model = new PlayerModel(mAssetManager,
                PlayerModel.intToColor(p.getPlayer().getNumber()));
        return model;
    }

    /**
     * Renvoie le modèle animé d'un serpent
     *
     * @param b le serpent
     * @return le modele animé du serpent
     */
    private AnimatedModel addSeaSerpent(SeaSerpent s) {
        SeaSerpentModel model = new SeaSerpentModel(mAssetManager);
        return model;
    }

    /**
     * Renvoie le modèle animé d'un requin bateau
     *
     * @param b le requin
     * @return le modele animé du requin
     */
    private AnimatedModel addShark(Shark s) {
        SharkModel model = new SharkModel(mAssetManager);
        return model;
    }

    /**
     * Renvoie le modèle animé de la baleine
     *
     * @param b la baleine
     * @return le modele animé de la baleine
     */
    private AnimatedModel addWhale(Whale w) {
        WhaleModel model = new WhaleModel(mAssetManager);
        return model;
    }
}
