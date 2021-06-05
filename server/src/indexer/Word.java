package indexer;

public class Word {
    private int frequency;
    private String text;
    private String textBlock;

    Word(String text) {
        this.text = text;
        this.frequency = 1;
        this.textBlock = "";
    }

    public String getText() {
        return text;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getTextBlock() {
        return textBlock;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextBlock(String textBlock) {
        this.textBlock = textBlock;
    }

    public void incrementFrequency() {
        this.frequency += 1;
    }
}
