package board

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import kotlin.test.fail

fun assertThrows(f: () -> Unit) = try {
    f()
    fail("Expected: Exception Found: no exception")
} catch (e: Exception) {

}

class TestSquareBoard {

    private fun Cell?.asString() = if (this != null) "($i, $j)" else ""

    private fun Collection<Cell>.asString() = joinToString(prefix = "[", postfix = "]") { it.asString() }

    private fun assertEquals(expected: Any?, actual: Any?) {
        if (expected != actual) fail("Expected: $expected Found: $actual")
    }

    @Test
    fun `a board is created with the correct width`() {
        val board = createSquareBoard(4)
        assertEquals(4, board.width)
    }

    @Test
    fun `a 4x4 board returns correct cell coordinates`() {
        val board = createSquareBoard(4)
        assertEquals(4, board.getCell(4, 3).i)
        assertEquals(3, board.getCell(4, 3).j)
    }

    @Test
    fun `row coordinate outside size of board throws exception`() {
        val board = createSquareBoard(4)
        assertThrows { board.getCell(5, 1) }
    }

    @Test
    fun `column coordinate outside size of board throws exception`() {
        val board = createSquareBoard(4)
        assertThrows { board.getCell(1, 5) }
    }

    @Test
    fun `row coordinate of zero throws exception`() {
        val board = createSquareBoard(4)
        assertThrows { board.getCell(0, 3) }
    }

    @Test
    fun `column coordinate of zero returns null`() {
        val board = createSquareBoard(4)
        assertEquals(null, board.getCellOrNull(3, 0))
    }

    @Test
    fun testAllCells() {
        val board = createSquareBoard(2)
        val cells = board.getAllCells().sortedWith(compareBy<Cell> { it.i }.thenBy { it.j })
        assertEquals("[(1, 1), (1, 2), (2, 1), (2, 2)]", cells.asString())
    }

    @Test
    fun testCell() {
        val board = createSquareBoard(2)
        val cell = board.getCellOrNull(1, 2)
        assertEquals(1, cell?.i)
        assertEquals(2, cell?.j)
    }

    @Test
    fun testNoCell() {
        val board = createSquareBoard(2)
        val cell = board.getCellOrNull(3, 3)
        assertEquals(null, cell)
    }

    @Test
    fun `retrieve one cell in a row`() {
        val board = createSquareBoard(4)
        val row = board.getRow(1, 1..1)
        assertEquals("[(1, 1)]", row.asString())
    }

    @Test
    fun `retrieve first two cells in a row`() {
        val board = createSquareBoard(4)
        val row = board.getRow(1, 1..2)
        assertEquals("[(1, 1), (1, 2)]", row.asString())
    }

    @Test
    fun `retrieve cells in row other than first row`() {
        val board = createSquareBoard(4)
        val row = board.getRow(3, 1..4)
        assertEquals("[(3, 1), (3, 2), (3, 3), (3, 4)]", row.asString())
    }

    @Test
    fun testRow() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 1..2)
        assertEquals("[(1, 1), (1, 2)]", row.asString())
    }

    @Test
    fun testRowReversed() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 2 downTo 1)
        assertEquals("[(1, 2), (1, 1)]", row.asString())
    }

    @Test
    fun testRowWrongRange() {
        val board = createSquareBoard(2)
        val row = board.getRow(1, 1..10)
        assertEquals("[(1, 1), (1, 2)]", row.asString())
    }

    @Test
    fun `retrieve one cell in a column`() {
        val board = createSquareBoard(4)
        val column = board.getColumn(1..1, 1)
        assertEquals("[(1, 1)]", column.asString())
    }

    @Test
    fun `retrieve first two cells in a column`() {
        val board = createSquareBoard(4)
        val column = board.getColumn(1..2, 1)
        assertEquals("[(1, 1), (2, 1)]", column.asString())
    }

    @Test
    fun `retrieve cells in column other than first column`() {
        val board = createSquareBoard(4)
        val column = board.getColumn(1..4, 3)
        assertEquals("[(1, 3), (2, 3), (3, 3), (4, 3)]", column.asString())
    }

    @Test
    fun `test column reversed`() {
        val board = createSquareBoard(2)
        val column = board.getColumn(2 downTo 1, 1)
        assertEquals("[(2, 1), (1, 1)]", column.asString())
    }

    @Test
    fun `test column wrong range`() {
        val board = createSquareBoard(2)
        val column = board.getColumn(1..10, 1)
        assertEquals("[(1, 1), (2, 1)]", column.asString())
    }

    @Test
    fun `get neighbor cell that is UP`() {
        val board = createSquareBoard(4)
        with(board) {
            val cell = board.getCell(2, 1)
            assertNotNull(cell)
            assertEquals("(1, 1)", cell.getNeighbour(Direction.UP).asString())
        }
    }

    @Test
    fun `return null if no neighbor cell that is UP`() {
        val board = createSquareBoard(4)
        with(board) {
            val cell = board.getCell(1, 4)
            assertNotNull(cell)
            assertEquals(null, cell.getNeighbour(Direction.UP))
        }
    }

    @Test
    fun `get neighbor cell that is DOWN`() {
        val board = createSquareBoard(4)
        with(board) {
            val cell = board.getCell(3, 3)
            assertNotNull(cell)
            assertEquals("(4, 3)", cell.getNeighbour(Direction.DOWN).asString())
        }
    }

    @Test
    fun `return null if no neighbor cell that is DOWN`() {
        val board = createSquareBoard(4)
        with(board) {
            val cell = board.getCell(4, 3)
            assertNotNull(cell)
            assertEquals(null, cell.getNeighbour(Direction.DOWN))
        }
    }

    @Test
    fun `get neighbor cell that is LEFT`() {
        val board = createSquareBoard(4)
        with(board) {
            val cell = board.getCell(2, 3)
            assertNotNull(cell)
            assertEquals("(2, 2)", cell.getNeighbour(Direction.LEFT).asString())
        }
    }

    @Test
    fun `return null if no neighbor cell that is LEFT`() {
        val board = createSquareBoard(4)
        with(board) {
            val cell = board.getCell(3, 1)
            assertNotNull(cell)
            assertEquals(null, cell.getNeighbour(Direction.LEFT))
        }
    }

    @Test
    fun `get neighbor cell that is RIGHT`() {
        val board = createSquareBoard(4)
        with(board) {
            val cell = board.getCell(2, 3)
            assertNotNull(cell)
            assertEquals("(2, 4)", cell.getNeighbour(Direction.RIGHT).asString())
        }
    }

    @Test
    fun `return null if no neighbor cell that is RIGHT`() {
        val board = createSquareBoard(4)
        with(board) {
            val cell = board.getCell(3, 4)
            assertNotNull(cell)
            assertEquals(null, cell.getNeighbour(Direction.RIGHT))
        }
    }

    @Test
    fun testNeighbour() {
        val board = createSquareBoard(2)
        with(board) {
            val cell = getCellOrNull(1, 1)
            assertNotNull(cell)
            assertEquals(null, cell!!.getNeighbour(Direction.UP))
            assertEquals(null, cell.getNeighbour(Direction.LEFT))
            assertEquals("(2, 1)", cell.getNeighbour(Direction.DOWN).asString())
            assertEquals("(1, 2)", cell.getNeighbour(Direction.RIGHT).asString())
        }
    }
}
