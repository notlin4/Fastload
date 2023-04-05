package io.github.bumblesoftware.fastload.mixin.mixins.local;

import io.github.bumblesoftware.fastload.abstraction.client.Client1193;
import io.github.bumblesoftware.fastload.abstraction.tool.AbstractClientCalls;
import io.github.bumblesoftware.fastload.config.init.FLMath;
import io.github.bumblesoftware.fastload.init.Fastload;
import io.github.bumblesoftware.fastload.init.FastloadClient;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = "1.19.3"))
@Mixin(FastloadClient.class)
public class Hook1193 {
    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "getAbstractedClient", at = @At("HEAD"), remap = false, cancellable = true)
    private static void compat1193(CallbackInfoReturnable<AbstractClientCalls> cir) {
        if (FLMath.isDebugEnabled())
            Fastload.LOGGER.info("compat1193");
        cir.setReturnValue(new Client1193());
    }
}
