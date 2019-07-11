package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;



public class BlizzardDragonPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("BlizzardDragonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.BLIZZARD_DRAGON_POWER);
    
    public BlizzardDragonPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.updateDescription();
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) 
    {
    	if (isPlayer)
    	{
    		DuelistCard.removePower(this, this.owner);
    	}
    }    
  
    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0];
    }
}
