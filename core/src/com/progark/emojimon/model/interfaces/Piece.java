package com.progark.emojimon.model.interfaces;

import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;

public interface Piece {

    Position getPosition();
    void setPosition(Position position);
    Player getOwner();
}
