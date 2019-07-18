package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.variables.Strings;

public class CardRewardRelicE extends CustomRelic
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("CardRewardRelicE");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
    // /FIELDS

    public CardRewardRelicE() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL); }
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
	public boolean canSpawn()
	{
		// Only spawn for non-Duelist characters
		if (DuelistMod.hasCardRewardRelic) { return false; }
		else { return true; }
	}
    
    @Override
    public void onEquip()
    {
    	DuelistMod.hasCardRewardRelic = true;
    }
    
    @Override
    public void onVictory() 
    {
    	int roll = AbstractDungeon.relicRng.random(1, 5);
    	if (roll == 1)
    	{
    		flash();
	    	for (int i = 0; i < 2; i++) { AbstractDungeon.getCurrRoom().addCardToRewards(); }  	
    	}
    }
}
