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
 * Classe cache des modèles du jeux
 */
public class ModelCache {

    /**
     * Modèles spatiaux
     */
    private Map<String, Spatial> mModels;
    /**
     * Modeles entités et tuiles et autres éléments
     */
    private Map<String, Material> mMaterials;

    /**
     * Constructeur vide de ModelCahce
     */
    private ModelCache() {
        mModels = new HashMap<String, Spatial>();
        mMaterials = new HashMap<String, Material>();

        // Preload des modèles
        getModel("Models/Avatar_A.mesh.xml");
        getModel("Models/boat.mesh.xml");
        getModel("Models/serpentA.mesh.xml");
    }
    /**
     * Instace actuelle (design singleton)
     */
    private final static ModelCache INSTANCE = new ModelCache();

    /**
     * Renvoie l'instance actuelle
     *
     * @return l'instance actuelle
     */
    public static ModelCache getInstance() {
        return INSTANCE;
    }

    /**
     * Renvoie le modèle selon le chemin indiqué
     *
     * @param path chemin de modèle
     * @return modèle spatial
     */
    public final Spatial getModel(final String path) {
        return mModels.get(path);
    }

    /**
     * Place un modèle à un chemin donnée
     *
     * @param path chemin donnée
     * @param model model à placer
     */
    public void putModel(final String path, final Spatial model) {
        mModels.put(path, model);
    }

    /**
     * Récupère un materiel en fonction de sa clé
     *
     * @param key la clé
     * @return le matérial
     */
    public Material getMaterial(final String key) {
        return mMaterials.get(key);
    }

    /**
     * Placé un matérial avec sa clé
     *
     * @param key clé
     * @param mat material à placer
     */
    public void putMaterial(final String key, final Material mat) {
        mMaterials.put(key, mat);
    }
}
