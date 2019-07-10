package duelistmod.cards.curses;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.actions.unique.ExhaustSpecificCardSuperFastAction;
import duelistmod.interfaces.DuelistCard;

public class GravekeeperCurse extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GravekeeperCurse");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GravekeeperCurse.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;
    private static final int COST = -2;
    // /STAT DECLARATION/

    public GravekeeperCurse() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    		
    }
    
    @Override
    public void triggerWhenDrawn() 
    {
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
        	AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardSuperFastAction(c, AbstractDungeon.player.discardPile));
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GravekeeperCurse();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		// TODO Auto-generated method stub
		
	}
	
    // If player doesn't have enough summons, can't play card
  	@Override
  	public boolean canUse(AbstractPlayer p, AbstractMonster m)
  	{
  		return false;
  	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		
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