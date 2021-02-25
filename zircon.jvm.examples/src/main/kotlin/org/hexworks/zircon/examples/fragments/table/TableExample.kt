package org.hexworks.zircon.examples.fragments.table

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.screen.Screen

/**
 * This example shows the usage of the table fragment.
 */
object TableExample {

    private val theme = ColorThemes.zenburnVanilla()

    @JvmStatic
    fun main(args: Array<String>) {
        val tableFragment: Table<Person> = buildTable()

//        val selectionPanel = buildPanel(tableFragment)

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withSize(tableFragment.size)
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .withTitle("Table example")
                .build()
        )

        val screen = Screen.create(tileGrid)

        screen.addFragment(tableFragment)
        screen.theme = theme
        screen.display()
    }

    private fun buildTable(): Table<Person> =
        Fragments
            .table(50.randomPersons())
            .withHeight(20)
            .withColumnSpacing(1)
            .withRowSpacing(0)
            .withColumns(
                Columns
                    .textColumn("First name", 14, Person::firstName),
                Columns
                    .textColumn("Last name", 14, Person::lastName),
                Columns
                    .textColumn("Age", 3, Person::age),
                Columns
                    .icon("Gender", Person::gender) {gender -> iconFor(gender)},
                Columns
                    .textColumnFormatted("Wage", 8, "%,d $", Person::wage)
            )
            .build()

    private fun iconFor(gender: Gender): Icon =
            Components
                    .icon()
                    .withIcon(gender.icon)
                    .withColorTheme(theme)
                    .build()

    private val Gender.icon
        get() = TileBuilder
                .newBuilder()
                .withForegroundColor(when (this) {
                    Gender.MALE -> ANSITileColor.BLUE
                    Gender.FEMALE -> ANSITileColor.RED
                })
                .withBackgroundColor(ANSITileColor.WHITE)
                .withCharacter(
                        when (this) {
                            Gender.MALE -> Symbols.MALE
                            Gender.FEMALE -> Symbols.FEMALE
                        }
                )
                .buildCharacterTile()
}