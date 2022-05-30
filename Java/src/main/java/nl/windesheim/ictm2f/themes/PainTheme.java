package nl.windesheim.ictm2f.themes;

import java.awt.Color;

public class PainTheme implements ITheme {

    @Override
    public Color getBackgroundColor() {
        return new Color(255, 255, 0);
    }

    @Override
    public Color getAltBackgroundColor() {
        return new Color(200, 200, 0);
    }

    @Override
    public Color getTextColor() {
        return new Color(255, 0, 255);
    }

    @Override
    public Color getAltTextColor() {
        return new Color(200, 0, 200);
    }

}
