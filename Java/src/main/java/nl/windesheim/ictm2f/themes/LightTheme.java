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
    public Color getInfoTextColor() {
        return new Color(52, 119, 174);
    }

    @Override
    public Color getWarningTextColor() {
        return new Color(174, 148, 52);
    }

    @Override
    public Color getErrorTextColor() {
        return new Color(174, 52, 52);
    }

    @Override
    public Color getGridLineColor() {
        return new Color(128, 128, 128);
    }

    @Override
    public Color getGridBackgroundColor() {
        return new Color(255, 255, 255);
    }

    @Override
    public Color getGridTextColor() {
        return new Color(196, 196, 196);
    }

    @Override
    public Color getGridPointColor() {
        return new Color(176, 0, 0);
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
        return new Color(51, 182, 0);
    }

    @Override
    public Color getGridFetchedPointColor() {
        return new Color(147, 0, 242);
    }

    @Override
    public Color getGridTitleColor() {
        return new Color(56, 56, 56);
    }

    @Override
    public Color getGridRobotColor() {
        return new Color(0, 190, 190);
    }
}
