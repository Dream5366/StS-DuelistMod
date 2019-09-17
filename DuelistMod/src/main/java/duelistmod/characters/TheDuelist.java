package duelistmod.characters;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.helpers.*;
import duelistmod.orbs.Black;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.MillenniumPuzzle;
import duelistmod.variables.Colors;
import duelistmod.variables.Strings;


public class TheDuelist extends CustomPlayer {
	public static final Logger logger = LogManager.getLogger(DuelistMod.class.getName());

	// =============== BASE STATS =================
	public static final int ENERGY_PER_TURN = DuelistMod.energyPerTurn;
	public static final int STARTING_HP = DuelistMod.startHP;
	public static final int MAX_HP = DuelistMod.maxHP;
	public static final int STARTING_GOLD = DuelistMod.startGold;
	public static final int CARD_DRAW = DuelistMod.cardDraw;
	public static final int ORB_SLOTS = DuelistMod.orbSlots;
	public static final int numberOfArchetypes = 17;
	public static CardGroup theDuelistArchetypeSelectionCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
	public CardGroup resummonPile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
	private static final CharacterStrings charStrings;
	public static final String NAME;
	public static final String[] DESCRIPTIONS;
	// =============== /BASE STATS/ =================
	
	static 
	{
        charStrings = CardCrawlGame.languagePack.getCharacterString("Duelist");
        NAME = charStrings.NAMES[0];
        DESCRIPTIONS = charStrings.TEXT;
    }

	// =============== TEXTURES OF BIG ENERGY ORB ===============
	public static final String[] orbTextures = 
		{	
				"duelistModResources/images/char/defaultCharacter/orb/layer1.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer2.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer3.png",
				//"duelistModResources/images/char/defaultCharacter/orb/layer4.png",
				//"duelistModResources/images/char/defaultCharacter/orb/layer5.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer4B.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer5B.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer6.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer1d.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer2d.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer3d.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer4d.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer5d.png", 
		};
	// =============== /TEXTURES OF BIG ENERGY ORB/ ===============


	// =============== CHARACTER CLASS START =================

	public TheDuelist(String name, PlayerClass setClass) 
	{
		super(name, setClass, orbTextures,
				//"duelistModResources/images/char/defaultCharacter/orb/vfx.png", null,
				"duelistModResources/images/char/defaultCharacter/orb/vfxB.png", null,
				new SpriterAnimation(DuelistMod.characterModel));
						//"duelistModResources/images/char/duelistCharacterUpdate/YugiB.scml"));
						//"duelistModResources/images/char/duelistCharacter/theDuelistAnimation.scml"));
						//"duelistModResources/images/char/defaultCharacter/Spriter/theDefaultAnimation.scml"));

		// =============== TEXTURES, ENERGY, LOADOUT =================  

		initializeClass(null, // required call to load textures and setup energy/loadout
				DuelistMod.makePath(Strings.THE_DEFAULT_SHOULDER_1), // campfire pose
				DuelistMod.makePath(Strings.THE_DEFAULT_SHOULDER_2), // another campfire pose
				DuelistMod.makePath(Strings.THE_DEFAULT_CORPSE), // dead corpse
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

		// =============== /TEXTURES, ENERGY, LOADOUT/ =================


		// =============== ANIMATIONS =================  

		//this.loadAnimation(
		//		DefaultMod.makePath(DefaultMod.THE_DEFAULT_SKELETON_ATLAS),
		//		DefaultMod.makePath(DefaultMod.THE_DEFAULT_SKELETON_JSON),
		//		1.0f);
		//AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
		//e.setTime(e.getEndTime() * MathUtils.random());

		// =============== /ANIMATIONS/ =================


		// =============== TEXT BUBBLE LOCATION =================

		this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
		this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

		// =============== /TEXT BUBBLE LOCATION/ =================

	}

	// =============== /CHARACTER CLASS END/ =================

	
	@Override
	public void applyStartOfTurnPostDrawPowers()
	{
		super.applyStartOfTurnPostDrawPowers();
		if (this.hasOrb())
		{
			for (AbstractOrb o : this.orbs)
			{
				if (o instanceof Black)
				{
					Black b = (Black)o;
					if (b.passiveAmount > 0) { b.triggerPassiveEffect(); }
				}
			}
		}
	}
	
	@Override
	public void switchedStance() {
		super.switchedStance();
		for (AbstractOrb o : this.orbs) { if (o instanceof DuelistOrb) { ((DuelistOrb) o).onChangeStance(); }}
		for (AbstractRelic r : this.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onChangeStance(); }}
		for (AbstractPotion p : this.potions) { if (p instanceof DuelistPotion) { ((DuelistPotion)p).onChangeStance(); }}
	}

	// Starting description and loadout
	@Override
	public CharSelectInfo getLoadout() 
	{
		return new CharSelectInfo(NAME,DESCRIPTIONS[0],
				STARTING_HP, 
				MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
				getStartingDeck(), false);
	}

	// Starting Deck
	@Override
	public ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();

		logger.info("Begin loading starter Deck Strings");

		// The original standard deck from the earliest version of the mod
		// Deprecated, deck is filled in DuelistMod with PostCreateStartingDeckSubscriber
		retVal.add(SevenColoredFish.ID);
		retVal.add(SevenColoredFish.ID);
		retVal.add(GiantSoldier.ID);
		retVal.add(GiantSoldier.ID);
		retVal.add(PowerWall.ID);
		retVal.add(PowerWall.ID);
		retVal.add(ScrapFactory.ID);
		retVal.add(Ookazi.ID);
		retVal.add(Ookazi.ID);
		retVal.add(SummonedSkull.ID);

		return retVal;
	}

	
	// Card Pool Patch 
	@Override
	public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool)
	{
		tmpPool = super.getCardPool(tmpPool);
		//tmpPool = new ArrayList<AbstractCard>();
		if (DuelistMod.shouldFill)
		{ 
			PoolHelpers.newFillColored();
			BoosterPackHelper.setupPoolsForPacks();
			DuelistMod.shouldFill = false;
		}
		else { PoolHelpers.coloredCardsHadCards(); }
		for (AbstractCard c : DuelistMod.coloredCards)
		{
			if (!c.rarity.equals(CardRarity.SPECIAL))
			{
				tmpPool.add(c);
			}				
		}
		return tmpPool;
	}
	

	// Starting Relics	
	@Override
	public ArrayList<String> getStartingRelics() 
	{
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add(MillenniumPuzzle.ID);
		UnlockTracker.markRelicAsSeen(MillenniumPuzzle.ID);
		return retVal;
	}

	// Character Select screen effect
	@Override
	public void doCharSelectScreenSelectEffect() 
	{
		int roll = ThreadLocalRandom.current().nextInt(1, 4);
		if (roll == 1) 		{ CardCrawlGame.sound.playV("theDuelist:TimeToDuelB", 0.5F);	}
		else if (roll == 2) { CardCrawlGame.sound.playV("theDuelist:TimeToDuel", 0.5F); 	}
		else if (roll == 3) { CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); 			}
		else 				{ CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); 			}
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false); // Screen Effect
	}

	// Character Select on-button-press sound effect
	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "ATTACK_DAGGER_1";
	}

	// Should return how much HP your maximum HP reduces by when starting a run at
	// Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
	@Override
	public int getAscensionMaxHPLoss() 
	{
		if (DuelistMod.challengeMode) { return 15; }
		else { return 10; }
	}

	// Should return the card color enum to be associated with your character.
	@Override
	public AbstractCard.CardColor getCardColor() {
		return AbstractCardEnum.DUELIST;
	}

	// Should return a color object to be used to color the trail of moving cards
	@Override
	public Color getCardTrailColor() {
		return Colors.CARD_GRAY;
	}

	// Should return a BitmapFont object that you can use to customize how your
	// energy is displayed from within the energy orb.
	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontRed;
	}

	// Should return class name as it appears in run history screen.
	@Override
	public String getLocalizedCharacterName() 
	{
		return NAME;
	}

	//Which card should be obtainable from the Match and Keep event?
	@Override
	public AbstractCard getStartCardForEvent() {
		return new GiantSoldier();
	}

	// The class name as it appears next to your player name in-game
	@Override
	public String getTitle(AbstractPlayer.PlayerClass playerClass) {
		return DESCRIPTIONS[1];
	}

	// Should return a new instance of your character, sending this.name as its name parameter.
	@Override
	public AbstractPlayer newInstance() {
		return new TheDuelist(this.name, this.chosenClass);
	}

	// Should return a Color object to be used to color the miniature card images in run history.
	@Override
	public Color getCardRenderColor() {
		return Colors.CARD_PURPLE;
	}

	// Should return a Color object to be used as screen tint effect when your
	// character attacks the heart.
	@Override
	public Color getSlashAttackColor() {
		return Colors.CARD_PURPLE;
	}

	// Should return an AttackEffect array of any size greater than 0. These effects
	// will be played in sequence as your character's finishing combo on the heart.
	// Attack effects are the same as used in DamageAction and the like.
	@Override
	public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[] 
				{
						AbstractGameAction.AttackEffect.FIRE,
						AbstractGameAction.AttackEffect.BLUNT_HEAVY,
						AbstractGameAction.AttackEffect.POISON,
						AbstractGameAction.AttackEffect.BLUNT_LIGHT,
						AbstractGameAction.AttackEffect.FIRE,
						AbstractGameAction.AttackEffect.SLASH_HEAVY
				};
	}

	// Should return a string containing what text is shown when your character is
	// about to attack the heart. For example, the defect is "NL You charge your
	// core to its maximum..."
	@Override
	public String getSpireHeartText() {
		return DESCRIPTIONS[2];
	}

	// The vampire events refer to the base game characters as "brother", "sister",
	// and "broken one" respectively.This method should return a String containing
	// the full text that will be displayed as the first screen of the vampires event.
	@Override
	public String getVampireText() {
		return DESCRIPTIONS[3];
	}

	// Fills in the cut up image slideshow during the Heart victory animation sequence
	// I guess the first one plays a sound effect when the image appears on screen? 
	// Idk this is copied from someone, Slimebound perhaps
	@Override
	public List<CutscenePanel> getCutscenePanels() {
		List<CutscenePanel> panels = new ArrayList<CutscenePanel>();
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist1.png"), "ATTACK_HEAVY"));
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist2.png")));
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist3.png")));
		return panels;
	}

	// Used to load images in the character select screen
	public static Texture GetCharacterPortrait(int id)
	{
	    Texture result;
	    if (!DuelistMod.characterPortraits.containsKey(id))
	    {
	        result = new Texture(DuelistMod.makePath("charSelect/duelist_portrait_" + id + ".png"));
	        DuelistMod.characterPortraits.put(id, result);
	    }
	    else
	    {
	        result = DuelistMod.characterPortraits.get(id);
	    }
	
	    return result;
	}

	@Override
	public String getPortraitImageName() {
		// TODO Auto-generated method stub
		return null;
	}
}
