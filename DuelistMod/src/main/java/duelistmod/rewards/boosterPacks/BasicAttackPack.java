package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.rewards.BoosterPack;

public class BasicAttackPack extends BoosterPack
{

	public BasicAttackPack() {
		super("Basic Attack Pack", "CommonBooster");
		rarity = PackRarity.COMMON;
		textColor = Color.GRAY;
		obeyPackSize = true;
		allowBasics = true;
		onlyBasics = true;
		alwaysUpgrade = false;
		alwaysUpgradeAtk = false;
		alwaysUpgradeSkill = false;
		alwaysUpgradePower = false;
		alwaysUpgradeMon = false;
		alwaysUpgradeSpell = false;
		alwaysUpgradeTrap = false;
		checkRelics();
	}
	
	@Override
	public boolean canSpawn() { return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(CardType.ATTACK, 4, toRet, CardRarity.RARE));
		toRet.addAll(this.findAllCards(CardType.ATTACK, 1, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new BasicAttackPack();
	}


}
