package com.mariii.readdiary.data.mapper

object CategoryMapper {

    fun map(apiCategory: String?): String {

        if (apiCategory.isNullOrBlank()) {
            return "Другое"
        }

        return when {

            apiCategory.contains("Fantasy", true) ->
                "Фэнтези"

            apiCategory.contains("Psychology", true) ->
                "Психология"

            apiCategory.contains("Philosophy", true) ->
                "Философия"

            apiCategory.contains("Self", true) ->
                "Саморазвитие"

            apiCategory.contains("Drama", true) ->
                "Классика"

            apiCategory.contains("Fiction", true) ->
                "Роман"

            apiCategory.contains("Literary", true) ->
                "Классика"

            apiCategory.contains("Juvenile", true) ->
                "Классика"

            else ->
                "Другое"
        }
    }
}