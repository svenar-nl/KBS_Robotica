package nl.windesheim.ictm2f.themes;

public class GUIThemes {
    public enum Theme {
        LIGHT,
        DARK,
        PAIN
    }

    private Theme theme = Theme.LIGHT;

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public ITheme getTheme() {
        switch (theme) {
            case LIGHT:
                return new LightTheme();

            case DARK:
                return new DarkTheme();

            case PAIN:
                return new PainTheme();

            default:
                return null;
        }
    }
}