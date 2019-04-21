
package duelistmod.patches;

import java.lang.reflect.*;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;

// Copied from Slimbound:
// Copied from The Mystic Mod:
//   	https://github.com/JohnnyDevo/The-Mystic-Project/blob/master/src/main/java/mysticmod/Patches/MysticMetricsPatch.java
//

/*
public class DuelistMetricsPatch {

	
		@SpirePatch(clz = Metrics.class, method = "sendPost", paramtypez = {String.class, String.class})
		public static class SendPostPatch {
	
			public static void Prefix(Metrics __instance, @ByRef String[] url, String fileName) {
				if (AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST) {
					url[0] = "http://softwaredev.site/MetricsSite";
				}
			}
	
		}
	
		@SpirePatch(clz = DeathScreen.class, method = "shouldUploadMetricData")
		public static class shouldUploadMetricData {
	
			public static boolean Postfix(boolean __returnValue) {
				if (AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST) {
					__returnValue = Settings.UPLOAD_DATA;
				}
				return __returnValue;
			}
	
		}
	
		@SpirePatch(clz = Metrics.class, method = "run")
		public static class RunPatch {
	
			public static void Postfix(Metrics __instance) {
				if (__instance.type == Metrics.MetricRequestType.UPLOAD_METRICS && AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST) {
					try {
						Method m = Metrics.class.getDeclaredMethod("gatherAllDataAndSend", boolean.class, boolean.class, MonsterGroup.class);
						m.setAccessible(true);
						m.invoke(__instance, __instance.death, __instance.trueVictory, __instance.monsters);
					} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
	
		}
	
}*/

