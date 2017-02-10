package cs2114.minesweeper;

import student.*;

// -------------------------------------------------------------------------
/**
 * @author krishanmadan
 * @version Feb 14, 2016
 */
public class MineSweeperBoardTest
    extends student.TestCase
{
    /**
     * Create MineSweeper Test Object y
     */
    MineSweeperBoard y;


    // ----------------------------------------------------------
    /**
     * Constructor
     */
    public MineSweeperBoardTest()
    {
        // no objects created-no need for code
    }


    // ----------------------------------------------------------
    /**
     * Sets up the test fixture. Called before every test case method.
     */
    public void setUp()
    {
        y = new MineSweeperBoard(4, 4, 2);
    }


    /**
     * test for adjacent to test
     */
    public void testAdjacentTo()
    {
        MineSweeperCell c = MineSweeperCell.ADJACENT_TO_0;
        assertNotNull(c);
        // testing for exception
        Exception thrown = null;
        try
        {
            c = MineSweeperCell.adjacentTo(10);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);

        thrown = null;
        try
        {
            MineSweeperCell.adjacentTo(-1);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        assertNotNull(MineSweeperCell.values());
        assertNotNull(
            MineSweeperCell.valueOf(MineSweeperCell.ADJACENT_TO_0.toString()));

    }


    /**
     * test loadBoardState
     */
    public void testloadBoardState()
    {
        MineSweeperBoard a = new MineSweeperBoard(2, 2, 1);
        Exception thrown = null;
        // loadBoardState testing
        // wrong number of rows
        try
        {
            a.loadBoardState("00");
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        thrown = null;
        // wrong number of columns
        try
        {
            a.loadBoardState("0000 ", " ");
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
        // Wrong symbol in cell
        try
        {
            a.loadBoardState("00", "$+");
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertTrue(thrown instanceof IllegalArgumentException);
    }


    /**
     * * This method test Equals.
     */
    public void testEqual()
    {
        MineSweeperBoard mBoard1 = new MineSweeperBoard(4, 4, 6);
        MineSweeperBoard mBoard2 = new MineSweeperBoard(4, 4, 6);
        mBoard1.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        mBoard2.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        // test the same board same dimensions
        assertTrue(mBoard1.equals(mBoard2));
        // same board testing same board
        assertTrue(mBoard1.equals(mBoard1));
        // testing same dimensions board with different cell
        MineSweeperBoard mBoard3 = new MineSweeperBoard(4, 4, 6);
        mBoard3.loadBoardState("    ", "O+OO", "O++O", "OOOO");
        assertFalse(mBoard1.equals(mBoard3));
        MineSweeperBoard mBoard4 = new MineSweeperBoard(15, 1, 0);
        mBoard4.loadBoardState("OFM+* 123456788");
        assertFalse(mBoard1.toString().equals(mBoard3.toString()));
        // testing two string against a board
        assertFalse(mBoard4.toString().equals(mBoard2.toString()));
        // testing against a string
        assertFalse(mBoard1.equals("abc"));
        assertFalse(mBoard1.equals("null"));
        // same width but different height
        MineSweeperBoard mBoard6 = new MineSweeperBoard(4, 5, 6);
        mBoard6.loadBoardState("    ", "O+OO", "O++O", "OOOO", "OOOO");
        assertFalse(mBoard6.equals(mBoard1));
        // different width same height
        MineSweeperBoard mBoard5 = new MineSweeperBoard(5, 4, 6);
        mBoard5.loadBoardState("     ", "O+OOO", "O++OO", "OOOOO");
        assertFalse(mBoard5.equals(mBoard1));
    }


    // ----------------------------------------------------------
    /**
     * Tests if two boards are equal
     *
     * @param theBoard is the board
     * @param expected
     *            is String
     */
    public void assertBoard(MineSweeperBoard theBoard, String... expected)
    {
        MineSweeperBoard expectedBoard =
            new MineSweeperBoard(expected[0].length(), expected.length, 0);
        expectedBoard.loadBoardState(expected);
        assertEquals(expectedBoard, theBoard); // uses equals() from
                                               // MineSweeperBoardBase
    }


    // ----------------------------------------------------------
    /**
     * Tests setCell Method
     */
    public void testSetCell()
    {
        // board is declared as part of the test fixture, and
        // is initialized to be 4x4
        MineSweeperBoard ab = new MineSweeperBoard(4, 4, 2);
        ab.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        ab.setCell(1, 2, MineSweeperCell.FLAGGED_MINE);
        assertEquals(ab.getCell(1, 2), MineSweeperCell.FLAGGED_MINE);
        ab.setCell(5, -3, MineSweeperCell.FLAGGED_MINE);

    }


    /**
     * This method tests UncoverCell.
     */
    public void testUncoverCell()
    {
        y.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        y.uncoverCell(1, 1);
        assertEquals(y.getCell(1, 1), MineSweeperCell.ADJACENT_TO_2);
        y.loadBoardState("    ", "OOOO", "O++O", "OOOO");
        y.uncoverCell(3, 1);
        assertEquals(y.getCell(3, 1), MineSweeperCell.ADJACENT_TO_1);
    }


    /**
     * This method tests FlagCell.
     */
    public void testFlagCell()
    {
        y.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        y.flagCell(1, 2);

        assertEquals(y.getCell(1, 2), MineSweeperCell.FLAGGED_MINE);
        y.loadBoardState("    ", "OOOO", "OOOO", "OOOO");
        y.flagCell(0, 2);
        assertEquals(y.getCell(0, 2), MineSweeperCell.FLAG);
        y.loadBoardState("    ", "OOOO", "O++O", "OOOF");

        y.flagCell(3, 3);
        /**
         * y.loadBoardState("    ", "OOOO", "O+MO", "OOOO"); y.flagCell(2, 2);
         * y.loadBoardState("    ", "OOOO", "O+MF", "OOOO"); y.flagCell(2, 2);
         * y.flagCell(3, 2);
         **/

    }


    /**
     * This method tests RevealCell.
     */
    public void testRevealBoard()
    {
        y.loadBoardState("OOOO", "OOOO", "O++O", "OFFO");

        y.revealBoard();
        assertBoard(y, "    ", "1221", "1**1", "1FF1");

    }


    // ----------------------------------------------------------
    /**
     * This method tests NumberOfAdjacentMines.
     */
    public void testnumberOfAdjacentMines()
    {
        y.loadBoardState("    ", "OOOO", "O++O", "OFFO");

        assertEquals(y.numberOfAdjacentMines(1, 1), 2);

        y.loadBoardState("    ", "OOOO", "O++O", "O*MO");

        assertEquals(y.numberOfAdjacentMines(0, 2), 2);

        y.loadBoardState("    ", "OOOO", "O++O", "OMMO");
        assertEquals(y.numberOfAdjacentMines(0, 2), 2);

    }


    /**
     * This method tests isGameWon.
     */
    public void testisGameWon()
    {
        y.loadBoardState("    ", "    ", "1221", "1MM1");

        assertEquals(y.isGameWon(), true);

        y.loadBoardState("O   ", "    ", "1221", "1MM1");

        assertFalse(y.isGameWon());

        y.loadBoardState("    ", "    ", "1221", "1*M1");
        assertFalse(y.isGameWon());

        y.loadBoardState("    ", "    ", "1221", "1FM1");
        assertFalse(y.isGameWon());
        y.loadBoardState("1111", "111*", "1221", "1MM1");
        assertFalse(y.isGameWon());
        y.loadBoardState(" 123", "4567", "8*++", "FM 4");
        assertFalse(y.isGameWon());

    }


    /**
     * This method tests isGameLost.
     */
    public void testisGameLost()
    {
        y.loadBoardState("    ", "111 ", "1*31", "2MM1");

        assertEquals(y.isGameLost(), true);
        y.loadBoardState("    ", "    ", "1221", "1MM1");
        assertFalse(y.isGameLost());

    }


    // ----------------------------------------------------------
    /**
     * Tests getCell Method
     */
    public void testGetCell()
    {
        y.loadBoardState("    ", "111 ", "1*31", "2MM1");
        assertEquals(y.getCell(3, 1), MineSweeperCell.ADJACENT_TO_0);
        assertEquals(y.getCell(-3, 1), MineSweeperCell.INVALID_CELL);
        assertEquals(y.getCell(4, 1), MineSweeperCell.INVALID_CELL);
        assertEquals(y.getCell(2, 5), MineSweeperCell.INVALID_CELL);
        assertEquals(y.getCell(2, -3), MineSweeperCell.INVALID_CELL);
    }

}
