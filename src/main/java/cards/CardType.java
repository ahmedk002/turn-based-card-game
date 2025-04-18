package cards;

public enum CardType {
    STATUS('s'),
    ATTACK('a'),
    CREATURE('c');

    private final char designator;

    CardType(char designator) {
        this.designator = designator;
    }

    public String toString() {
        String type = switch (designator) {
            case 's' -> "status";
            case 'a' -> "attack";
            case 'c' -> "creature";
            default -> "";
        };
        return type;
    }
}
