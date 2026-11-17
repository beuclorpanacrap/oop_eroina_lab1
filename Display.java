public class Display {

    private final String model;
    private final int width;
    private final int height;
    private final float ppi;

    public Display(String model, int width, int height, float ppi) {
        this.model = model;
        this.width = width;
        this.height = height;
        this.ppi = ppi;
    }

    public double calculateDiagonal() {
        return Math.sqrt((double) width * width + (double) height * height) / ppi;
    }

    private int sizeComparison(Display other) {
        return Double.compare(calculateDiagonal(), other.calculateDiagonal());
    }

    private int sharpnessComparison(Display other) {
        return Float.compare(ppi, other.ppi);
    }

    public String compareSize(Display other) {
        double thisSize = calculateDiagonal();
        double otherSize = other.calculateDiagonal();
        int result = sizeComparison(other);

        if (result > 0) {
            return String.format("%s (%.1f\") is bigger than %s (%.1f\")", model, thisSize, other.model, otherSize);
        } else if (result < 0) {
            return String.format("%s (%.1f\") is smaller than %s (%.1f\")", model, thisSize, other.model, otherSize);
        } else {
            return "Both displays are the same size";
        }
    }

    public String compareSharpness(Display other) {
        int result = sharpnessComparison(other);

        if (result > 0) {
            return String.format("%s (%.1f ppi) is sharper than %s (%.1f ppi)", model, ppi, other.model, other.ppi);
        } else if (result < 0) {
            return String.format("%s (%.1f ppi) is sharper than %s (%.1f ppi)", other.model, other.ppi, model, ppi);
        } else {
            return "Both displays have the same ppi";
        }
    }

    public String compareWithMonitor(Display other) {
        String size = compareSize(other);
        String sharpness = compareSharpness(other);

        int sizeResult = sizeComparison(other);
        int sharpnessResult = sharpnessComparison(other);

        String verdict;

        if (sizeResult == 0 && sharpnessResult == 0) {
            verdict = "Both displays are equal.";
        } else if (sizeResult >= 0 && sharpnessResult >= 0) {
            verdict = model + " wins overall.";
        } else if (sizeResult <= 0 && sharpnessResult <= 0) {
            verdict = other.model + " wins overall.";
        } else {
            verdict = "It's a trade-off between size and sharpness.";
        }

        return size + "\n" + sharpness + "\nFinal verdict: " + verdict;
    }
}