package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.variables.Tags;

public class AmuletDragon extends DuelistCard 
{
    // TEXT DECLARATION 

    public static final String ID = DuelistMod.makeID("AmuletDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AmuletDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION 	
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    private double dynamicBlock = 0;
    // /STAT DECLARATION/
    
    public AmuletDragon() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.MONSTER);
    	this.tags.add(Tags.DRAGON);
		this.originalName = this.name;
		this.magicNumber = this.baseMagicNumber = 3;
		this.tributes = this.baseTributes = 3;
		this.secondMagic = this.baseSecondMagic = 0;
		this.exhaust = true;
    }
    
	@Override
	public void update()
	{
		super.update();
		if (AbstractDungeon.player != null && AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT))
		{
			this.dynamicBlock = this.magicNumber * DuelistMod.tribCombatCount;
			this.secondMagic = this.baseSecondMagic = (int)this.dynamicBlock;
		}
	}

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	DuelistCard.applyPowerToSelf(new StrengthPower(p, DuelistMod.tribCombatCount * this.magicNumber));
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new AmuletDragon();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
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