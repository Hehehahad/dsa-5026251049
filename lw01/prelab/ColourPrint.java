package lw01.prelab;

public class ColourPrint extends PrintJob {

    public ColourPrint(String id, int pages) {
        super(id, pages);
    }

    @Override
    public int calculateCharge() {
        int firstBatch = Math.min(getPages(), 10);
        int extraPages = Math.max(getPages() - 10, 0);

        return (firstBatch * 1500) + (extraPages * 1000) + 2000;
    }

    @Override
    public String label() {
        return "Colour";
    }
}
