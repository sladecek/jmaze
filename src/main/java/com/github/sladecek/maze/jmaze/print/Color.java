package com.github.sladecek.maze.jmaze.print;
//REV1
/**
 * Represents a color in maze printout.
 */
public final class Color implements java.io.Serializable {
    /**
     * Create new color.
     *
     * @param r red component value
     * @param g green component value
     * @param b blue component value
     * @param a alpha channel value
     */
    @SuppressWarnings("SameParameterValue")
    public Color(final int r, final int g, final int b, final int a) {
        super();
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Create new color from a string
     *
     * @param s Three hexadecimal numbers in the from "rrggbb".
     */
    public Color(final String s) {
        r = parseHex(s, 0);
        g = parseHex(s, 1);
        b = parseHex(s, 2);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getA() {
        return a;
    }

    @Override
    public String toString() {
        return "Color [r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + "]";
    }

    public String toSvg() {
        return "rgb(" + r + "," + g + "," + b + ")";
    }

    public String toThreeJs() {
        return String.format("#%02x%02x%02x", r, g, b);
    }

    private int parseHex(final String s, final int pair) {
        if (s.length() < (pair + 1) * 2) {
            throw new IllegalArgumentException("String '" + s
                    + "' is too short to specify color. Must have at least 6 characters");
        }
        final int hex = 16;
        return Integer.parseInt(s.substring(pair * 2, pair * 2 + 2), hex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        return r == color.r && g == color.g && b == color.b && a == color.a;
    }

    @Override
    public int hashCode() {
        int result = r;
        result = 31 * result + g;
        result = 31 * result + b;
        result = 31 * result + a;
        return result;
    }

    private final int r;
    private final int g;
    private final int b;
    private int a;
}
