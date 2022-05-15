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
        return Color.gray;
    }

    @Override
    public Color getGridBackgroundColor() {
        return new Color(255, 255, 255);
    }

    @Override
    public Color getGridTextColor() {
        return new Color(144, 144, 144);
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
        return new Color(56, 56, 56);
    }

    @Override
    public Color getGridRobotColor() {
        return Color.cyan;
    }

    @Override
    public Color getGridFetchedPointColor() {
        return Color.ORANGE;
    }
}
