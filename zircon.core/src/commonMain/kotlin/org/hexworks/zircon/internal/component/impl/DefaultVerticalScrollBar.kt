package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.RangeSelect
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import kotlin.math.roundToInt

class DefaultVerticalScrollBar(componentMetadata: ComponentMetadata,
                               renderingStrategy: ComponentRenderingStrategy<RangeSelect>,
                               minValue: Int,
                               maxValue: Int,
                               itemsShownAtOnce: Int,
                               numberOfSteps: Int) : BaseScrollBar(

        componentMetadata = componentMetadata,
        renderingStrategy = renderingStrategy,
        minValue = minValue,
        maxValue = maxValue,
        rangeValue = itemsShownAtOnce,
        numberOfSteps = numberOfSteps) {

    override fun getMousePosition(event: MouseEvent): Int {
        return event.position.minus(absolutePosition + contentOffset).y
    }

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            when (event.code) {
                KeyCode.UP -> {
                    incrementValue()
                    Processed
                }
                KeyCode.DOWN -> {
                    decrementValue()
                    Processed
                }
                KeyCode.RIGHT -> {
                    addToCurrentValues(valuePerStep.roundToInt())
                    Processed
                }
                KeyCode.LEFT -> {
                    subtractToCurrentValues(valuePerStep.roundToInt())
                    Processed
                }
                else -> Pass
            }.apply {
                render()
            }
        } else Pass
    }
}
