package nl.windesheim.ictm2f.themes;

import java.awt.Color;

public class DarkTheme implements ITheme {

    @Override
    public Color getBackgroundColor() {
        return new Color(51, 51, 51);
    }

    @Override
    public Color getAltBackgroundColor() {
        return new Color(42, 42, 42);
    }

    @Override
    public Color getTextColor() {
        return new Color(242, 242, 242);
    }

    @Override
    public Color getAltTextColor() {
        return new Color(152, 152, 152);
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

    @Override
    public Color getGridTitleColor() {
        return Color.white;
    }

    @Override
    public Color getGridRobotColor() {
        return Color.cyan;
    }
}
