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
import de.lessvoid.nifty.elements.MouseClickMethods;
import fr.miage.atlantis.graphics.models.TileModel;

/**
 *
 */
public class InputActionListener {

    private final static String INPUTMAP_MOUSE_HOVER = "mouse_hover";
    private final static String INPUTMAP_MOUSE_CLICK = "mouse_click";

    private InputManager mInputManager;
    private Game3DRenderer mRenderer;

    private class PickingResult {
        public final static int SOURCE_BOARD = 0;
        public final static int SOURCE_ENTITY = 1;

        public Geometry geometry;
        public int source;
    }

    private AnalogListener mMouseHoverListener = new AnalogListener() {

        private Geometry mPreviousGeometry = null;
        private Material mOriginalMaterial = null;

        public void onAnalog(String name, float value, float tpf) {
            // On remet à zéro l'élément précédemment highlighté, si on en a un
            if (mPreviousGeometry != null) {
                mPreviousGeometry.setMaterial(mOriginalMaterial);
            }

            // On cherche si on a un nouvel élément
            PickingResult result = performPicking();

            if (result != null) {
                // On a un résultat, on l'highlight
                Material mat = result.geometry.getMaterial();
                mOriginalMaterial = mat.clone();
                if (highlightMaterial(mat)) {
                    mPreviousGeometry = result.geometry;
                }
            }
        }
    };

    private ActionListener mMouseClickListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (isPressed) {
                PickingResult result = performPicking();

                if (result != null) {
                    if (result.source == PickingResult.SOURCE_BOARD) {
                        System.out.println(result.geometry.getParent().getUserData(TileModel.DATA_TILE_NAME));
                    }
                }
            }
        }
    };

    public InputActionListener(InputManager inputManager, Game3DRenderer renderer) {
        mInputManager = inputManager;
        mRenderer = renderer;

        // Picking 3D souris : écoute sur X et Y
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

    private PickingResult performPicking() {
        CollisionResults results = new CollisionResults();

        // On convertit la position de la souris en coordonnées projetées 3D
        Vector2f click2d = mInputManager.getCursorPosition();
        Vector3f click3d = mRenderer.getCamera().getWorldCoordinates(
                new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = mRenderer.getCamera().getWorldCoordinates(
                new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        Ray ray = new Ray(click3d, dir);

        // On teste d'abord si on touche une entité (elles sont toujours par dessus les tiles)
        mRenderer.getEntitiesRenderer().updateModelBound();
        mRenderer.getEntitiesRenderer().collideWith(ray, results);
        PickingResult output = processPickingOutput(results, PickingResult.SOURCE_ENTITY);

        if (output == null) {
            // Rien sur les entités. On teste si on touche un élément du plateau (tiles).
            mRenderer.getBoardRenderer().collideWith(ray, results);
            output = processPickingOutput(results, PickingResult.SOURCE_BOARD);
        }

        return output;
    }

    private PickingResult processPickingOutput(CollisionResults results, int source) {
        if (results.size() > 0) {
            // On a des résultats, on prend le plus proche
            CollisionResult result = results.getClosestCollision();

            PickingResult output = new PickingResult();
            output.geometry = result.getGeometry();
            output.source = source;

            return output;
        }

        return null;
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
