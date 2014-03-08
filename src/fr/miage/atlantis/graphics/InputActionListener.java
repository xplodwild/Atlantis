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

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.MatParam;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
import fr.miage.atlantis.graphics.models.EmptyTileModel;
import fr.miage.atlantis.graphics.models.TileModel;
import fr.miage.atlantis.logic.GameLogic;

/**
 *
 */
public class InputActionListener {

    private final static int REQUEST_NONE = -1;
    public final static int REQUEST_ENTITY_PICK = 0;
    public final static int REQUEST_TILE_PICK = 1;
    private final static int REQUEST_MAX = 2;

    private final static String INPUTMAP_MOUSE_HOVER = "mouse_hover";
    private final static String INPUTMAP_MOUSE_CLICK = "mouse_click";

    private InputManager mInputManager;
    private Game3DRenderer mRenderer;
    private PickingResult mPickingResult;
    private int mPickingRequest;
    private GameLogic.EntityPickRequest mEntityRequest;
    private GameLogic.TilePickRequest mTileRequest;

    private class PickingResult {
        public final static int SOURCE_BOARD = 0;
        public final static int SOURCE_ENTITY = 1;

        public Geometry geometry;
        public int source;
    }

    private AnalogListener mMouseHoverListener = new AnalogListener() {

        private Spatial mPreviousGeometry = null;
        private Material mOriginalMaterial = null;

        public void onAnalog(String name, float value, float tpf) {
            // On remet à zéro l'élément précédemment highlighté, si on en a un
            if (mPreviousGeometry != null) {
                mPreviousGeometry.setMaterial(mOriginalMaterial);
            }

            if (mPickingRequest != REQUEST_NONE) {
                // On cherche si on a un nouvel élément
                PickingResult result = performPicking();

                if (result != null && result.geometry != null) {
                    // On a un résultat et qu'il correspond aux contraintes, on l'highlight
                    if (mPickingRequest == REQUEST_ENTITY_PICK) {
                        // On retrouve l'entité et on la passe.
                        GameEntity ent = getEntityFromNode(result.geometry);

                        if (checkPickingConstraints(mEntityRequest, ent)) {
                            Material mat = result.geometry.getMaterial();
                            mOriginalMaterial = mat.clone();
                            if (highlightMaterial(mat)) {
                                mPreviousGeometry = result.geometry;
                            }
                        }
                    } else if (mPickingRequest == REQUEST_TILE_PICK) {
                        // On retrouve la tile et on la passe.
                        GameTile tile = getTileFromNode(result.geometry);

                        if (tile != null && checkPickingConstraints(mTileRequest, tile)) {
                            Material mat = result.geometry.getMaterial();
                            mOriginalMaterial = mat.clone();
                            if (highlightMaterial(mat)) {
                                mPreviousGeometry = result.geometry;
                            }
                        }
                    }
                }
            }
        }
    };

    private ActionListener mMouseClickListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            // Si on clique et qu'on a effectivement une requête de picking
            if (isPressed && mPickingRequest != REQUEST_NONE) {
                // On effectue le picking
                PickingResult result = performPicking();


                if (result != null) {
                    // On a complété la requête. On laisse la place pour des requêtes de picking
                    // qui peuvent avoir lieu dans la logique ci dessous
                    int request = mPickingRequest;
                    mPickingRequest = REQUEST_NONE;

                    if (request == REQUEST_ENTITY_PICK) {
                        // On retrouve l'entité et on la passe.
                        // Hierarchie: submesh0.getParent(mesh).getParent(modelNode).getParent(node)
                        GameEntity ent = getEntityFromNode(result.geometry);

                        if (checkPickingConstraints(mEntityRequest, ent)) {
                            mRenderer.getLogic().onEntityPicked(ent);
                        } else {
                            // On relance la requête
                            mPickingRequest = request;
                        }
                    } else if (request == REQUEST_TILE_PICK) {
                        // On retrouve la tile et on la passe
                        GameTile tile = getTileFromNode(result.geometry);

                        if (checkPickingConstraints(mTileRequest, tile)) {
                            mRenderer.getLogic().onTilePicked(tile);
                        } else {
                            // On relance la requête
                            mPickingRequest = request;
                        }
                    }

                }
            }
        }
    };

    public InputActionListener(InputManager inputManager, Game3DRenderer renderer) {
        mInputManager = inputManager;
        mRenderer = renderer;
        mPickingResult = new PickingResult();
        mPickingRequest = REQUEST_NONE;

        // Picking 3D souris : écoute sur X et Y positif et négatif
        inputManager.addMapping(INPUTMAP_MOUSE_HOVER,
                new MouseAxisTrigger(MouseInput.AXIS_X, true),
                new MouseAxisTrigger(MouseInput.AXIS_X, false),
                new MouseAxisTrigger(MouseInput.AXIS_Y, true),
                new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addListener(mMouseHoverListener, INPUTMAP_MOUSE_HOVER);

        // Picking au clic
        inputManager.addMapping(INPUTMAP_MOUSE_CLICK,
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(mMouseClickListener, INPUTMAP_MOUSE_CLICK);
    }

    public void requestEntityPicking(GameLogic.EntityPickRequest request) {
        requestPicking(REQUEST_ENTITY_PICK);
        mEntityRequest = request;
    }

    public void requestTilePicking(GameLogic.TilePickRequest request) {
        requestPicking(REQUEST_TILE_PICK);
        mTileRequest = request;
    }

    /**
     * Demande à ce listener d'effectuer un picking en particulier. Le résultat sera rapporté
     * au GameLogic correspondant.
     * @param request L'une des constantes REQUEST_** de cette classe
     */
    private void requestPicking(int request) {
        if (request < 0 || request >= REQUEST_MAX) {
            throw new IllegalArgumentException("Request must be one of InputActionListener.REQUEST_**");
        }

        // En théorie quand on request un picking, on est en état "NONE", c'est-à-dire qu'aucune
        // autre requête n'est en cours. Si on passe d'une requête à une autre, on a peut être
        // un événement d'attente manquant.
        if (mPickingRequest != REQUEST_NONE) {
            System.out.println("WARN: Previous picking request wasn't complete! An event might be missing!");
        }

        mPickingRequest = request;
    }

    private PickingResult performPicking() {
        CollisionResults results = new CollisionResults();

        // On convertit la position de la souris en coordonnées projetées 3D
        Vector2f click2d = mInputManager.getCursorPosition();
        Vector3f click3d = mRenderer.getCamera().getWorldCoordinates(
                new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = mRenderer.getCamera().getWorldCoordinates(
                new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        Ray ray = new Ray(click3d, dir);

        PickingResult output = null;
        if (mPickingRequest == REQUEST_ENTITY_PICK) {
            // On teste si on touche une entité
            mRenderer.getEntitiesRenderer().updateModelBound();
            mRenderer.getEntitiesRenderer().collideWith(ray, results);

            output = processPickingOutput(results, PickingResult.SOURCE_ENTITY);
        } else if (mPickingRequest == REQUEST_TILE_PICK) {
            // On teste si on touche un élément du plateau (tiles).
            mRenderer.getBoardRenderer().collideWith(ray, results);

            output = processPickingOutput(results, PickingResult.SOURCE_BOARD);
        }

        return output;
    }

    private PickingResult processPickingOutput(CollisionResults results, int source) {
        if (results.size() > 0) {
            // On a des résultats, on prend le plus proche
            CollisionResult result = results.getClosestCollision();

            PickingResult output = mPickingResult;

            // On vérifie si le noeud pické est un shell ou non (exemples les tiles vides, voir
            // EmptyTileModel)
            if (result.getGeometry().getParent().getUserDataKeys().contains(EmptyTileModel.DATA_IS_TILE_SHELL)) {
                EmptyTileModel etm = (EmptyTileModel) result.getGeometry().getParent().getUserData(EmptyTileModel.DATA_IS_TILE_SHELL);

                // Hack: On retrouve le vrai mesh au lieu du shell.
                // TODO: Exporter proprement de blender
                Spatial spatial = ((Node) ((Node) etm.getModel()).getChild(0)).getChild(0);
                output.geometry = ((Geometry) spatial);
            } else {
                output.geometry = result.getGeometry();
            }
            output.source = source;

            return output;
        }

        return null;
    }

    /**
     * Vérifie que l'entité pickée correspond aux contraintes de la requête de picking
     * @param request La requête
     * @param ent L'entité pickée
     * @return true si l'entité respecte au moins une condition, false sinon
     */
    private boolean checkPickingConstraints(GameLogic.EntityPickRequest request, GameEntity ent) {
        if ((request.pickingRestriction & GameLogic.EntityPickRequest.FLAG_PICK_PLAYER_ENTITIES) != 0) {
            // On veut picker un pion du joueur. On vérifie que l'entité est bien cela.
            // @TODO: Bateau!

            // On vérifie que c'est bien un pion
            if ((ent instanceof PlayerToken)) {
                PlayerToken pt = (PlayerToken) ent;

                // On vérifie que le pion appartient au joueur
                if (pt.getPlayer() == request.player) {
                    return true;
                }
            }
        }

        if ((request.pickingRestriction & GameLogic.EntityPickRequest.FLAG_PICK_SEASERPENT) != 0) {
            // On veut picker un kraken.
            if (ent instanceof SeaSerpent) {
                // RELEASE THE KRAKEN
                return true;
            }
        }

        if ((request.pickingRestriction & GameLogic.EntityPickRequest.FLAG_PICK_SHARK) != 0) {
            // On veut picker un shark
            if (ent instanceof Shark) {
                return true;
            }
        }

        if ((request.pickingRestriction & GameLogic.EntityPickRequest.FLAG_PICK_WHALE) != 0) {
            // On veut picker une baleine
            if (ent instanceof Whale) {
                return true;
            }
        }

        return false;
    }

    private boolean checkPickingConstraints(GameLogic.TilePickRequest request, GameTile tile) {
        if (request.requiredHeight >= 0 && tile.getHeight() != request.requiredHeight) {
            return false;
        }

        if (request.waterEdgeOnly) {
            boolean isAtEdge = false;

            if (tile.getLeftBottomTile() != null && tile.getLeftBottomTile().getHeight() == 0) {
                isAtEdge = true;
            } else if (tile.getLeftUpperTile() != null && tile.getLeftUpperTile().getHeight() == 0) {
                isAtEdge = true;
            } else if (tile.getLeftTile() != null && tile.getLeftTile().getHeight() == 0) {
                isAtEdge = true;
            } else if (tile.getRightTile() != null && tile.getRightTile().getHeight() == 0) {
                isAtEdge = true;
            } else if (tile.getRightUpperTile() != null && tile.getRightUpperTile().getHeight() == 0) {
                isAtEdge = true;
            } else if (tile.getRightBottomTile() != null && tile.getRightBottomTile().getHeight() == 0) {
                isAtEdge = true;
            }

            if (!isAtEdge) {
                return false;
            }
        }

        if (request.pickNearTile != null) {
            if (tile.getLeftBottomTile() != request.pickNearTile &&
                    tile.getLeftTile() != request.pickNearTile &&
                    tile.getLeftUpperTile() != request.pickNearTile &&
                    tile.getRightBottomTile() != request.pickNearTile &&
                    tile.getRightTile() != request.pickNearTile &&
                    tile.getRightUpperTile() != request.pickNearTile) {
                return false;
            }
        }

        return true;
    }

    /**
     * Retrouve l'entité a partir d'une géometrie d'entité pickée
     * @param geometry
     * @return
     */
    private GameEntity getEntityFromNode(Geometry geometry) {
        // Hierarchie: submesh0.getParent(mesh).getParent(modelNode).getParent(node)
        return mRenderer.getEntitiesRenderer().getEntityFromNode(
                geometry.getParent().getParent().getParent());
    }

    /**
     * Retrouve une GameTile à partir d'une géométrie de tile pickée
     * @param geometry
     * @return
     */
    private GameTile getTileFromNode(Geometry geometry) {
        String tileName = geometry.getParent().getUserData(TileModel.DATA_TILE_NAME);
        return mRenderer.getLogic().getBoard().getTileSet().get(tileName);
    }

    private boolean highlightMaterial(Material mat) {
        for (MatParam def : mat.getParams()) {
            if (def.getName().equals("Diffuse")) {
                mat.setColor("Diffuse", ColorRGBA.White);
                return true;
            } else if (def.getName().equals("Color")) {
                mat.setColor("Color", ColorRGBA.Magenta);
                return true;
            }
        }

        return false;
    }
}