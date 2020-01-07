package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription


interface RangeSelect : Component {

    /**
     * Maximum value of the [RangeSelect]
     */
    var maxValue: Int
    /**
     * Minimum value of the [RangeSelect]
     */
    var minValue: Int

    /**
     * Distance between the low and high values
     */
    var rangeValue: Int

    /**
     * Current low value
     */
    var currentValue: Int

    /**
     * Bindable, current low value
     */
    val currentValueProperty: Property<Int>

    /**
     * Current low step
     */
    var currentStep: Int

    /**
     * Bindable, current low step
     */
    val currentStepProperty: Property<Int>

    fun incrementStep()
    fun decrementStep()
    fun incrementValue()
    fun decrementValue()

    /**
     * Function to specify a new maximum value
     */
    fun changeMaxValue(maxValue: Int)

    /**
     * Callback called when the low value changes
     */
    fun onValueChange(fn: (ChangeEvent<Int>) -> Unit): Subscription

    /**
     * Callback called when low step changes
     */
    fun onStepChange(fn: (ChangeEvent<Int>) -> Unit): Subscription
}
