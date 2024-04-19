package name.modid.events.sphere.listeners;

public interface IListeners {

    @Deprecated
    boolean isStatic();

    Class<?> getTarget();

    int getPriority();

    void call(Object event);
}
