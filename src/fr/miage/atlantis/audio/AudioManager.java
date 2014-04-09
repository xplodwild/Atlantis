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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package fr.miage.atlantis.audio;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

/**
 * Classe gérant les sons du jeu
 */
public class AudioManager {
    
    private static final AudioManager INSTANCE = new AudioManager();
    
    private AssetManager mAssetManager;
    private Node mRootNode;
    private AudioNode mMainMusicNode;
    
    private AudioManager() {
        
    }
    
    public static AudioManager getDefault() {
        return INSTANCE;
    }
    
    public void initialize(AssetManager am, Node rootNode) {
        mAssetManager = am;
        mRootNode = rootNode;
    }
    
    public void setMainMusic(boolean playing) {
        if (mMainMusicNode == null) {
            mMainMusicNode = new AudioNode(mAssetManager, "Audio/MainTheme.ogg", true);
            mMainMusicNode.setPositional(false);
            mRootNode.attachChild(mMainMusicNode);
        }
        
        if (playing) {
            mMainMusicNode.setTimeOffset(0.0f);
            mMainMusicNode.play();
        } else {
            mMainMusicNode.pause();
        }
    }
}
