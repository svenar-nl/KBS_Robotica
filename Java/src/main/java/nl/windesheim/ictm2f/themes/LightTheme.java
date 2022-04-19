package nl.windesheim.ictm2f.themes;

import java.awt.Color;

public class LightTheme implements ITheme {

    @Override
    public Color getBackgroundColor() {
        return new Color(244, 236, 219);
    }

    @Override
    public Color getAltBackgroundColor() {
        return new Color(255, 245, 234);
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
