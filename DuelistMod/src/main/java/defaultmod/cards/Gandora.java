package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class Gandora extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("Gandora");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.GANDORA);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final AttackEffect AFX = AttackEffect.FIRE;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public Gandora() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.DRAGON);
        this.tags.add(DefaultMod.GOOD_TRIB);
        this.tags.add(DefaultMod.FULL);
        this.originalName = this.name;
        this.baseDamage = this.damage = 50;
        this.tributes = 5;
        this.baseMagicNumber = this.magicNumber = 30;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Tribute
    	tribute(p, this.tributes, false, this);
    	
    	// Deal half your HP damage to yourself
    	//damageSelf(player().currentHealth/2);

    	// Record hand size
    	int handSize = AbstractDungeon.player.hand.size() - 1;
    	if (handSize < 0) { handSize = 0; }
    	
    	// Exhaust all cards
    	AbstractDungeon.actionManager.addToTop(new ExhaustAction(p, p, handSize, true));
    	
    	// Deal 50 damage + 30 for every card exhausted
    	attack(m, AFX, this.damage + (this.magicNumber * handSize));

    	// Add 5 random 0 cost, upgraded dragons to draw pile
    	for (int i = 0; i < 5; i++)
    	{
	    	AbstractCard randomDragon = (DuelistCard) returnTrulyRandomFromSet(DefaultMod.DRAGON);
	    	randomDragon.upgrade();
	    	randomDragon.updateCost(0);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(randomDragon, 1, false));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Gandora();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.tributes = 4;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    // If player doesn't have enough summons, can't play card
   	@Override
   	public boolean canUse(AbstractPlayer p, AbstractMonster m)
   	{
   		// Check super canUse()
   		boolean canUse = super.canUse(p, m); 
   		if (!canUse) { return false; }
   		
   		// Pumpking & Princess
   		else if (this.misc == 52) { return true; }
   		
  		// Mausoleum check
    	else if (p.hasPower(EmperorPower.POWER_ID))
		{
			EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
			if (!empInstance.flag)
			{
				return true;
			}
		}

   		// Check for # of summons >= tributes
   		else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } } }

   		// Player doesn't have something required at this point
   		this.cantUseMessage = "Not enough Summons";
   		return false;
   	}

	@Override
	public void onTribute(DuelistCard tributingCard) {
		if (tributingCard.hasTag(DefaultMod.DRAGON)) { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, 1)); }
		
	}


	@Override
	public void onSummon(int summons) {
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
}