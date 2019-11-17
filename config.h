/*
 * config.h
 *
 *  Automatically created by OSEC Parameter Configurator
 *  Author: stancecoke
 */

#ifndef CONFIG_H_
#define CONFIG_H_

#define NUMBER_OF_PAS_MAGS 12
#define limit 28
#define timeout 16000
#define wheel_circumference 2230L
#define limit_without_pas 6
#define ADC_THROTTLE_MIN_VALUE 43
#define ADC_THROTTLE_MAX_VALUE 182
#define BATTERY_VOLTAGE_MIN_VALUE 10
#define BATTERY_CURRENT_MAX_VALUE 66L
#define PHASE_CURRENT_MAX_VALUE 350L
#define REGEN_CURRENT_MAX_VALUE 50L
#define MOTOR_ROTOR_DELTA_PHASE_ANGLE_RIGHT 238
#define current_cal_a 46
#define LEVEL_1 22
#define LEVEL_2 33
#define LEVEL_3 50
#define LEVEL_4 75
#define LEVEL_5 100
#define MORSE_TIME_1 50
#define MORSE_TIME_2 50
#define MORSE_TIME_3 50
#define RAMP_END 0
#define P_FACTOR 0.5
#define I_FACTOR 0.2
#define GEAR_RATIO 51L
#define PAS_THRESHOLD 1.9
#define RAMP_START 0
#define limit_with_throttle_override 35
#define CORRECTION_AT_ANGLE 127
#define ANGLE_4_0 1
#define ANGLE_6_60 43
#define ANGLE_2_120 86
#define ANGLE_3_180 128
//#define BLUOSEC
#define DIAGNOSTICS
#define ANGLE_1_240 171
#define ANGLE_5_300 213
#define TQS_CALIB 80
#define ACA 6812
#define EEPROM_INIT_MAGIC_BYTE 25 // makes sure (chance of fail 1/255) eeprom is invalidated after flashing new config
#define ADC_BATTERY_VOLTAGE_K 67
#define ACA_EXPERIMENTAL 128
#define BATTERY_VOLTAGE_MAX_VALUE 208

#endif /* CONFIG_H_ */
