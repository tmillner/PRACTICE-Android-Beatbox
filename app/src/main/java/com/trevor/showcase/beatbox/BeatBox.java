package com.trevor.showcase.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.nfc.Tag;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by trevormillner on 1/13/18.
 * Source of authority for playing/ managing sounds
 */
public class BeatBox {
    
    private static final String TAG = BeatBox.class.getName();
    
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssetManager;

    private List<Sound> mSounds;
    private SoundPool mSoundPool;
    
    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        // Even though the soundpool constructor below is deprecated, need
        //  to use it for compatibility (Lowest SDK version). Can't use builder
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }
    
    private void loadSounds() {
        String[] sounds;
        
        try {
            // Stores the individual filename INSIDE the directory, not the path
            sounds = mAssetManager.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + sounds.length + " sounds");
        } catch (IOException e) {
            Log.e(TAG, "An error occured loading sounds!: ", e);
            return;
        }

        mSounds = new ArrayList<>();
        for (String filename : sounds) {
            try {
                Sound sound = new Sound( SOUNDS_FOLDER + "/" + filename);
                load(sound);
                mSounds.add(sound);
            } catch (IOException e) {
                Log.e(TAG, "An error occured loading sound!: ", e);
            }
        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssetManager.openFd(sound.getAssetPath());
        Integer soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId != null) {
            mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    public void release() {
        mSoundPool.release();
    }

    public List<Sound> getSounds() {
        return mSounds;
    }
    
}
