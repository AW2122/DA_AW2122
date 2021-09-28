public class Painting extends Artwork {
    protected PaintingType type;
    protected String format;

    public PaintingType getType() {
        return type;
    }

    public void setType(PaintingType type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
