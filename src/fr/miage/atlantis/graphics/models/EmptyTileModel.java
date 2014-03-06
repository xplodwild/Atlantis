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

package fr.miage.atlantis.graphics.models;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import fr.miage.atlantis.graphics.ModelCache;

/**
 *
 */
public class EmptyTileModel extends StaticModel implements AbstractTileModel {

    // Détermine si la tile pickée est juste un cocon autour de la tile
    public final static String DATA_IS_TILE_SHELL = "is_tile_shell";

    public EmptyTileModel(final String tileName, AssetManager assetManager, ColorRGBA color) {
        super(assetManager, "Models/hexagon.blend", null, null);

        Material matGrid = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matGrid.setColor("Color", color);
        getModel().setMaterial(matGrid);

        // Les normals sont cheloues dans le model d'hexagone, donc on désactive le culling
        matGrid.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);

        getModel().setUserData(TileModel.DATA_TILE_NAME, tileName);
        getModel().setLocalScale(22.0f);

        // On utilise une tile pleine comme modèle de collision, car le picking de jME utilise un
        // cast au niveau polygone. Du coup, il ne prend pas le milieu des tiles vides, ce qui
        // est embêtant.
        // TODO: Le cache empêche le model de fonctionner pour une raison étrange
        Spatial model = null; //ModelCache.getInstance().getModel("Models/collision_tile.mesh.xml");
        if (model == null) {
            model = assetManager.loadModel("Models/collision_tile.mesh.xml");
            //ModelCache.getInstance().putModel("Models/collision_tile.mesh.xml", model);
        }
        model.setUserData(DATA_IS_TILE_SHELL, this);
        model.setQueueBucket(RenderQueue.Bucket.Sky);
        model.setLocalTranslation(24.0f, 0.0f, 20.9999f);
        model.setLocalScale(10.0f, 5.0f, 10.01f);
        model.setName("Collision Shell " + tileName);
        attachChild(model);
        getModel().setName("Model Spatial " + tileName);
        getModelNode().setName("Model Node " + tileName);
        setName("Root Node " + tileName);
    }

    public Vector3f getTileTopCenter() {
        updateWorldBound();
        return getWorldBound().getCenter().add(0, 4.0f, 0);
    }
}
