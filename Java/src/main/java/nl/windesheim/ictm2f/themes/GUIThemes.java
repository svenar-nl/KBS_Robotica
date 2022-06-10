package nl.windesheim.ictm2f.themes;

public class GUIThemes {
    public enum Theme {
        LIGHT("Light"),
        DARK("Dark");

        private String name;

        private Theme(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
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

    public static Theme getTheme(String name) {
        for (Theme theme : Theme.values()) {
            if (theme.getName().equalsIgnoreCase(name)) {
                return theme;
            }
        }
        return null;
    }

    public String getThemeName() {
        return this.theme.getName();
    }
}