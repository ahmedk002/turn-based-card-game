package cards;

public enum CardType {
    STATUS('s'),
    ATTACK('a'),
    CREATURE('c');

    private final char designator;

    CardType(char designator) {
        this.designator = designator;
    }
}
