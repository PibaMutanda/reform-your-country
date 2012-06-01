package BBParser;

/**
 * @author xBlackCat
 */
class Part {
    private final String content;

    Part(String content) {
        if (content == null || content.length() == 0) {
            throw new IllegalArgumentException("Part can not be empty.");
        }

        this.content = content;
    }

    String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}