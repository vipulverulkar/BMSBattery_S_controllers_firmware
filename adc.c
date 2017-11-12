/*
 * EGG OpenSource EBike firmware
 *
 * Copyright (C) Casainho, 2015, 2106, 2017.
 *
 * Released under the GPL License, Version 3
 */

#include <stdint.h>
#include <stdio.h>
#include "stm8s.h"
#include "gpio.h"
#include "stm8s_adc1.h"
#include "adc.h"




void adc_init (void)
{
 uint8_t ui8_i;
 //uint16_t ui16_counter;

  //init GPIO for the used ADC pins
  GPIO_Init(GPIOB,
	    (THROTTLE__PIN || CURRENT_PHASE_B__PIN || CURRENT_MOTOR_TOTAL__PIN),
	    GPIO_MODE_IN_FL_NO_IT);

  GPIO_Init(GPIOE,
	    (CURRENT_MOTOR_TOTAL_FILTERED__PIN),
	    GPIO_MODE_IN_FL_NO_IT);

  //de-Init ADC peripheral
  ADC1_DeInit();

  //init ADC1 peripheral
  ADC1_Init(ADC1_CONVERSIONMODE_SINGLE,
	    ADC1_CHANNEL_9,
	    ADC1_PRESSEL_FCPU_D2,
            ADC1_EXTTRIG_TIM,
	    DISABLE,
	    ADC1_ALIGN_LEFT,
	    (ADC1_SCHMITTTRIG_CHANNEL4 || ADC1_SCHMITTTRIG_CHANNEL5 || ADC1_SCHMITTTRIG_CHANNEL6 || ADC1_SCHMITTTRIG_CHANNEL8),
            DISABLE);

  ADC1_ScanModeCmd (ENABLE);
  ADC1_Cmd (ENABLE);

  //********************************************************************************
  // next code is for "calibrating" the offset value of ADC motor total current
  // read and discard few samples of ADC, to make sure the next samples are ok
  for (ui8_i = 0; ui8_i < 8; ui8_i++)
  {
    //ui16_counter = TIM2_GetCounter () + 10;
    //while (TIM2_GetCounter () < ui16_counter) ; // delay
    adc_trigger ();

    printf("%d, %d, %d\r\n", ui8_adc_read_phase_B_current (), ui16_adc_read_motor_total_current (), ui8_adc_read_throttle ());
  }

 /* // read and average a few values of ADC
  ui16_motor_total_current_offset_10b = 0;
  for (ui8_i = 0; ui8_i < 16; ui8_i++)
  {
    ui16_counter = TIM2_GetCounter () + 10;
    while (TIM2_GetCounter () < ui16_counter) ;
    adc_trigger ();
    ui16_motor_total_current_offset_10b += ui16_adc_read_motor_total_current ();
  }
  ui16_motor_total_current_offset_10b >>= 4;
  ui8_motor_total_current_offset = ui16_motor_total_current_offset_10b >> 2;*/
}

  inline void adc_trigger (void) //inline ?!
{
  // start ADC all channels, scan conversion (buffered)
  ADC1->CSR &= 0x09; // clear EOC flag first (selected also channel 9)
  ADC1_StartConversion ();
}

uint8_t ui8_adc_read_phase_B_current (void)
{
//  /* Read LSB first */
//  templ = *(uint8_t*)(uint16_t)((uint16_t)ADC1_BaseAddress + (uint8_t)(Buffer << 1) + 1);
//  /* Then read MSB */
//  temph = *(uint8_t*)(uint16_t)((uint16_t)ADC1_BaseAddress + (uint8_t)(Buffer << 1));
//#define ADC1_BaseAddress        0x53E0
//phase_B_current --> ADC_AIN5
// 0x53E0 + 2*5 = 0x53EA
  return *(uint8_t*)(0x53EA);
}

uint16_t ui16_adc_read_phase_B_current (void)
{
  uint16_t temph;
  uint8_t templ;

  templ = *(uint8_t*)(0x53EB);
  temph = *(uint8_t*)(0x53EA);

  return ((uint16_t) temph) << 2 | ((uint16_t) templ);
}

uint8_t ui8_adc_read_throttle (void)
{
// 0x53E0 + 2*4 = 0x53E8
//  return *(uint8_t*)(0x53E8);
  return *(uint8_t*)(0x53E8);
}

uint8_t ui8_adc_read_motor_total_current (void)
{
// 0x53E0 + 2*8 = 0x53F0
  return *(uint8_t*)(0x53F0);
}

uint16_t ui16_adc_read_motor_total_current (void)
{
  uint16_t temph;
  uint8_t templ;

  templ = *(uint8_t*)(0x53F1);
  temph = *(uint8_t*)(0x53F0);

  return ((uint16_t) temph) << 2 | ((uint16_t) templ);
}

uint8_t ui8_adc_read_battery_voltage (void)
{
  // 0x53E0 + 2*9 = 0x53F2
  return *(uint8_t*)(0x53F2);
}
