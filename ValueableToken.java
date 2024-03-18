/**
 * Tokens with value, i.e. INT and FLOAT.
 * @author Gil-Ad Shay. 
 */
public class ValueableToken extends Token {
    /**
     * Class wrapping float.
     * The idea is to be able to present it as integer.
     * @author Gil-Ad Shay.
     */
    static class Value {
        private final float value; 
        private final boolean isInteger;

        /**
         * Initialize new float value.
         * @param value Given value.
         */
        public Value(float value) {
            this.value = value;
            this.isInteger = false;
        }

        /**
         * Initialize a new integer value.
         * @param value Given value.
         */
        public Value(int value) {
            this.value = value;
            this.isInteger = true;
        }

        @Override
        public String toString() {
            return isInteger ? Integer.toString((int) value) : Float.toString(value);
        }
    }

    private final Value value;

    /**
     * Initialize a new ValueableToken with the given type and given value.
     * @param type Given type.
     * @param value Given value. 
     */
    public ValueableToken(Token.Type type, Value value) {
        super(type);
        this.value = value;
    }
    
    @Override
    public String toString() {
        return String.format("%s:%s", super.toString(), this.value.toString());
    }
}
