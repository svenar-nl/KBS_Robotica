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

}
