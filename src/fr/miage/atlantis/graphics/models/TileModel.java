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
import com.jme3.math.Vector3f;
import java.util.Random;

/**
 * Modèle de la tile
 */
public class TileModel extends StaticModel implements AbstractTileModel {

    /**
     *
     */
    public final static String DATA_IS_TILE = "is_tile";
    /**
     *
     */
    public final static String DATA_TILE_NAME = "tile_name";
    private int mHeight;

    /**
     * Constructeur du modèle de la tile
     * @param tileName Nom de la tile
     * @param height hauteur de la tile
     * @param am AssetManager qui permet d'accéder aux assets
     */
    public TileModel(final String tileName, int height, AssetManager am) {
        super(am, getModelPathFromHeight(height),
                getTexturePathFromHeight(height),
                getNormalPathFromHeight(height));

        mHeight = height;

        // Les modèles de tiles hexagonaux sont décalées de l'origine, et du
        // coup ne collent pas avec les tiles empty. On les recentre manuellement.
        setLocalTranslation(24.0f, 0.0f, 20.9999f);
        setLocalScale(2.0f, 2.0f, 2.01f);

        getModel().setUserData(DATA_IS_TILE, true);
        getModel().setUserData(DATA_TILE_NAME, tileName);
    }

    /**
     * Recupère l'emplacement du centre de la tile
     * @return l'emplacement du centre de la tile
     */
    public Vector3f getTileTopCenter() {
        updateWorldBound();
        return getModelNode().getWorldBound().getCenter().add(0.0f, 6.0f * (mHeight + 1), 0.0f);
    }

    /**
     * Recupère l'emplacement du centre de la tile random
     * @return l'emplacement du centre de la tile random
     */
    public Vector3f getRandomizedTileTopCenter() {
        Random r = new Random();
        return getTileTopCenter().add(-10.0f + r.nextFloat() * 20.0f, 0f, -10.0f + r.nextFloat() * 20.0f);
    }

    /**
     * Recupère le modèle du chemin par rapport à la hauteur
     * @param height hauteur de la tile
     * @return 
     */
    private static String getModelPathFromHeight(int height) {
        String path = null;
        switch (height) {
            case 1:
                path = "Models/beach_tile.mesh.xml";
                break;

            case 2:
                path = "Models/jungle_tile.mesh.xml";
                break;

            case 3:
                path = "Models/rock_tile.mesh.xml";
                break;
        }
        return path;
    }

    /**
     * Recupère la texture de la tile
     * @param height hauteur de la tile
     * @return 
     */
    private static String getTexturePathFromHeight(int height) {
        String path = null;
        switch (height) {
            case 1:
                path = "Textures/sand.jpg";
                break;

            case 2:
                path = "Textures/grass.jpg";
                break;

            case 3:
                path = "Textures/rock.jpg";
                break;
        }
        return path;
    }

    /**
     * Recupère le chemin normal
     * @param height hauteur de la tile
     * @return 
     */
    private static String getNormalPathFromHeight(int height) {
        String path = null;
        switch (height) {
            case 1:
                path = "Textures/sand_normal.jpg";
                break;

            case 2:
                path = "Textures/grass_normal.jpg";
                break;

            case 3:
                path = "Textures/rock_normal.jpg";
                break;
        }
        return path;
    }
}
