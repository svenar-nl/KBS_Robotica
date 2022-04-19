package nl.windesheim.ictm2f.themes;

import java.awt.Color;

public class LightTheme implements ITheme {

    @Override
    public Color getBackgroundColor() {
        return new Color(224, 216, 199);
    }

    @Override
    public Color getTextColor() {
        return new Color(51, 51, 51);
    }
    
}
