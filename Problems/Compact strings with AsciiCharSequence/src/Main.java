import java.util.Arrays;

class AsciiCharSequence implements CharSequence {

    private final byte[] charSequence;

    public AsciiCharSequence(byte[] charSequence) {
        this.charSequence = charSequence.clone();
    }
    // implementation
    @Override
    public int length() {
        if (charSequence == null) {
            return 0;
        } else {
            return this.charSequence.length;
        }
    }

    @Override
    public char charAt(int index) {
        return (char) charSequence[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (end - start == 0) {
            return new AsciiCharSequence(new byte[] {});
        } else {
            return new AsciiCharSequence(Arrays.copyOfRange(charSequence,
                    start,
                    end < charSequence.length ? end : charSequence.length));
        }
    }

    @Override
    public String toString() {
        return new String(charSequence);
    }

    //////////////*********FOR TESTING***********\\\\\\\\\\\\\\\\\\\\\\\\\\

//    public static void main(String[] args) {
//        try {
//            CharSequence sequence = new AsciiCharSequence(new byte[]{61,62,63,64});
//            System.out.println(sequence.subSequence(0,3).toString());
//        }
//        catch (NumberFormatException nfXception) {
//            nfXception.printStackTrace();
//        }
//    }

}