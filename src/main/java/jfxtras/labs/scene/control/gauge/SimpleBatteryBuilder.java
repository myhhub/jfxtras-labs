/*
 * Copyright (c) 2012, JFXtras
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jfxtras.labs.scene.control.gauge;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Stop;

import java.util.HashMap;


/**
 * Created by
 * User: hansolo
 * Date: 30.03.12
 * Time: 15:13
 */
public class SimpleBatteryBuilder {
    private HashMap<String, Property> properties = new HashMap<String, Property>();

    public static final SimpleBatteryBuilder create() {
        return new SimpleBatteryBuilder();
    }

    public final SimpleBatteryBuilder charging(final boolean CHARGING) {
        properties.put("charging", new SimpleBooleanProperty(CHARGING));
        return this;
    }

    public final SimpleBatteryBuilder chargeIndicator(final SimpleBattery.ChargeIndicator CHARGE_INDICATOR) {
        properties.put("chargeIndicator", new SimpleObjectProperty<SimpleBattery.ChargeIndicator>(CHARGE_INDICATOR));
        return this;
    }

    public final SimpleBatteryBuilder chargingLevel(final double CHARGING_LEVEL) {
        properties.put("chargingLevel", new SimpleDoubleProperty(CHARGING_LEVEL));
        return this;
    }

    public final SimpleBatteryBuilder levelColors(final Stop[] LEVEL_COLORS) {
        properties.put("levelColors", new SimpleObjectProperty<Stop[]>(LEVEL_COLORS));
        return this;
    }

    public final SimpleBattery build() {
        final SimpleBattery BATTERY = new SimpleBattery();
        for (String key : properties.keySet()) {
            if ("charging".equals(key)) {
                BATTERY.setCharging(((BooleanProperty) properties.get(key)).get());
            } else if ("chargeIndicator".equals(key)) {
                BATTERY.setChargeIndicator(((ObjectProperty<SimpleBattery.ChargeIndicator>) properties.get(key)).get());
            } else if ("chargingLevel".equals(key)) {
                BATTERY.setChargingLevel(((DoubleProperty) properties.get(key)).get());
            } else if ("levelColors".equals(key)) {
                BATTERY.setLevelColors(((ObjectProperty<Stop[]>) properties.get(key)).get());
            }
        }
        return BATTERY;
    }
}
