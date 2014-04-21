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
import java.util.HashMap;
import java.util.Map;

/**
 * Classe gérant les sons du jeu
 */
public class AudioManager {

    private static final AudioManager INSTANCE = new AudioManager();

    private AssetManager mAssetManager;
    private Node mRootNode;
    private AudioNode mMainMusicNode;
    private AudioNode mAmbienceNode;
    private Map<String, AudioNode> mKnownNodes;

    private AudioManager() {
        mKnownNodes = new HashMap<String, AudioNode>();
    }

    public static AudioManager getDefault() {
        return INSTANCE;
    }

    public void initialize(AssetManager am, Node rootNode) {
        mAssetManager = am;
        mRootNode = rootNode;
    }

    /**
     * Met à jour le gestionnaire audio à chaque frame.
     * Appelé automatiquement par Game3DRenderer
     * @param timeDelta Temps en secondes depuis le dernier appel
     */
    public void update(float timeDelta) {
        
    }

    /**
     * Lit ou arrête la lecture de la musique principale
     * @param playing true pour lire, false pour arrêter
     */
    public void setMainMusic(boolean playing) {
        if (mMainMusicNode == null) {
            mMainMusicNode = new AudioNode(mAssetManager, AudioConstants.Path.MAIN_MUSIC, true);
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
    
    /**
     * Lit ou arrête la lecture de l'ambiance audio "eau" de fond
     * @param playing true pour lire, false pour arrêter
     */
    public void setAmbience(boolean playing) {
        if (mAmbienceNode == null) {
            mAmbienceNode = new AudioNode(mAssetManager, AudioConstants.Path.AMBIENCE, true);
            mAmbienceNode.setPositional(false);
            mRootNode.attachChild(mAmbienceNode);
        }

        if (playing) {
            mAmbienceNode.setTimeOffset(0.0f);
            mAmbienceNode.play();
        } else {
            mAmbienceNode.pause();
        }
    }

    /**
     * Lis le song indiqué en chemin et renvoie une node
     * @param path Le chemin du fichier
     */
    public AudioNode playSound(final String path) {
        return playSound(path, false);
    }

    /**
     * Lis le song indiqué en chemin et renvoie une node
     * @param path Le chemin du fichier
     * @param loop true pour lire en boucle
     */
    public AudioNode playSound(final String path, final boolean loop) {
        AudioNode node = mKnownNodes.get(path);
        if (node == null) {
            node = new AudioNode(mAssetManager, path, false);
            node.setPositional(false);
            node.setLooping(loop);
            mRootNode.attachChild(node);
        }
        
        // node.setTimeOffset(0.0f);
        node.play();

        return node;
    }

    /**
     * Arrête la lecture de la node passée en paramètre.
     * Notez qu'une fois la lecture arrêtée, il est impossible de rappeler "play" sur l'objet
     * AudioNode puisqu'il est supprimé du noeud principal.
     * @param node Le noeud audio à arrêter (ce que renvoie playSound)
     */
    public void stopSound(final AudioNode node) {
        node.stop();
        mRootNode.detachChild(node);
    }

}
