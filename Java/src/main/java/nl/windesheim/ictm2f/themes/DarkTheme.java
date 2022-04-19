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
    
}
