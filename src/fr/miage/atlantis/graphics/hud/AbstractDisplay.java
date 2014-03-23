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

package fr.miage.atlantis.graphics.hud;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.ui.Picture;

/**
 *
 */
public class AbstractDisplay extends Picture {
    private Material mMaterial;
    private AssetManager mAssetManager;
    private int mWidth;
    private int mHeight;

    public AbstractDisplay(final int width, final int height, final String name,
            final AssetManager assetManager) {
        super(name);
        mWidth = width;
        mHeight = height;
        mAssetManager = assetManager;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    /**
     * Définit l'image affichée
     * @param image Chemin vers l'image
     */
    protected void showImage(final String image) {
        this.setImage(mAssetManager, image, true);
        this.setWidth(mWidth);
        this.setHeight(mHeight);

        setupMaterialIfNeeded();
    }

    /**
     * Créée le material custom si nécessaire
     */
    private void setupMaterialIfNeeded() {
        if (mMaterial == null) {
            // On créé un material spécial afin de pouvoir changer la valeur alpha (transparence)
            // de l'image.
            mMaterial = getMaterial().clone();
            mMaterial.setColor("Color", new ColorRGBA(1,1,1,1));
            mMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            this.setMaterial(mMaterial);
        }
    }

    /**
     * Définit la transparence de l'image
     * @param alpha Transparence, où 1.0f est opaque, et 0.0f est transparent
     */
    public void setAlpha(float alpha) {
        setupMaterialIfNeeded();
        mMaterial.setColor("Color", new ColorRGBA(1,1,1,alpha));
    }

    /**
     * @return La valeur alpha actuelle de l'image (0 = transparent, 1 = opaque)
     */
    public float getAlpha() {
        setupMaterialIfNeeded();
        return ((ColorRGBA) mMaterial.getParam("Color").getValue()).getAlpha();
    }
}
