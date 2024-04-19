package name.modid.modules.settings;

public class Setting {

    private String name;
    private boolean visible;

    public Setting(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
