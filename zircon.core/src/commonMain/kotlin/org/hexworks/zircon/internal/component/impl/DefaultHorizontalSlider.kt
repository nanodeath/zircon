package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.RangeSelect
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import kotlin.math.roundToInt

class DefaultHorizontalSlider(componentMetadata: ComponentMetadata,
                              renderingStrategy: ComponentRenderingStrategy<RangeSelect>,
                              minValue: Int,
                              maxValue: Int,
                              numberOfSteps: Int) : BaseSlider(

        componentMetadata = componentMetadata,
        renderingStrategy = renderingStrategy,
        minValue = minValue,
        maxValue = maxValue,
        numberOfSteps = numberOfSteps) {

    override fun getMousePosition(event: MouseEvent): Int {
        return event.position.minus(absolutePosition + contentOffset).x
    }

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            when (event.code) {
                KeyCode.RIGHT -> {
                    incrementValue()
                    Processed
                }
                KeyCode.LEFT -> {
                    decrementValue()
                    Processed
                }
                KeyCode.UP -> {
                    addToCurrentValue(valuePerStep.roundToInt())
                    Processed
                }
                KeyCode.DOWN -> {
                    subtractToCurrentValue(valuePerStep.roundToInt())
                    Processed
                }
                else -> Pass
            }.apply {
                render()
            }
        } else Pass
    }
}
