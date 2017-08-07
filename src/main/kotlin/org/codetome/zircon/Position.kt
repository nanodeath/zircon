package org.codetome.zircon

/**
 * A 2D position in terminal space. Please note that the coordinates are 0-indexed, meaning 0x0 is the top left
 * corner of the terminal. This object is immutable so you cannot change it after it has been created. Instead, you
 * can easily create modified clones by using the `with*` methods.
 */
data class Position(val column: Int,
                    val row: Int) : Comparable<Position> {

    /**
     * Returns a new [Position] which is the sum of `column` and `row` in both [Position]s.
     * so `Position(column = 1, row = 1).plus(Position(column = 2, row = 2))` will be
     * `Position(column = 3, row = 3)`.
     */
    operator fun plus(position: Position): Position {
        return Position(
                column = column + position.column,
                row = row + position.row)
    }

    /**
     * Returns a new [Position] which is the difference between `column` and `row` in both [Position]s.
     * so `Position(column = 3, row = 3).minus(Position(column = 2, row = 2))` will be
     * `Position(column = 1, row = 1)`.
     */
    operator fun minus(position: Position): Position {
        return Position(
                column = column - position.column,
                row = row - position.row)
    }

    /**
     * Creates a new [Position] object representing a position with the same column index as this but with a
     * supplied row index.
     */
    fun withRow(row: Int): Position {
        if (row == 0 && this.column == 0) {
            return TOP_LEFT_CORNER
        }
        return copy(row = row)
    }

    /**
     * Creates a new [Position] object representing a position with the same row index as this but with a
     * supplied column index.
     */
    fun withColumn(column: Int): Position {
        if (column == 0 && this.row == 0) {
            return TOP_LEFT_CORNER
        }
        return copy(column = column)
    }

    /**
     * Creates a new [Position] object representing a position on the same row, but with a column offset by a
     * supplied value. Calling this method with delta 0 will return this, calling it with a positive delta will return
     * a terminal position <code>delta</code> number of columns to the right and for negative numbers the same to the left.
     */
    fun withRelativeColumn(delta: Int): Position {
        if (delta == 0) {
            return this
        }
        return withColumn(column + delta)
    }

    /**
     * Creates a new [Position] object representing a position on the same column, but with a row offset by a
     * supplied value. Calling this method with delta 0 will return this, calling it with a positive delta will return
     * a terminal position <code>delta</code> number of rows to the down and for negative numbers the same up.
     */
    fun withRelativeRow(delta: Int): Position {
        if (delta == 0) {
            return this
        }
        return withRow(row + delta)
    }

    /**
     * Creates a new [Position] object that is translated by an amount of rows and columns specified by another
     * [Position]. Same as calling `withRelativeRow(translate.getRow()).withRelativeColumn(translate.getColumn())`.
     */
    fun withRelative(translate: Position): Position {
        return withRelativeRow(translate.row).withRelativeColumn(translate.column)
    }

    override fun compareTo(other: Position): Int {
        if (row < other.row) {
            return -1
        } else if (row == other.row) {
            if (column < other.column) {
                return -1
            } else if (column == other.column) {
                return 0
            }
        }
        return 1
    }


    companion object {

        /**
         * Constant for the top-left corner (0x0)
         */
        @JvmStatic
        val TOP_LEFT_CORNER = Position(0, 0)

        /**
         * Constant for the 1x1 position (one offset in both directions from top-left)
         */
        @JvmStatic
        val OFFSET_1x1 = Position(1, 1)

        /**
         * This position can be considered as the default
         */
        @JvmStatic
        val DEFAULT_POSITION = TOP_LEFT_CORNER

        /**
         * Used in place of a possible null value. Means that the position is unknown (cursor for example)
         */
        @JvmStatic
        val UNKNOWN = Position(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}

