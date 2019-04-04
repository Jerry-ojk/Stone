package com.stone.player;

public interface PlayerController {
    boolean isLandscape();

    boolean onPlay(boolean isPlay);

    boolean onDrag(long process);

    boolean onZoom();

    void onBack();

}
