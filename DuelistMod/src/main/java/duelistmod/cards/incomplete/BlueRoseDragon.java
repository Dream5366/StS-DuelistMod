package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.helpers.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class BlueRoseDragon extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("BlueRoseDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BlueRoseDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public BlueRoseDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.PLANT); 
        this.tags.add(Tags.DRAGON); 
        this.tags.add(Tags.ROSE); 
        this.summons = this.baseSummons = 1;
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();	
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BlueRoseDragon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSummons(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public void customOnTribute(DuelistCard tc)
    {
    	ArrayList<AbstractCard> plants = new ArrayList<>();
    	for (AbstractCard c : AbstractDungeon.player.discardPile.group)
    	{
    		if (c.hasTag(Tags.PLANT) && !c.hasTag(Tags.EXEMPT))
    		{
    			plants.add(c);
    		}
    	}
    	
    	if (plants.size() > 0)
    	{    		
    		AbstractMonster m = AbstractDungeon.getRandomMonster();
    		if (m != null) { AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(plants, 1, m, true, !this.upgraded)); }		 		
    	}
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
	}

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}
	
    // Checking for Monster Zones if the challenge is enabled
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }

    	if (Util.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeLevel20)
    	{
    		if ((DuelistMod.getChallengeDiffIndex() < 3) && this.misc == 52) { return true; }
    		if (p.hasPower(SummonPower.POWER_ID))
    		{
    			int sums = DuelistCard.getSummons(p); int max = DuelistCard.getMaxSummons(p);
    			if (sums + this.summons <= max) 
    			{ 
    				return true; 
    			}
    			else 
    			{ 
    				if (sums < max) 
    				{ 
    					if (max - sums > 1) { this.cantUseMessage = "You only have " + (max - sums) + " monster zones"; }
    					else { this.cantUseMessage = "You only have " + (max - sums) + " monster zone"; }
    					
    				}
    				else { this.cantUseMessage = "No monster zones remaining"; }
    				return false; 
    			}
    		}
    		else
    		{
    			return true;
    		}
    	}
    	
    	else
    	{
    		return true;
    	}
    }
	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}