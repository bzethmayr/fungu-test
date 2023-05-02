package net.zethmayr.fungu.test;

/**
 * This class has nothing to do with any application class, provably.
 * So, any code using it is testing generic functionality.
 */
public class Irrelevant {

    /**
     * This field has nothing to do with any application functionality,
     * but is definitely a field.
     */
    private String field;

    /**
     * Creates a new instance with the given field value.
     *
     * @param field the field value.
     */
    public Irrelevant(final String field) {
        setField(field);
    }

    /**
     * Creates a new instance with a null field value.
     */
    public Irrelevant() {
        this(null);
    }

    /**
     * Sets the field value to the given value.
     *
     * @param field the new value.
     */
    public final void setField(final String field) {
        this.field = field;
    }

    /**
     * Returns the field value.
     *
     * @return the field value.
     */
    public final String getField() {
        return field;
    }
}
