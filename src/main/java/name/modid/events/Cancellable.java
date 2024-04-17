package name.modid.events;

public class Cancellable {

    private boolean cancelled = false;

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() { setCancelled(true); }
}
