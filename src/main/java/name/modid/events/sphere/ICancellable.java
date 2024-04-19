package name.modid.events.sphere;

public interface ICancellable {

    void setCancelled(boolean cancelled);

    default void cancel() { setCancelled(true); }

    boolean isCancelled();
}
