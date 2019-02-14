package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

// Passive no-effect power, just lets Toon Monsters check for playability

public class ToonKingdomPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DefaultMod.makeID("ToonKingdomPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.TOON_WORLD_POWER);
    public static int TOON_DMG = 6;
    
    public ToonKingdomPower(final AbstractCreature owner, final AbstractCreature source, int toonDmg) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        TOON_DMG = toonDmg;
        this.amount = TOON_DMG;
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount != TOON_DMG) { this.amount = TOON_DMG; }
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount != TOON_DMG) { this.amount = TOON_DMG; }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount != TOON_DMG) { this.amount = TOON_DMG; }
    	if (DuelistCard.isToon(c.name)) 
    	{ 
    		if (TOON_DMG > 0) { DuelistCard.damageSelf(TOON_DMG); }
    		if (TOON_DMG > 0) { TOON_DMG--; } 
    		AbstractCard randomToon = DuelistCard.randomToonMonster("random");
    		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(randomToon, 1, true, false));
    	}
    	
    	this.amount = TOON_DMG;
    	updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount != TOON_DMG) { this.amount = TOON_DMG; }
	}

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0] + TOON_DMG + DESCRIPTIONS[1];
    }
}
