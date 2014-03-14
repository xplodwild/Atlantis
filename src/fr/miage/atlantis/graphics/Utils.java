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
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 */
public class Utils {

    /**
     * Génère un material transparent (invisible)
     * @param am AssetManager
     * @return Un material transparent
     */
    public static Material generateTransparentMaterial(AssetManager am) {
        Material transparent = new Material(am, "Common/MatDefs/Misc/Unshaded.j3md");
        transparent.setTransparent(true);
        transparent.setColor("Color", new ColorRGBA(1, 1, 1, 0.0f));
        transparent.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        return transparent;
    }

    /**
     * Génère un cube avec un material invisible (pour les volumes de collision par exemple)
     * @param am AssetManager
     * @param box Box représentant le cube
     * @return Geometry cube
     */
    public static Geometry generateInvisibleBox(AssetManager am, Box box) {
        Geometry geom = new Geometry("Custom_Col_Box", box);
        geom.setQueueBucket(RenderQueue.Bucket.Sky);
        geom.setMaterial(generateTransparentMaterial(am));
        geom.setShadowMode(RenderQueue.ShadowMode.Off);
        return geom;
    }
}
