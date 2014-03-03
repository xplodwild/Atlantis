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
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.material.MatParam;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 *
 */
public class InputActionListener {

    private final static String INPUTMAP_MOUSE_HOVER = "mouse_hover";

    private InputManager mInputManager;
    private Game3DRenderer mRenderer;

    private AnalogListener mMouseHoverListener = new AnalogListener() {

        private Geometry mPreviousGeometry = null;
        private Material mOriginalMaterial = null;

        public void onAnalog(String name, float value, float tpf) {
            CollisionResults results = new CollisionResults();

            // On convertit la position de la souris en coordonnées projetées 3D
            Vector2f click2d = mInputManager.getCursorPosition();
            Vector3f click3d = mRenderer.getCamera().getWorldCoordinates(
                    new Vector2f(click2d.x, click2d.y), 0f).clone();
            Vector3f dir = mRenderer.getCamera().getWorldCoordinates(
                    new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
            Ray ray = new Ray(click3d, dir);

            // On teste si on touche un élément du plateau (tiles)
            mRenderer.getBoardRenderer().collideWith(ray, results);

            // On remet à zéro l'élément précédemment highlighté, si on en a un
            if (mPreviousGeometry != null) {
                mPreviousGeometry.setMaterial(mOriginalMaterial);
            }

            if (results.size() > 0) {
                // On prend le point le plus proche, c'est celui qu'on a besoin
                CollisionResult closest = results.getClosestCollision();


                Material mat = closest.getGeometry().getMaterial();
                mOriginalMaterial = mat.clone();
                if (highlightMaterial(mat)) {
                    mPreviousGeometry = closest.getGeometry();
                }
            } else {
                // Rien de touché
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
