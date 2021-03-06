package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import duelistmod.abstracts.DuelistMonster;

public class KaibaResetHandAction extends AbstractGameAction 
{
	DuelistMonster enemy;
	public KaibaResetHandAction(DuelistMonster enemy) 
	{
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.DRAW;
		this.enemy = enemy;
	}
	
	@Override
	public void update() 
	{
		this.enemy.resetHand();
		this.isDone = true;
	}
	
}
