/*
 * Copyright © Wynntils 2023.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.services.itemfilter;

import net.minecraft.client.resources.language.I18n;

public class UnknownFilterException extends Exception {
    public UnknownFilterException(String filter) {
        super(I18n.get("feature.wynntils.itemFilter.unknownFilter", filter));
    }
}
