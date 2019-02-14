package defaultmod.cards;

import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class ToonMask extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID("ToonMask");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DefaultMod.makePath(DefaultMod.TOON_MASK);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
	private static final int COST = 2;
	private static final int DAMAGE = 0;
	private static int MIN_DMG = 10;
	private static int MAX_DMG = 40;
	private static int MIN_DMG_U = 15;
	private static int MAX_DMG_U = 50;
	// /STAT DECLARATION/

	public ToonMask() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = DAMAGE;
		this.isMultiDamage = true;
		this.multiDamage = new int[]{0, 0, 0, 0, 0};
		this.magicNumber = this.baseMagicNumber = 20;
		this.toon = true;
		this.tags.add(DefaultMod.TOON);
		this.tags.add(DefaultMod.TRAP);
		this.misc = 0;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		int[] test = new int[5];
		for (int i = 0; i < 5; i++)
		{
			int randomNum = ThreadLocalRandom.current().nextInt(MIN_DMG, MAX_DMG + 1);
			int randomNumU = ThreadLocalRandom.current().nextInt(MIN_DMG_U, MAX_DMG_U + 1);
			if (upgraded) { test[i] = randomNum; }
			else { test[i] = randomNumU; }
		}
		this.multiDamage = test;
		AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new ToonMask();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	// If player doesn't have Toon World, can't be played
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Pumpking & Princess
  		if (this.misc == 52) { return true; }
  		
  		// Toon World
    	if (p.hasPower(ToonWorldPower.POWER_ID) || p.hasPower(ToonKingdomPower.POWER_ID)) { return true; }
    	
    	// Otherwise
    	this.cantUseMessage = "You need Toon World";
    	return false;
    }

}