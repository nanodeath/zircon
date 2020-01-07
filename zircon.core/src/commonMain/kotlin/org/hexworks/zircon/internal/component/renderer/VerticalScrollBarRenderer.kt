package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.RangeSelect
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import kotlin.math.roundToInt

@Suppress("DuplicatedCode")
class VerticalScrollBarRenderer : ComponentRenderer<RangeSelect> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<RangeSelect>) {
        val component = context.component
        val defaultStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DEFAULT)
        val invertedDefaultStyleSet = defaultStyleSet
                .withBackgroundColor(defaultStyleSet.foregroundColor)
                .withForegroundColor(defaultStyleSet.backgroundColor)
        val disabledStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DISABLED)

        val barSizeInSteps = (component.rangeValue / ((component.maxValue - component.minValue).toDouble() / component.size.height.toDouble())).roundToInt()

        val lowBarPosition = context.component.currentStep
        val highBarPosition = lowBarPosition + barSizeInSteps - 1
        val totalScrollBarHeight = context.component.contentSize.height

        tileGraphics.applyStyle(context.currentStyle)

        (0..totalScrollBarHeight).forEach { idx ->
            when {
                idx < lowBarPosition -> tileGraphics.draw(Tile.createCharacterTile(' ', disabledStyleSet), Position.create(0, idx))
                idx > highBarPosition -> tileGraphics.draw(Tile.createCharacterTile(' ', disabledStyleSet), Position.create(0, idx))
                else -> tileGraphics.draw(Tile.createCharacterTile(' ', invertedDefaultStyleSet), Position.create(0, idx))
            }
        }
    }
}
