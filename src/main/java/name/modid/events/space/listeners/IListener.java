package name.modid.events.space.listeners;

public interface IListener {

    @Deprecated
    boolean isStatic();

    Class<?> getTarget();

    int getPriority();

    void call(Object event);
}
