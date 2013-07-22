import com.sun.media.sound.FFT;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/21/13
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class FastFT {

        public double[] getMagnitudes(double[] amplitudes) {

            int sampleSize = amplitudes.length;

            FFT fft = new FFT(sampleSize / 2, -1);
            fft.transform(amplitudes);

            double[] complexNumbers = amplitudes;

            int indexSize = sampleSize / 2;

            // FFT produces a transformed pair of arrays where the first half of the
            // values represent positive frequency components and the second half
            // represents negative frequency components.
            // we omit the negative ones
            int positiveSize = indexSize / 2;

            double[] mag = new double[positiveSize];
            for (int i = 0; i < indexSize; i += 2) {
                mag[i / 2] = Math.sqrt(complexNumbers[i] * complexNumbers[i] + complexNumbers[i + 1] * complexNumbers[i + 1]);
            }

            return mag;
        }

    }

