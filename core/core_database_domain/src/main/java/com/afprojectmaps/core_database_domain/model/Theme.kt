package com.afprojectmaps.core_database_domain.model

import com.afprojectmaps.core_utils.style_map.architect
import com.afprojectmaps.core_utils.style_map.retro

enum class Theme(val theme: String) {
    RETRO(theme = retro),
    ARCHITECT(theme = architect)
}