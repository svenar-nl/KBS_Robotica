package nl.windesheim.ictm2f.themes;

public class GUIThemes {
    public enum Theme {
        LIGHT,
        DARK
    }

    private Theme theme = Theme.DARK;

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public ITheme getTheme() {
        switch (theme) {
            case LIGHT:
                return new LightTheme();

            case DARK:
                return new DarkTheme();

            default:
                return null;
        }
    }
}