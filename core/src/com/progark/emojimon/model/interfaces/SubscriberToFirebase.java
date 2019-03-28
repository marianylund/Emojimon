package com.progark.emojimon.model.interfaces;

import com.progark.emojimon.model.fireBaseData.GameData;
import com.progark.emojimon.model.fireBaseData.LastTurnData;

public interface SubscriberToFirebase {
    void notifyOfNewLastTurn(String gameID, LastTurnData lastTurn);
    void notifyOfGameData(String gameID, GameData gameData);
}
