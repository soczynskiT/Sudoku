import gamecode.boardbuilding.CellEntry;
import org.junit.Test;

public class SudokuTestSuite {
    @Test
    public void testCheckBlockNo() {
        CellEntry entry = new CellEntry(0, 7);

        System.out.println(entry.getRowNo());
        System.out.println(entry.getColumnNo());
        System.out.println(entry.getBlockNo().toString());



    }
}
