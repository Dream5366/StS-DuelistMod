package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.stances.*;

public class BattleguardRageAction extends AbstractGameAction
{
    @Override
    public void update() {
        if (GameActionManager.playerHpLastTurn < AbstractDungeon.player.currentHealth) {
            this.addToBot(new ChangeStanceAction("Wrath"));
        }
        this.isDone = true;
    }
}
