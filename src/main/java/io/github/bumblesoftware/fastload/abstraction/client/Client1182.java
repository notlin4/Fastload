package io.github.bumblesoftware.fastload.abstraction.client;

import io.github.bumblesoftware.fastload.abstraction.tool.AbstractClientCalls;
import io.github.bumblesoftware.fastload.abstraction.tool.RetrieveValueFunction;
import io.github.bumblesoftware.fastload.abstraction.tool.StoreValueFunction;
import io.github.bumblesoftware.fastload.client.sceen.BuildingTerrainScreen;
import io.github.bumblesoftware.fastload.config.screen.FLConfigScreen1182;
import io.github.bumblesoftware.fastload.config.screen.FLConfigScreenButtons;
import io.github.bumblesoftware.fastload.mixin.mixins.client.OptionAccess;
import io.github.bumblesoftware.fastload.mixin.mixins.client.ScreenAccess;
import io.github.bumblesoftware.fastload.util.MinMaxHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@SuppressWarnings("unchecked")
public class Client1182 implements AbstractClientCalls {

    @Override
    public String getVersion() {
        return "1.18.2";
    }

    @Override
    public MinecraftClient getClientInstance() {
        return MinecraftClient.getInstance();
    }

    @Override
    public ClientWorld getClientWorld() {
        return getClientInstance().world;
    }

    @Override
    public Screen newFastloadConfigScreen(final Screen parent) {
        return new FLConfigScreen1182(parent);
    }

    @Override
    public Screen newBuildingTerrainScreen() {
        return new BuildingTerrainScreen();
    }

    @Override
    public Text newTranslatableText(final String content) {
        return new TranslatableText(content);
    }

    @Override
    public Text newLiteralText(final String content) {
        return new LiteralText(content);
    }

    @Override
    public <T1 extends Element & Drawable> T1 addDrawableChild(
            final Screen screen,
            final T1 drawableElement
    ) {
        return ((ScreenAccess)screen).addDrawableChildProxy(drawableElement);
    }

    @Override
    public <T> FLConfigScreenButtons<T> newFLConfigScreenButtons() {
        return (FLConfigScreenButtons<T>) new FLConfigScreenButtons<Option>();
    }


    @Override
    public ButtonWidget getNewButton(
            final int x,
            final int y,
            final int width,
            final int height,
            final Text message,
            final ButtonWidget.PressAction onPress
    ) {
        return new ButtonWidget(x, y, width, height, message, onPress);
    }

    @Override
    public <T> T newCyclingButton(
            final String namespace,
            final String identifier,
            final RetrieveValueFunction retrieveValueFunction,
            final StoreValueFunction storeValueFunction
    ) {
        return (T) CyclingOption.create(
                namespace + identifier,
                new TranslatableText(namespace + identifier + ".tooltip"),
                gameOptions -> Boolean.parseBoolean(retrieveValueFunction.getValue(identifier)),
                (gameOptions, option, value) -> storeValueFunction.setValue(identifier, value.toString())
        );
    }

    @Override
    public <T> T newSlider(
            final String namespace,
            final String identifier,
            final RetrieveValueFunction retrieveValueFunction,
            final StoreValueFunction storeValueFunction,
            final MinMaxHolder minMaxValues,
            final int width
    ) {
        return (T) new DoubleOption(
                namespace + identifier,
                minMaxValues.min(),
                minMaxValues.max(),
                1.0F,
                gameOptions -> Double.parseDouble(retrieveValueFunction.getValue(identifier)),
                (gameOptions, aDouble) ->
                        storeValueFunction.setValue(identifier, Integer.toString(aDouble.intValue())),
                (gameOptions, option) -> {
                    double d = option.get(gameOptions);
                    if (d == minMaxValues.min()) {
                        return ((OptionAccess)option).getGenericLabelProxy(new TranslatableText(namespace + identifier +
                                ".min"));
                    } else {
                        return d == minMaxValues.max() ?
                                ((OptionAccess)option).getGenericLabelProxy(new TranslatableText(namespace + identifier +
                                        ".max")) :
                                ((OptionAccess)option).getGenericLabelProxy(new LiteralText(Integer.toString((int)d)));
                    }
                },
                minecraftClient -> minecraftClient.textRenderer.wrapLines(
                        StringVisitable.plain(new TranslatableText(namespace + identifier + ".tooltip").getString()),
                        200
                ));
    }


    @Override
    public void setScreen(final Screen screen) {
        getClientInstance().setScreen(screen);
    }
    @Override
    public void renderScreenBackgroundTexture(
            final Screen screen,
            final int offset,
            final MatrixStack matrices
    ) {
        screen.renderBackgroundTexture(0);
    }

    @Override
    public void drawCenteredText(
            final MatrixStack matrices,
            final TextRenderer textRenderer,
            final Text text,
            final int centerX,
            final int y,
            final int color
    ) {
        DrawableHelper.drawCenteredText(matrices, textRenderer, text, centerX, y, color);
    }

    @Override
    public void drawCenteredText(
            final MatrixStack matrices,
            final TextRenderer textRenderer,
            final String text,
            final int centerX,
            final int y,
            final int color
    ) {
        DrawableHelper.drawCenteredText(matrices, textRenderer, text, centerX, y, color);
    }

    @Override
    public int getLoadedChunkCount() {
        return getClientWorld().getChunkManager().getLoadedChunkCount();
    }

    @Override
    public int getCompletedChunkCount() {
        return getClientInstance().worldRenderer.getCompletedChunkCount();
    }

    @Override
    public boolean isWindowFocused() {
        return getClientInstance().isWindowFocused();
    }

    @Override
    public boolean isGameMenuScreen(final Screen screen) {
        return screen instanceof GameMenuScreen;
    }

    @Override
    public boolean isProgressScreen(final Screen screen) {
        return screen instanceof ProgressScreen;
    }

    @Override
    public boolean isDownloadingTerrainScreen(final Screen screen) {
        return screen instanceof DownloadingTerrainScreen;
    }
}
