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

import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ModelCache {

    private Map<String, Spatial> mModels;
    private Map<String, Material> mMaterials;

    private ModelCache() {
        mModels = new HashMap<String, Spatial>();
        mMaterials = new HashMap<String, Material>();

        // Preload des modèles
        getModel("Models/Avatar_A.mesh.xml");
        getModel("Models/boat.mesh.xml");
        getModel("Models/serpentA.mesh.xml");
    }

    private final static ModelCache INSTANCE = new ModelCache();

    public static ModelCache getInstance() {
        return INSTANCE;
    }

    public final Spatial getModel(final String path) {
        return mModels.get(path);
    }

    public void putModel(final String path, final Spatial model) {
        mModels.put(path, model);
    }

    public Material getMaterial(final String key) {
        return mMaterials.get(key);
    }

    public void putMaterial(final String key, final Material mat) {
        mMaterials.put(key, mat);
    }

}
