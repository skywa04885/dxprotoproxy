package com.github.skywa04885.dxprotoproxy.dxprotoproxy.net;

import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class NetService {
    /**
     * Represents a state of the net service.
     */
    public static abstract class State {
        protected @Nullable NetService parent;

        /**
         * Gets the parent.
         *
         * @return the parent.
         */
        public @Nullable NetService parent() {
            return parent;
        }

        /**
         * Sets the parent.
         *
         * @param parent the new parent.
         */
        public void setParent(@Nullable NetService parent) {
            this.parent = parent;
        }

        public abstract void entry() throws Exception;

        public abstract void exit() throws Exception;

        public abstract @NotNull String label();
    }

    /**
     * Represents the active state of a net service.
     */
    public static abstract class Active extends State {
        public static final String LABEL = "Active";

        @Override
        public @NotNull String label() {
            return LABEL;
        }
    }

    /**
     * Represents the inactive state of a net service.
     */
    public static class Inactive extends State {
        public static final String LABEL = "Inactive";

        @Override
        public void entry() throws Exception {

        }

        @Override
        public void exit() throws Exception {

        }

        @Override
        public @NotNull String label() {
            return LABEL;
        }
    }

    /**
     * Represents the crashed state of a net service.
     */
    public static class Crashed extends State {
        public static final String LABEL = "Crashed";

        private final @NotNull Exception exception;

        /**
         * Constructs a new crashed state with the given exception.
         *
         * @param exception the exception causing the crash.
         */
        public Crashed(@NotNull Exception exception) {
            this.exception = exception;
        }

        /**
         * Gets the exception that caused the crash.
         *
         * @return the exception.
         */
        public @NotNull Exception exception() {
            return exception;
        }

        @Override
        public void entry() throws Exception {

        }

        @Override
        public void exit() throws Exception {

        }

        @Override
        public @NotNull String label() {
            return LABEL;
        }
    }

    private @NotNull State state;

    /**
     * Constructs a new inactive net state.
     */
    public NetService() throws Exception {
        state = createInactiveState();
    }

    /**
     * Gets the current state of the net service.
     *
     * @return the current state of the net service.
     */
    public @NotNull State state() {
        return state;
    }

    /**
     * Activates the net service.
     */
    public void activate() throws Exception {
        // Make sure we're either in the inactive or crashed state.
        assert state instanceof Inactive || state instanceof Crashed;

        // Creates the active state.
        final Active active = createActiveState();

        // Transitions to the active state.
        transition(active);
    }

    /**
     * Deactivates the net service.
     */
    public void deactivate() throws Exception {
        // Make sure we're in the active state.
        assert state instanceof Active;

        // Creates the inactive state.
        final Inactive inactive = createInactiveState();

        // Transitions to the active state.
        transition(inactive);
    }

    /**
     * Transitions the net service to the given state.
     *
     * @param nextState the state tot transition to.
     */
    protected void transition(@NotNull State nextState) throws Exception {
        // Calls the exit of the current state.
        state.exit();

        // Sets the parent of the next state and calls its entry.
        nextState.setParent(this);
        nextState.entry();

        // Sets the state to the next state.
        state = nextState;
    }

    /**
     * Creates a new inactive state instance.
     *
     * @return the inactive state instance.
     */
    protected Inactive createInactiveState() throws Exception {
        return new Inactive();
    }

    /**
     * Creates a new active state instance.
     *
     * @return the active state instance.
     */
    protected abstract Active createActiveState() throws Exception;

    /**
     * Gets the name of the service.
     *
     * @return the string containing the name of the service.
     */
    public abstract @NotNull String getName();

    /**
     * Gets the description of the service.
     *
     * @return the string containing the description of the service.
     */
    public abstract @NotNull String getDescription();
}
