package nl.windesheim.ictm2f.themes;

import java.awt.Color;

public class LightTheme implements ITheme {

    @Override
    public Color getBackgroundColor() {
        return new Color(229, 229, 229);
    }

    @Override
    public Color getAltBackgroundColor() {
        return new Color(183, 183, 183);
    }

    @Override
    public Color getTextColor() {
        return new Color(51, 51, 51);
    }

    @Override
    public Color getAltTextColor() {
        return new Color(22, 22, 22);
    }

    @Override
    public Color getGridLineColor() {
        return Color.gray;
    }

    @Override
    public Color getGridBackgroundColor() {
        return Color.white;
    }

    @Override
    public Color getGridTextColor() {
        return Color.lightGray;
    }

    @Override
    public Color getGridPointColor() {
        return Color.red;
    }

    @Override
    public Color getGridPathColor() {
        return Color.green;
    }
}
