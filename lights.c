/*
 * lights.c
 *
 *  Created on: Oct 14, 2018
 *      Author: mvturnho
 */


#include <stdint.h>
#include "stm8s.h"
#include "gpio.h"
#include "stm8s_gpio.h"

void lights_init (void)
{
  GPIO_Init(LIGHTS__PORT,
            LIGHTS__PIN,
            GPIO_MODE_OUT_PP_LOW_SLOW);
}

void lights_set_state (uint8_t ui8_state)
{
  if (ui8_state)
  {
    GPIO_WriteHigh (LIGHTS__PORT, LIGHTS__PIN);
  }
  else
  {
    GPIO_WriteLow (LIGHTS__PORT, LIGHTS__PIN);
  }
}
