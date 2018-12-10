package board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TestGameBoard {
    operator fun <T> GameBoard<T>.get(i: Int, j: Int) = get(getCell(i, j))
    operator fun <T> GameBoard<T>.set(i: Int, j: Int, value: T) = set(getCell(i, j), value)

    private fun Cell?.asString() = if (this != null) "($i, $j)" else ""

    @Test
    fun testGetAndSetElement() {
        val gameBoard = createGameBoard<Char>(2)
        gameBoard[1, 1] = 'a'
        assertEquals('a', gameBoard[1, 1])
    }

    @Test
    fun `get returns null if no value has been set`() {
        val gameBoard = createGameBoard<String>(4)
        assertEquals(null, gameBoard[2, 4])
    }

    @Test
    fun testFilter() {
        val gameBoard = createGameBoard<Char>(2)
        gameBoard[1, 1] = 'a'
        gameBoard[1, 2] = 'b'
        val cells = gameBoard.filter { it == 'a' }
        assertEquals(1, cells.size)
        val cell = cells.first()
        assertEquals(1, cell.i)
        assertEquals(1, cell.j)
    }

    @Test
    fun `filter returns an empty collection if nothing matches the predicate`() {
        val gameBoard = createGameBoard<String>(2)
        gameBoard[1 ,1] = "cat"
        gameBoard[1, 2] = "dog"
        gameBoard[2, 1] = "parrot"
        gameBoard[2, 2] = "goldfish"
        val cells = gameBoard.filter { it == "hamster" }
        assertEquals(0, cells.size)
    }

    @Test
    fun `cannot create key value pair for out of bounds cell`() {
        val gameBoard = createGameBoard<String>(2)
        assertThrows { gameBoard[4, 4] = "cat" }
    }

    @Test
    fun `find returns cell if there is a predicate match`() {
        val gameBoard = createGameBoard<String>(2)
        gameBoard[1 ,1] = "cat"
        gameBoard[1, 2] = "dog"
        gameBoard[2, 1] = "parrot"
        gameBoard[2, 2] = "goldfish"
        val cell = gameBoard.find { it == "goldfish" }
        assertEquals("(2, 2)", cell.asString())
    }

    @Test
    fun `find returns null if there is not a predicate match`() {
        val gameBoard = createGameBoard<String>(2)
        gameBoard[1 ,1] = "cat"
        gameBoard[1, 2] = "dog"
        gameBoard[2, 1] = "parrot"
        gameBoard[2, 2] = "goldfish"
        val cell = gameBoard.find { it == "hamster" }
        assertEquals(null, cell)
    }

    @Test
    fun `find returns cell when cell value is null`() {
        val gameBoard = createGameBoard<String>(2)
        gameBoard[1 ,1] = "cat"
        gameBoard[1, 2] = "dog"
        val cell = gameBoard.find { it == null }
        assertEquals("(2, 1)", cell.asString())
    }


    @Test
    fun testAll() {
        val gameBoard = createGameBoard<Char>(2)
        gameBoard[1, 1] = 'a'
        gameBoard[1, 2] = 'a'
        assertFalse(gameBoard.all { it == 'a' })
        gameBoard[2, 1] = 'a'
        gameBoard[2, 2] = 'a'
        assertTrue(gameBoard.all { it == 'a' })
    }

    @Test
    fun `any returns false when no values match the predicate`() {
        val gameBoard = createGameBoard<String>(2)
        gameBoard[1 ,1] = "cat"
        gameBoard[1, 2] = "dog"
        gameBoard[2, 1] = "parrot"
        gameBoard[2, 2] = "goldfish"
        assertEquals(false, gameBoard.any { it == "hamster" })
    }

    @Test
    fun `any returns true when a value matches the predicate`() {
        val gameBoard = createGameBoard<String>(2)
        gameBoard[1 ,1] = "cat"
        gameBoard[1, 2] = "dog"
        gameBoard[2, 1] = "parrot"
        gameBoard[2, 2] = "goldfish"
        assertEquals(true, gameBoard.any { it == "cat" })
    }

    @Test
    fun testAny() {
        val gameBoard = createGameBoard<Char>(2)
        gameBoard[1, 1] = 'a'
        gameBoard[1, 2] = 'b'
        assertTrue(gameBoard.any { it in 'a'..'b' })
        assertTrue(gameBoard.any { it == null })
    }
}