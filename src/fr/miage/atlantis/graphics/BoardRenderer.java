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
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.graphics.models.AbstractTileModel;
import fr.miage.atlantis.graphics.models.EmptyTileModel;
import fr.miage.atlantis.graphics.models.StaticModel;
import fr.miage.atlantis.graphics.models.TileModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class BoardRenderer extends Node {

    public final static String DATA_TILE = "tile";
    public final static String DATA_TILE_X = "tile_x";
    public final static String DATA_TILE_Y = "tile_y";
    public final static String DATA_TILE_OFFSET = "tile_offset";
    private final static boolean DEBUG_ITERATION = false;
    private final static float TILE_WIDTH = 35.0f;
    private final static float TILE_HEIGHT = 39.9f;
    private final static float GRID_HEIGHT = 1.0f;
    private float mTileOffset = 0.0f;
    private AssetManager mAssetManager;
    private List<Node> mTiles;
    private Map<Node, GameTile> mNodeToGameTiles;
    private Map<GameTile, AbstractTileModel> mGameTileToModel;

    public BoardRenderer(AssetManager am) {
        mAssetManager = am;
        mTiles = new ArrayList<Node>();
        mNodeToGameTiles = new HashMap<Node, GameTile>();
        mGameTileToModel = new HashMap<GameTile, AbstractTileModel>();

        addIslands();
    }

    public void addIslands() {
        StaticModel islands = new StaticModel(mAssetManager,
                "Models/polymsh.mesh.xml", "Textures/sand.jpg", name);
        islands.scale(1.0f, 1.5f, 0.85f);
        islands.setLocalTranslation(TILE_WIDTH * -6.5f, 0, TILE_WIDTH * 4.8f);
        attachChild(islands);
    }

    public Node getTile(int i) {
        return mTiles.get(i);
    }

    public AbstractTileModel findTileModel(GameTile tile) {
        return mGameTileToModel.get(tile);
    }

    /**
     * Parcours le board passé en paramètre et crée toutes les nodes 3D en
     * fonction de l'état actuel du board.
     *
     * @param board Le board a rendre
     */
    public void renderBoard(final GameBoard board) {
        GameTile currentTile = board.getFirstTile();
        GameTile rowHeadTile = currentTile;

        if (currentTile == null) {
            throw new IllegalStateException("Impossible de rendre un board vide");
        }

        int x = 0, y = 0;
        while (true) {
            int initialX = x;

            // On ajoute la tile présente
            addTileToRender(currentTile, x, y);
            if (DEBUG_ITERATION) {
                System.out.println("Adding self tile " + x + "," + y + " (" + currentTile.getName() + ")");
            }

            // On ajoute toutes les tiles à droite
            while (currentTile.getRightTile() != null) {
                x++;
                currentTile = currentTile.getRightTile();
                addTileToRender(currentTile, x, y);
                if (DEBUG_ITERATION) {
                    System.out.println("Adding right row tile " + x + "," + y + " (" + currentTile.getName() + ")");
                }
            }

            // On ajoute toutes les tiles à gauche
            currentTile = rowHeadTile;
            x = initialX;

            while (currentTile.getLeftTile() != null) {
                x--;
                currentTile = currentTile.getLeftTile();
                addTileToRender(currentTile, x, y);
                if (DEBUG_ITERATION) {
                    System.out.println("Adding left row tile " + x + "," + y + " (" + currentTile.getName() + ")");
                }
            }

            // On cherche l'élément du dessous. On est déjà à gauche, donc
            // on continue à droite tant qu'on a rien en dessous.
            boolean belowFound = false;
            while (!belowFound && currentTile != null) {
                if (currentTile.getLeftBottomTile() != null) {
                    if (DEBUG_ITERATION) {
                        System.out.println("Going below left of " + currentTile.getName());
                    }
                    rowHeadTile = currentTile.getLeftBottomTile();
                    currentTile = rowHeadTile;
                    mTileOffset -= TILE_HEIGHT / 2.0f;

                    y++;
                    belowFound = true;
                } else if (currentTile.getRightBottomTile() != null) {
                    if (DEBUG_ITERATION) {
                        System.out.println("Going below right of " + currentTile.getName());
                    }
                    rowHeadTile = currentTile.getRightBottomTile();
                    currentTile = rowHeadTile;
                    mTileOffset += TILE_HEIGHT / 2.0f;

                    y++;
                    belowFound = true;
                } else {
                    currentTile = currentTile.getRightTile();
                    x++;
                }
            }

            if (!belowFound) {
                // Il n'y a plus rien en dessous, on s'arrête
                break;
            }
        }
    }

    /**
     * Ajoute une tile au rendu à l'endroit singulier spécifié. Les coordonnées
     * sont converties automatiquement en coordonnées de l'univers 3D
     *
     * @param tile La tile à ajouter
     * @param x L'emplacement X sur le GameBoard
     * @param y L'emplacement Y sur le GameBoard
     */
    public void addTileToRender(GameTile tile, int x, int y) {
        Node output;

        // Si c'est une tile au dessus de l'eau, on utilise un mesh avec la
        // texture qui va bien. Sinon, on fait un contour seulement.
        if (tile.getHeight() > 0) {
            output = new TileModel(tile.getName(), tile.getHeight(), mAssetManager);
        } else {
            // On détermine la couleur en fonction du type de tile
            ColorRGBA color = ColorRGBA.White;
            if (tile.getHeight() < 0) {
                color = ColorRGBA.Red;
            } else if (tile.getHeight() == 0) {
                WaterTile wt = (WaterTile) tile;
                color = wt.isLandingTile() ? ColorRGBA.White
                        : new ColorRGBA(51.0f / 255.0f, 181.0f / 255.0f, 229.0f / 255.0f, 1.0f);
                //color = wt.isBeginningWithSeaShark() ? ColorRGBA.Magenta : color;
            }

            output = new EmptyTileModel(tile.getName(), mAssetManager, color);
        }

        // On positionne la tile
        output.setLocalTranslation(output.getLocalTranslation().add(
                new Vector3f(y * -TILE_WIDTH,
                GRID_HEIGHT,
                x * TILE_HEIGHT + mTileOffset)));

        // On l'attache à cette Node
        output.setUserData(DATA_TILE, tile.getName());
        output.setUserData(DATA_TILE_X, x);
        output.setUserData(DATA_TILE_Y, y);
        output.setUserData(DATA_TILE_OFFSET, mTileOffset);
        mNodeToGameTiles.put(output, tile);
        mGameTileToModel.put(tile, (AbstractTileModel) output);

        // Les tiles border ont une hauteur inférieure à 0. On en a besoin pour situer les escape,
        // mais on ne veut pas les afficher, donc on ne les attache pas au graphe de scène.
        if (tile.getHeight() >= 0) {
            attachChild(output);
        } else {
            output.move(0, 10, 0);
        }

        mTiles.add(output);
    }

    /**
     * Remplace une tile existante au rendu. Cela est utile pour ne pas avoir à
     * recalculer la position x et y de la tile
     *
     * @param src La tile supprimée
     * @param dest La nouvelle tiel
     */
    public void replaceTile(GameTile src, GameTile dest) {
        Node srcNode = (Node) mGameTileToModel.get(src);

        if (srcNode == null) {
            throw new IllegalStateException("Impossible de trouver la tile source!");
        }

        // On supprime l'ancienne tile
        detachChild(srcNode);

        // On ajoute la nouvelle au même endroit
        int x = (Integer) srcNode.getUserData(DATA_TILE_X);
        int y = (Integer) srcNode.getUserData(DATA_TILE_Y);
        mTileOffset = (Float) srcNode.getUserData(DATA_TILE_OFFSET);

        addTileToRender(dest, x, y);
    }

    /**
     * Enlève tous les éléments en rendu du board
     */
    public void clearBoard() {
        Set<Node> nodes = mNodeToGameTiles.keySet();
        for (Node node : nodes) {
            detachChild(node);
        }

        mTileOffset = 0.0f;

        mTiles.clear();
        mNodeToGameTiles.clear();
        mGameTileToModel.clear();
    }
}