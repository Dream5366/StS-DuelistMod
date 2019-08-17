package duelistmod.helpers;

import com.evacipated.cardcrawl.mod.hubris.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.patches.AbstractCardEnum;

public class HubrisHelper 
{

	public static void extraRelics()
	{
		BaseMod.addRelicToCustomPool(new IronBody(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new AncientText(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new RGBLights(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new BallOfYels(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SoftwareUpdate(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(IronBody.ID);
		UnlockTracker.markRelicAsSeen(AncientText.ID);
		UnlockTracker.markRelicAsSeen(RGBLights.ID);
		UnlockTracker.markRelicAsSeen(BallOfYels.ID);
		UnlockTracker.markRelicAsSeen(SoftwareUpdate.ID);
		
	}
}
