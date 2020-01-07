package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.RangeSelect
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.TileGraphics

@Suppress("DuplicatedCode")
class HorizontalSliderRenderer : ComponentRenderer<RangeSelect> {
    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<RangeSelect>) {
        tileGraphics.applyStyle(context.currentStyle)
        val component = context.component

        val defaultStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DEFAULT)
        val invertedDefaultStyleSet = defaultStyleSet
                .withBackgroundColor(defaultStyleSet.foregroundColor)
                .withForegroundColor(defaultStyleSet.backgroundColor)
        val disabledStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DISABLED)

        val cursorPosition = context.component.currentStep

        (0..context.component.width).forEach { idx ->
            when {
                idx == cursorPosition -> tileGraphics.draw(Tile.createCharacterTile(Symbols.DOUBLE_LINE_VERTICAL, context.currentStyle), Position.create(idx, 0))
                idx < cursorPosition -> tileGraphics.draw(Tile.createCharacterTile(' ', invertedDefaultStyleSet), Position.create(idx, 0))
                else -> tileGraphics.draw(Tile.createCharacterTile(' ', disabledStyleSet), Position.create(idx, 0))
            }
        }
    }
}
