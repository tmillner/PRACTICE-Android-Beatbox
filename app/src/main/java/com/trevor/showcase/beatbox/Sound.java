package com.trevor.showcase.beatbox;

/**
 * Created by trevormillner on 1/14/18.
 */

public class Sound {

    private String mAssetPath;
    private String mSoundName;

    private Integer mSoundId;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[ components.length - 1];
        mSoundName = filename.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getSoundName() {
        return mSoundName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
