package com.kennedyrobotics.drivers;

import com.revrobotics.CANError;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.EncoderType;
import com.revrobotics.jni.CANSparkMaxJNI;

import java.lang.reflect.Field;

public class SparkMaxUtil {
    private SparkMaxUtil() {}


    public static CANError setSensorType(CANSparkMaxLowLevel spark, EncoderType type) {
        try {
            Field f = CANSparkMaxLowLevel.class.getDeclaredField("m_sparkMax");
            f.setAccessible(true);
            long sparkMaxPtr = f.getLong(spark);
            return CANError.fromInt(CANSparkMaxJNI.c_SparkMax_SetSensorType(sparkMaxPtr, type.value));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
