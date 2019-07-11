package duelistmod.orbs;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.interfaces.*;

@SuppressWarnings("unused")
public class Gate extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("Gate");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxTimer = 1.0F; 
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	private static ArrayList<DuelistCard> guardians = new ArrayList<DuelistCard>();
	private int MANA = 1;
	private int evokeIndex = 0;
	private ArrayList<String> evokeDescriptions = new ArrayList<String>();
	
	public Gate()
	{
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Gate.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 2;
		this.basePassiveAmount = this.passiveAmount = 4;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		this.evokeIndex = ThreadLocalRandom.current().nextInt(0, 3);
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		DuelistCard thunder = new SangaThunder();
		DuelistCard earth = new SangaEarth();
		DuelistCard water = new SangaWater(); 
		guardians.add(thunder); 
		guardians.add(earth);
		guardians.add(water);
		checkFocus(false);
		updateEvokeValues();
	}

	@Override
	public void updateDescription()
	{
		applyFocus();
		updateEvokeValues();
		this.description = DESC[0] + this.passiveAmount + DESC[1] + (this.passiveAmount + 1) + DESC[2] + evokeDescriptions.get(evokeIndex);
	}
	
	public void updateEvokeValues()
	{
		int block = (this.passiveAmount + 1) * 2;
		int dmg = this.passiveAmount * 2;
		evokeDescriptions = new ArrayList<String>();
		evokeDescriptions.add(DESC[3]);
		evokeDescriptions.add(DESC[4] + block + DESC[5]);
		evokeDescriptions.add(DESC[6] + dmg + DESC[7]);
	}

	@Override
	public void onEvoke()
	{
		if (this.evokeAmount > 0)
		{
			switch (evokeIndex)
			{
				// Gain energy
				case 0:
					DuelistCard.gainEnergy(2);
					break;
				case 1:
					DuelistCard.staticBlock((this.passiveAmount + 1) * 2);
					break;
				case 2:
					AbstractDungeon.actionManager.addToTop(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount * 2, DamageInfo.DamageType.THORNS), this, true));
					break;
			}
		}
	}

	@Override
	public void onEndOfTurn()
	{
		evokeIndex = AbstractDungeon.cardRandomRng.random(0, 2);
		checkFocus(false);
		updateDescription();
	}
	
	@Override
	public void onStartOfTurn()
	{
		if (!hasNegativeFocus())
		{
			AbstractDungeon.actionManager.addToTop(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), this, true));
			DuelistCard.gainEnergy(this.MANA);
			DuelistCard.staticBlock(this.passiveAmount + 1);
		}
		else if (this.passiveAmount > 0)
		{
			AbstractDungeon.actionManager.addToTop(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), this, true));
			DuelistCard.staticBlock(this.passiveAmount + 1);
		}
		else if (this.passiveAmount + 1 > 0)
		{
			DuelistCard.staticBlock(this.passiveAmount + 1);
		}
		
	}

	/*
	private void triggerPassiveEffect(DamageInfo info, boolean hitAll)
	{
		float speedTime;

		if (!hitAll) 
		{
			AbstractCreature m = AbstractDungeon.getRandomMonster();
			if (m != null) 
			{
				speedTime = 0.2F / AbstractDungeon.player.orbs.size();
				if (Settings.FAST_MODE) 
				{
					speedTime = 0.0F;
				}
				AbstractDungeon.actionManager.addToBottom(new DamageAction(m, info, AbstractGameAction.AttackEffect.NONE, true));
				
			}
		} 
		else 
		{
	
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, 
			DamageInfo.createDamageMatrix(info.base, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
			
		}
	}
	*/

	@Override
	public void triggerEvokeAnimation()
	{

	}

	@Override
	public void updateAnimation()
	{
		super.updateAnimation();
	}

	@Override
	public void render(SpriteBatch sb)
	{
		sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
		sb.setBlendFunction(770, 1);
		sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
		sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale + 
		MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F, this.angle, 0, 0, 96, 96, false, false);
		sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.2F, this.scale + 
		MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, -this.angle, 0, 0, 96, 96, false, false);
		sb.setBlendFunction(770, 771);
		sb.setColor(this.c);
		sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle / 12.0F, 0, 0, 96, 96, false, false);
		//renderText(sb);
		this.hb.render(sb);
	}

	@Override
	public void playChannelSFX()
	{
		CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
	}
	
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		// Render evoke amount text
		//FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
		// Render passive amount text
		//FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET + 20.0F * Settings.scale, this.c, this.fontScale);
	}
		
	@Override
	public AbstractOrb makeCopy()
	{
		return new Gate();
	}

	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}


