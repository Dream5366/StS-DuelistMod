package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

// 

public class OrbHealerPower extends AbstractPower
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("OrbHealPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.ORB_HEAL_POWER);
    public boolean finished = false;
    
    public OrbHealerPower(final AbstractCreature owner, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.amount = newAmount;
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	
    }
    
    @Override
    public void onEvokeOrb(AbstractOrb orb) 
    {
    	DuelistCard.heal(AbstractDungeon.player, this.amount);
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	
	}
    

    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
