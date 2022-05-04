package com.example.core_database_domain.model

import com.example.core_utils.style_map.architect
import com.example.core_utils.style_map.retro

enum class Theme(val theme: String) {
    RETRO(theme = retro),
    ARCHITECT(theme = architect)
}