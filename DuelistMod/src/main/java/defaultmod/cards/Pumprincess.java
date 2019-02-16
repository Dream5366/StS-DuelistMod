package defaultmod.cards;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class Pumprincess extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("Pumprincess");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.PUMPRINCESS);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public Pumprincess() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.NO_PUMPKIN);
        this.summons = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons);
    	ArrayList<AbstractCard> discards = player().discardPile.group;
    	ArrayList<AbstractCard> toChooseFrom = new ArrayList<AbstractCard>();
    	for (AbstractCard c : discards) { if (c.tags.contains(DefaultMod.MONSTER) && !c.tags.contains(DefaultMod.NO_PUMPKIN)) { toChooseFrom.add(c); } }
    	if (toChooseFrom.size() > 0)
    	{
	    	int randomAttack = ThreadLocalRandom.current().nextInt(0, toChooseFrom.size());
	    	AbstractCard chosen = toChooseFrom.get(randomAttack).makeStatEquivalentCopy();
	    	String cardName = chosen.originalName;
	    	System.out.println("theDuelist:Pumprincess --- > Found: " + cardName);
	    	DuelistCard cardCopy = newCopyOfMonsterPumpkin(cardName);
	    	if (cardCopy != null)
	    	{
		    	if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
		        cardCopy.freeToPlayOnce = true;
		        cardCopy.applyPowers();
		        cardCopy.purgeOnUse = true;
		        if (chosen.upgraded) { cardCopy.upgrade(); }
		        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
	    	}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Pumprincess();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}