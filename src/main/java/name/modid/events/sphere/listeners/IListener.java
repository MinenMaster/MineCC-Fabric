package name.modid.events.sphere.listeners;

public interface IListener {

    void call(Object event);

    Class<?> getTarget();

    int getPriority();

    @Deprecated
    boolean isStatic();
}
