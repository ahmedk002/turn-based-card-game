package cards;

public enum CardEffect {
    DECREASE_LOW("D2"),
    DECREASE_MED("D6"),
    DECREASE_HIGH("D10"),
    INCREASE_LOW("I2"),
    INCREASE_MED("I5"),
    INCREASE_HIGH("I10"),
    HEAL_LOW("H2"),
    HEAL_MED("H5"),
    HEAL_HIGH("H10");

    private final String effect;

    private static final int LOW_STRENGTH = 1;
    private static final int MEDIUM_STRENGTH = 5;
    private static final int HIGH_STRENGTH = 10;

    CardEffect(String effect) {
        this.effect = effect;
    }

    public String toString() {
        return getEffect() + getStrength();
    }

    public String getEffect() {
        return switch (effect.charAt(0)) {
            case 'D' -> "damage decreases";
            case 'I' -> "damage increases";
            case 'H' -> "heals";
            default -> "";
        };
    }

    public String getStrength() {
        int strength = Integer.parseInt(effect.substring(1));
        if (strength == LOW_STRENGTH) {
            return " slightly";
        } else if (strength == MEDIUM_STRENGTH) {
            return "";
        } else {
            return " significantly";
        }
    }

    public int getStrengthValue() {
        return Integer.parseInt(effect.substring(1));
    }
}
