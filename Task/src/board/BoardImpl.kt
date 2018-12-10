package board

import board.Direction.*

data class BoardCell(override val i: Int, override val j: Int) : Cell

fun createSquareBoard(width: Int): SquareBoard = object : SquareBoard {
    override val width: Int = width

    val boardCells: List<BoardCell> = (1..width).flatMap { column -> (1..width).map { BoardCell(column, it) } }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
            if (coordinatesOutOfBounds(i, j)) null else boardCells.first { it.i == i && it.j == j }

    override fun getCell(i: Int, j: Int): Cell = getCellOrNull(i, j) ?: throw IllegalArgumentException()

    private fun coordinatesOutOfBounds(i: Int, j: Int) = i > width || j > width || i <= 0 || j <= 0

    override fun getAllCells(): Collection<Cell> {
        return boardCells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val row = boardCells.filter { it -> it.i == i }.filter { it -> it.j in jRange}
        return if (jRange.first > jRange.last) row.asReversed() else row
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val column = boardCells.filter { it -> it.j == j }.filter { it -> it.i in iRange}
        return if (iRange.first > iRange.last) column.asReversed() else column
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> boardCells.find { it.i == i - 1 && it.j == j }
            DOWN -> boardCells.find { it.i == i + 1 && it.j == j }
            RIGHT -> boardCells.find { it.i == i && it.j == j + 1 }
            LEFT -> boardCells.find { it.i == i && it.j == j - 1 }
        }
    }
}

fun <T> createGameBoard(width: Int): GameBoard<T> {
    val squareBoard = createSquareBoard(width)
    return object : GameBoard<T>, SquareBoard by squareBoard {
        private val boardCellsWithValues = mutableMapOf<Cell, T?>()

        override fun get(cell: Cell): T? = boardCellsWithValues[cell]

        override fun set(cell: Cell, value: T?) {
            boardCellsWithValues[cell] = value
        }

        override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = boardCellsWithValues.filter { it -> predicate(it.value) }.keys

        override fun find(predicate: (T?) -> Boolean): Cell? = getAllCells().find { predicate(get(it)) }

        override fun any(predicate: (T?) -> Boolean): Boolean = find(predicate) != null

        override fun all(predicate: (T?) -> Boolean): Boolean = getAllCells().all { predicate(get(it)) }
    }
}

