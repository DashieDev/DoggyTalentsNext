package doggytalents.common.entity.anim;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import org.joml.Vector3f;

/**
 * @param f affects the speed at which the system responds to changes and the natural frequency
 * the system tends to vibrate  
 * @param z Damping: 0.0 = Infinite Vibration, 1.0 = No Vibration (Critically Damped).
 * Values > 1.0 = No vibration and slowly settles at target. 
 * Values 0.5 - 0.8 are "Fluffy".
 * @param r Initial Response: 0 = Warms up, >0 = reacts immediately, 
 * >1 = Overshoot the target, <0 = anticipate the changes. 2 is typical.
 */
public class SecondOrderDynamics<T> {
    
    private final ToFloatArrayCodec<T> codec;
    private final float[] xp; // Previous Input
    private final float[] y, yd; // State (Current Value, Current Velocity)
    private final float k1, k2, k3; // Constants derived from f, z, r
    private final float[] buffer;
    
    public SecondOrderDynamics(float f, float z, float r, T startValue, 
        ToFloatArrayCodec<T> codec) {

        if (f <= 0)
            throw new IllegalArgumentException("Frequency (f) must be > 0. Received: " + f);
        if (z < 0)
            throw new IllegalArgumentException("Damping (z) must be >= 0. Received: " + z);
        final int dimension = codec.dimensions();
        if (dimension <= 0)
            throw new IllegalArgumentException("Array Codec Dimension cannot be <= 0.");
        
        this.codec = codec;

        float PI = (float) Math.PI;
        this.k1 = z / (PI * f);
        this.k2 = 1 / ((2 * PI * f) * (2 * PI * f));
        this.k3 = r * z / (2 * PI * f);

        this.xp = this.codec.encodeNew(startValue);
        this.y = this.xp.clone();
        this.yd = this.codec.newZeroed();

        this.buffer = this.codec.newZeroed();
    }

    public T update(final float T_val, T x) {
        return update(T_val, x, null);
    }

    public T update(final float T_val, T x, @Nullable T out) {
        final var buffer = this.buffer;
        this.codec.encode(x, buffer);

        final float k2_stable = Math.max(
            Math.max(k2, T_val * T_val / 2 + T_val * k1 / 2), 
            T_val * k1
        ); // Stability clamp
        
        for (int i = 0; i < this.y.length; ++i) {
            // T = Time step since last update (seconds)
            // x = The noisy target value 
            
            // Estimate velocity of the target (xd)
            float xd = (buffer[i] - xp[i]) / T_val;
            xp[i] = buffer[i];

            float acc = (buffer[i] + k3 * xd - y[i] - k1 * yd[i])/k2_stable;
            yd[i] += acc * T_val; // Integrate velocity

            y[i] += yd[i] * T_val; // Integrate position
        }

        return this.codec.decode(y, out);
    }

    public void reset(T resetValue) {
        this.codec.encode(resetValue, this.xp);
        this.codec.copyArr(this.y, this.xp);
        Arrays.fill(this.yd, 0);
    }

    public static SecondOrderDynamics<Vector3f> vector3f(float f, float z, float r, Vector3f startValue) {
        return new SecondOrderDynamics<>(f, z, r, startValue, VECTOR3F_TO_ARRAY);
    }

    public static SecondOrderDynamics<Float> single(float f, float z, float r, float startValue) {
        return new SecondOrderDynamics<>(f, z, r, startValue, FLOAT_TO_ARRAY);
    }

    private static final ToFloatArrayCodec<Vector3f> VECTOR3F_TO_ARRAY
        = ToFloatArrayCodec.of(3, (data, arr) -> {
            arr[0] = data.x();
            arr[1] = data.y();
            arr[2] = data.z();
        }, (arr, output) -> {
            if (output == null)
                output = new Vector3f();
            output.set(arr[0], arr[1], arr[2]);
            return output;
        });

    private static final ToFloatArrayCodec<Float> FLOAT_TO_ARRAY
        = ToFloatArrayCodec.of(1, (data, arr) -> {
            arr[0] = data;
        }, (arr, output) -> {
            return arr[0];
        });

    public static interface ToFloatArrayCodec<T> {
        int dimensions();
        void encode(T data, float[] arr);
        T decode(float[] arr, @Nullable T output);

        public static <T> ToFloatArrayCodec<T> of(
            int dimension, 
            BiConsumer<T, float[]> encoder, 
            BiFunction<float[], T, T> decoder) {
            
            return new ToFloatArrayCodec<T>() {

                @Override
                public int dimensions() {
                    return dimension;
                }

                @Override
                public void encode(T data, float[] arr) {
                    encoder.accept(data, arr);
                }

                @Override
                public T decode(float[] arr, @Nullable T output) {
                    return decoder.apply(arr, output);
                }
                
            };
        }
        
        default float[] encodeNew(T data) {
            var arr = new float[this.dimensions()];
            this.encode(data, arr);
            return arr;
        }

        default float[] newZeroed() {
            return new float[this.dimensions()];
        }

        default void copyArr(float[] to, float[] from) {
            System.arraycopy(from, 0, to, 0, this.dimensions());
        }
    }

    
}