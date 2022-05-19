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
    public Color getInfoTextColor() {
        return new Color(172, 213, 254);
    }

    @Override
    public Color getWarningTextColor() {
        return new Color(254, 238, 172);
    }

    @Override
    public Color getErrorTextColor() {
        return new Color(254, 172, 172);
    }

    @Override
    public Color getGridLineColor() {
        return new Color(128, 128, 128);
    }

    @Override
    public Color getGridBackgroundColor() {
        return new Color(30, 30, 30);
    }

    @Override
    public Color getGridTextColor() {
        return new Color(54, 54, 54);
    }

    @Override
    public Color getGridPointColor() {
        return new Color(187, 15, 15);
    }

    @Override
    public Color getGridStartPointColor() {
        return new Color(130, 75, 15);
    }

    @Override
    public Color getGridPointTextColor() {
        return new Color(235, 235, 235);
    }

    @Override
    public Color getGridPathColor() {
        return new Color(76, 182, 32);
    }

    @Override
    public Color getGridFetchedPointColor() {
        return new Color(115, 33, 234);
    }

    @Override
    public Color getGridTitleColor() {
        return new Color(235, 235, 235);
    }

    @Override
    public Color getGridRobotColor() {
        return new Color(0, 197, 201);
    }
}
