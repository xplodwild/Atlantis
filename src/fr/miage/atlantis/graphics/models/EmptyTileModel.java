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
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import fr.miage.atlantis.graphics.ModelCache;
import java.util.Random;

/**
 *
 */
public class EmptyTileModel extends StaticModel implements AbstractTileModel {

    // Détermine si la tile pickée est juste un cocon autour de la tile
    public final static String DATA_IS_TILE_SHELL = "is_tile_shell";

    private final static String COLLISION_MESH_FILE_NAME = "Models/collision_tile.mesh.xml";

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
        Spatial model = ModelCache.getInstance().getModel(COLLISION_MESH_FILE_NAME);
        if (model == null) {
            model = assetManager.loadModel(COLLISION_MESH_FILE_NAME);
            ModelCache.getInstance().putModel(COLLISION_MESH_FILE_NAME, model);
        } else {
            model = model.clone(false);
        }

        model.setUserData(DATA_IS_TILE_SHELL, this);
        model.setQueueBucket(RenderQueue.Bucket.Sky);
        model.setLocalTranslation(24.0f, 0.0f, 20.9999f);
        model.setLocalScale(10.0f, 5.0f, 10.01f);

        // On attache le shell a la node, meme s'il est invisible
        attachChild(model);

        // Set tile name data
        Node meshNode = ((Node) ((Node) getModel()).getChild(0));
        meshNode.setName(tileName);
        meshNode.setUserData(TileModel.DATA_IS_TILE, true);
        meshNode.setUserData(TileModel.DATA_TILE_NAME, tileName);
    }

    public Vector3f getTileTopCenter() {
        updateWorldBound();
        return getWorldBound().getCenter().add(0, 4.0f, 0);
    }

    public Vector3f getRandomizedTileTopCenter() {
        Random r = new Random();
        return getTileTopCenter(); //.add(-30.0f + r.nextFloat() * 30.0f, 0f, -30.0f + r.nextFloat() * 30.0f);
    }
}
